package com.example.song.optimize.bitmap_cache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.util.LruCache;

import com.example.song.BuildConfig;
import com.example.song.optimize.bitmap_cache.disk.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 33105 on 2018/2/2.
 */

public class ImageCache {
    private static ImageCache instance;
    private Context mContext;
    private LruCache<String, Bitmap> memoryCache;
    private DiskLruCache mDiskLruCache;
    BitmapFactory.Options mOptions = new BitmapFactory.Options();
    /**
     * 使用一个Bitmap复用池 来保存可被复用的Bitmap
     */
    Set<WeakReference<Bitmap>> reuseablePool;

    public static ImageCache getInstance() {
        if (null == instance) {
            synchronized (ImageCache.class) {
                if (null == instance) {
                    instance = new ImageCache();
                }
            }
        }
        return instance;
    }

    private ReferenceQueue<Bitmap> referenceQueue;
    private Thread clearReferenceQueue;
    boolean shutDown;

    private ReferenceQueue<Bitmap> getReferenceQueue(String key) {
        if (null == referenceQueue) {
            //引用队列，当弱引用需要被回收，会放入队列
            referenceQueue = new ReferenceQueue<Bitmap>();
            clearReferenceQueue = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!shutDown) {
                        try {
                            Reference<? extends Bitmap> reference = referenceQueue.remove();
                            Bitmap bitmap = reference.get();
                            if (null != bitmap && !bitmap.isRecycled()) {
                                bitmap.recycle();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            clearReferenceQueue.start();
        }
        return referenceQueue;
    }

    public void init(Context context, String dir) {
        this.mContext = context.getApplicationContext();
        reuseablePool = Collections.synchronizedSet(new HashSet<WeakReference<Bitmap>>());
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memoryClass = am.getMemoryClass();
        memoryCache = new LruCache<String, Bitmap>(memoryClass / 8 * 1024 * 1024) {
            /**
             *
             * @param key
             * @param value
             * @return value 占用内存
             */
            @Override
            protected int sizeOf(String key, Bitmap value) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    return value.getAllocationByteCount();
                }
                return value.getByteCount();
            }

            /**
             * 当Bitmap从lru移除的时候回调
             * @param evicted
             * @param key
             * @param oldValue
             * @param newValue
             */
            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                if (oldValue.isMutable()) {
                    //bitmap对象存储位置
                    //3.0以下是在直接内存中
                    //3.0 以上是在java堆中
                    //8.0又到了直接内存
                    reuseablePool.add(new WeakReference<Bitmap>(oldValue, getReferenceQueue(key)));
                    Log.i("Adapter", "加入复用池: "+oldValue);
                }
                oldValue.recycle();
            }
        };

        try {
            mDiskLruCache = DiskLruCache.open(new File(dir), BuildConfig.VERSION_CODE, 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void putBitmap2Memory(String key, Bitmap bitmap) {
        memoryCache.put(key, bitmap);
    }

    public void putBitmap2Disk(String key, Bitmap bitmap)  {
        DiskLruCache.Snapshot snapshot = null;
        OutputStream os = null;
        try {
            snapshot = mDiskLruCache.get(key);
            //如果缓存有对应key的文件，那么不管
            if (snapshot == null) {
                DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                if (null != editor) {
                    os = editor.newOutputStream(0);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
                    editor.commit();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != snapshot) {
                snapshot.close();
            }
        }
    }


    public Bitmap getBitmapFromDisk(String key,Bitmap reuseable) {
        DiskLruCache.Snapshot snapshot = null;
        Bitmap bitmap = null;
        try {
            snapshot = mDiskLruCache.get(key);
            if (snapshot == null) {
                return null;
            }
            InputStream is = snapshot.getInputStream(0);
            //为了能复用
            mOptions.inMutable = true;
            mOptions.inBitmap = reuseable;
            Log.i("Adapter", "使用复用内存: "+reuseable);
            bitmap = BitmapFactory.decodeStream(is);
            if (bitmap != null) {
                memoryCache.put(key, bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }
        return bitmap;
    }

    public Bitmap getReuseable(int w, int h, int inSampleSize) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return null;
        }
        Bitmap reuseable = null;
        Iterator<WeakReference<Bitmap>> iterator = reuseablePool.iterator();
        //查找符合复用条件的Bitmap
        while (iterator.hasNext()) {
            Bitmap bitmap = iterator.next().get();
            if (null != bitmap) {
                if (checkInBitmap(bitmap, w, h, inSampleSize)) {
                    reuseable = bitmap;
                    //移出复用池
                    iterator.remove();
                    break;
                }
            } else {
                iterator.remove();
            }
        }
        return reuseable;
    }

    boolean checkInBitmap(Bitmap bitmap, int w, int h, int inSampleSize) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return bitmap.getWidth() == w && bitmap.getHeight() == h;
        }
        if (inSampleSize > 1) {
            w /= inSampleSize;
            h /= inSampleSize;
        }
        int byteCount = w * h * getPixelsCount(bitmap.getConfig());
        return byteCount <= bitmap.getAllocationByteCount();
    }

    int getPixelsCount(Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888) {
            return 4;
        }
        return 2;
    }

    public Bitmap getBitmapFromMemory(String key) {
        return memoryCache.get(key);
    }

    public void clearMemory() {
        memoryCache.evictAll();
    }
}
