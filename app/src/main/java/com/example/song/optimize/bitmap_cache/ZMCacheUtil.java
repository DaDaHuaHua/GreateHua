package com.example.song.optimize.bitmap_cache;

import android.util.LruCache;


import com.example.commonlibrary.util.StringUtil;
import com.example.song.optimize.bitmap_cache.disk.DiskLruCache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * <p> Created by 宋华 on 2018/2/5.
 */
public class ZMCacheUtil {
    //默认内存缓存10M
    public static final int DEFAULT_MEMORY_CAPACITY = 1024 * 1024 * 10;
    //默认磁盘缓存10M
    public static final int DEFAULT_DISK_CAPACITY = 1024 * 1024 * 10;
    private static ZMCacheUtil sInstance;
    private LruCache<String, String> mMemoryCache;
    private DiskLruCache mDiskLruCache;
    private boolean mInited;

    public static ZMCacheUtil getInstance() {
        if (null == sInstance) {
            synchronized (ZMCacheUtil.class) {
                if (null == sInstance) {
                    sInstance = new ZMCacheUtil();
                }
            }
        }
        return sInstance;
    }

    public void init(File cacheDir, int appVersion) {
        init(cacheDir, appVersion, DEFAULT_MEMORY_CAPACITY);
    }

    public void init(File cacheDir, int appVersion, int capacity) {
        if (mInited) {
            throw new IllegalStateException("ZMCacheUtil has already initialized !!!");
        }
        mInited = true;
        //初始化MemoryCache
        mMemoryCache = new LruCache<String, String>(capacity) {
            @Override
            protected int sizeOf(String key, String value) {
                if (value == null) {
                    return 0;
                }
                int length;
                try {
                    length = value.getBytes("utf-8").length;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    length = value.getBytes().length;
                }
                return length;
            }
        };
        //初始化DiskCache
        try {
            mDiskLruCache = DiskLruCache.open(cacheDir, appVersion, 1, DEFAULT_DISK_CAPACITY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void put2Memory(String key, String value) {
        mMemoryCache.put(key, value);
    }


    public void put2Disk(String key, String value) {
        if (StringUtil.isEmpty(value) || StringUtil.isEmpty(key)) {
            throw new NullPointerException("key == null || value == null");
        }
        if (mDiskLruCache == null) {
            return;
        }
        DiskLruCache.Snapshot snapshot = null;
        OutputStream os = null;
        try {
            snapshot = mDiskLruCache.get(key);
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (null != editor) {
                os = editor.newOutputStream(0);
                os.write(value.getBytes());
                editor.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != snapshot) {
                snapshot.close();
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getFromMemory(String key) {
        return mMemoryCache.get(key);
    }

    public String getFromDisk(String key) {
        if (null == mDiskLruCache) {
            return null;
        }
        DiskLruCache.Snapshot snapshot = null;
        ByteArrayOutputStream bos = null;
        try {
            snapshot = mDiskLruCache.get(key);
            if (snapshot == null) {
                return null;
            }
            InputStream is = snapshot.getInputStream(0);
            if (is != null) {
                bos = new ByteArrayOutputStream();
                int i = -1;
                while ((i = is.read()) != -1) {
                    bos.write(i);
                }
                return bos.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != snapshot) {
                snapshot.close();
            }
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void clearMemoryCache() {
        mMemoryCache.evictAll();
    }

    public void clearDiskCache() {
        if (mDiskLruCache != null && !mDiskLruCache.isClosed()) {
            try {
                mDiskLruCache.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void put(String key, String value) {
        this.put(key, value, true);
    }

    public void put(String key, String value, boolean cacheInDisk) {
        put2Memory(key, value);
        if (cacheInDisk) {
            put2Disk(key, value);
        }
    }

    public String get(String key, boolean findInDisk) {
        String value = getFromMemory(key);
        if (StringUtil.isEmpty(value) && findInDisk) {
            return getFromDisk(key);
        }
        return value;
    }

}
