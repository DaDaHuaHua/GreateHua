package com.example.song.optimize.bitmap_cache;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

/**
 * Created by 33105 on 2018/2/2.
 */

public class ImageResize {

    public static Bitmap resizeBitmap(Context context, @DrawableRes int id, int maxW, int maxH, boolean hasAlpha,Bitmap reuseable) {
        Resources resources = context.getResources();
        BitmapFactory.Options options = new BitmapFactory.Options();
        //只解码出 outXXX参数，比如 宽、高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, id, options);
        int w = options.outWidth;
        int h = options.outHeight;
        //设置缩放系数
        options.inSampleSize = calcuInSampleSize(w, h, maxW, maxH);
        if (!hasAlpha) {
            //不需要透明度，采用RGB565
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        options.inJustDecodeBounds = false;
        //复用：1：需要设置为易变的
        options.inMutable = true;
        options.inBitmap = reuseable;
        return BitmapFactory.decodeResource(resources, id, options);
    }

    /**
     * @param w
     * @param h
     * @param maxW
     * @param maxH
     * @return 缩放系数
     */
    private static int calcuInSampleSize(int w, int h, int maxW, int maxH) {
        int inSampleSize = 1;
        if (w > maxW && h > maxH) {
            inSampleSize = 2;
            //循环使 宽高小于最大的宽高
            while (w / inSampleSize > maxW && h / inSampleSize > maxH) {
                inSampleSize *= 2;
            }

        }
        return inSampleSize;
    }
}
