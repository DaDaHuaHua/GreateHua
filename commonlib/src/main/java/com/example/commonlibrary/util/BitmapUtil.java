package com.example.commonlibrary.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.graphics.Color.TRANSPARENT;

public class BitmapUtil extends FileUtil {

    /**
     * 计算给定Bitmap占用的空间大小,如果bmp参数为null,则返回-1
     *
     * @param bmp 给定的Bitmap
     * @return 计算所得的结果
     */
    public static int getBitmapSize(Bitmap bmp) {
        if (bmp == null) {
            return -1;
        }
        return bmp.getRowBytes() * bmp.getHeight();
    }

    /**
     * 将Bitmap转换为字节数组
     */
    public static byte[] convertBitmapToBytes(Bitmap bmp) {
        if (bmp == null || bmp.isRecycled()) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 得到 图片旋转 的角度
     */
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        if (StringUtil.isBlank(filepath)) {
            return 0;
        }
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            Log.e("error","cannot read exif:" + ex);
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

    public static Bitmap autoScaleBitmap(String picUri) {
        if (StringUtil.isBlank(picUri)) {
            return null;
        }
        int angle = getExifOrientation(picUri);
        try {
            Bitmap photoViewBitmap;
            if (angle != 0) { // 如果照片出现了 旋转 那么 就更改旋转度数
                Matrix matrix = new Matrix();
                matrix.postRotate(angle);
                Bitmap bmp = BitmapFactory.decodeFile(picUri);
                photoViewBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            } else {
                photoViewBitmap = BitmapFactory.decodeFile(picUri);
            }
            return photoViewBitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return BitmapFactory.decodeFile(picUri);
        }
    }

    public static Bitmap createBitmapFromText(String text, @ColorInt int textColor, float textSize) {
        if (StringUtil.isBlank(text)) {
            return null;
        }
        Canvas canvas = new Canvas();
        canvas.drawColor(TRANSPARENT, PorterDuff.Mode.CLEAR);
        Paint paint = new Paint();
        paint.setColor(textColor);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setAntiAlias(true);//去除锯齿
        paint.setFilterBitmap(true);//对位图进行滤波处理
        paint.setTextSize(textSize);
        paint.setAlpha(255);
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        Bitmap bitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        canvas.drawText(text, 0, 0, paint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        if(!saveBitmap(bitmap,Environment.getExternalStorageDirectory() + "/掌门水印文字.png")){
            throw new RuntimeException("保存文字图片失败！");
        }

        return bitmap;
    }

    public static boolean saveBitmap(@NonNull Bitmap bitmap, String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            return false;
        }
        FileOutputStream os;
        boolean result;
        try {
            os = new FileOutputStream(file);
            result = bitmap.compress(CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }


    public static Bitmap createBitmap(Resources res, @DrawableRes int resId, String sufix, @ColorInt int textColor, float textSize) {
        Bitmap textBitmap = createBitmapFromText(sufix, textColor, textSize);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) res.getDrawable(resId);
        Bitmap drawableBitmap = bitmapDrawable.getBitmap();
        Canvas canvas = new Canvas();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (textBitmap == null && drawableBitmap != null) {
            return drawableBitmap;
        } else if (textBitmap != null && drawableBitmap == null) {
            return textBitmap;
        } else if (textBitmap == null && drawableBitmap == null) {
            return null;
        } else {
            int drawableWidth = drawableBitmap.getWidth();
            int drawableHeight = drawableBitmap.getHeight();
            int textWidth = textBitmap.getWidth();
            int textHeight = textBitmap.getHeight();

            Bitmap result = Bitmap.createBitmap(Math.max(drawableWidth, textWidth), Math.max(drawableHeight, textHeight), Bitmap.Config.ARGB_8888);
            canvas.setBitmap(result);
            canvas.drawBitmap(drawableBitmap, 0, 0, paint);
            int textStartY = (drawableHeight - textHeight) / 2;
            canvas.drawBitmap(textBitmap, drawableWidth, textStartY, paint);

            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
            if (result != null) {
                boolean saved = saveBitmap(result, Environment.getExternalStorageDirectory() + "/掌门水印.png");
                if (!saved) {
                    throw new RuntimeException("保存图片失败！");
                }
            }
            return result;
        }
    }


}
