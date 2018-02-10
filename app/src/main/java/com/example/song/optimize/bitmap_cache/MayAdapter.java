package com.example.song.optimize.bitmap_cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.song.R;

/**
 * Created by 33105 on 2018/2/2.
 */

public class MayAdapter extends BaseAdapter {
    private Context mContext;

    public MayAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_bitmap_chache, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //一般写法
//        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.my_gril);
        //第一次优化
//        Bitmap bitmap = ImageResize.resizeBitmap(mContext, R.drawable.my_gril, 80, 80, false);
        //第二次优化
        /**
         * 先从内存缓存中查找图片，没有找到就从复用池查找允许复用的图片
         * 然后使用复用的bitmap  inBitmap去加载新的图片
         */
        Bitmap bitmap = ImageCache.getInstance().getBitmapFromMemory(String.valueOf(position));
        Log.i("Adapter", "从内存缓存获取: "+bitmap);
        if (null == bitmap) {
            //从复用池中找可以被复用内存的图片
            Bitmap reuseable = ImageCache.getInstance().getReuseable(80, 80, 1);
            Log.i("Adapter", "复用内存: "+reuseable);
            //内存没有，从磁盘中拿
            bitmap = ImageCache.getInstance().getBitmapFromDisk(String.valueOf(position),reuseable);
            Log.i("Adapter", "从磁盘缓存获取: "+bitmap);
            if(bitmap == null){
                //内存没找到，再去加载
                bitmap = ImageResize.resizeBitmap(mContext, R.drawable.my_gril, 80, 80, false,reuseable);
                ImageCache.getInstance().putBitmap2Memory(String.valueOf(position),bitmap);
                ImageCache.getInstance().putBitmap2Disk(String.valueOf(position),bitmap);
            }
        }
        viewHolder.mIvAvatar.setImageBitmap(bitmap);
        return convertView;
    }


    class ViewHolder {
        ImageView mIvAvatar;

        ViewHolder(View convertView) {
            mIvAvatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
        }
    }
}
