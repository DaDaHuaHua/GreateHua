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
import android.util.Log;

import com.example.commonlibrary.util.FileUtil;
import com.example.commonlibrary.util.bean.TextConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
            Log.e("","cannot read exif:" + ex);
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

    /**
     * 根据文字生成图片
     *
     * @return Bitmap
     */
    public static Bitmap createBitmapFromText(TextConfig textConfig) {
        return createBitmapFromText(textConfig, 0, 0);
    }

    /**
     * 根据文字生成图片
     * 可以传入最小宽高，如果 minWidth大于文字的宽度，那么Bitmap的宽即为minWith，minHeight同理
     *
     * @return Bitmap
     */
    public static Bitmap createBitmapFromText(TextConfig textConfig, int minWidth, int minHeight) {
        if (textConfig == null) {
            return null;
        }
        if (StringUtil.isBlank(textConfig.text)) {
            return null;
        }
        Canvas canvas = new Canvas();

        Paint paint = new Paint();
        paint.setColor(textConfig.textColor);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setAntiAlias(true);//去除锯齿
        paint.setFilterBitmap(true);//对位图进行滤波处理
        paint.setTextSize(textConfig.textSize);
        paint.setAlpha(textConfig.textAlpha);
        TextConfig.TextShadow shadow = textConfig.textShadow;
        if (shadow != null) {
            paint.setShadowLayer(shadow.shadowRadius, shadow.shadowDx, shadow.shadowDy, shadow.shadowColor);
        }
        float textWidth = paint.measureText(textConfig.text);
        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        paint.getFontMetrics(fontMetrics);
        float startY = -fontMetrics.top;
        float textHeight = fontMetrics.bottom - fontMetrics.top;
        int bitmapHeight;
        int bitmapWidth;
        bitmapWidth = (int) Math.max(textWidth, minWidth) + 1;
        bitmapHeight = (int) Math.max(textHeight, minHeight) + 1;
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        canvas.drawColor(TRANSPARENT, PorterDuff.Mode.CLEAR);

        canvas.drawText(textConfig.text, 0, startY, paint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return bitmap;
    }


    public static String createBitmapFromTextAndSave(TextConfig textConfig, String path) {
        Bitmap bitmap = createBitmapFromText(textConfig);
        if (bitmap != null) {
            boolean succ = saveBitmap(bitmap, path);
            if (succ) {
                return path;
            }
        }
        return null;
    }


    /**
     * 图片+文字 生成 新图片
     */
    public static Bitmap createBitmap(Resources res, @DrawableRes int resId, TextConfig textConfig, int verticalOffSet, int textPadding) {
        Bitmap bitmap = getBitmapFromRes(res, resId);
        return createBitmap(bitmap, textConfig, verticalOffSet, textPadding);
    }

    public static Bitmap createBitmap(Resources res, @DrawableRes int resId, TextConfig textConfig, int verticalOffSet, int textPadding, int minWidth, int minHeight) {
        Bitmap bitmap = getBitmapFromRes(res, resId);
        return createBitmap(bitmap, textConfig, verticalOffSet, textPadding, minWidth, minHeight);
    }

    public static Bitmap createBitmap(Bitmap bitmap, TextConfig textConfig, int verticalOffSet, int textPadding) {
        return createBitmap(bitmap, textConfig, verticalOffSet, textPadding, 0, 0);
    }

    /**
     * 图片+文字
     *
     * @param bitmap         图片
     * @param textConfig     文字设置 {@link TextConfig}
     * @param verticalOffSet 拼接时候的图片偏移量 <0 会向下偏移  >0会向上偏移
     * @param textPadding    图片和文字之间的间距
     * @param minWidth       最终生成图片的最小宽度。 默认情况下，最终图片宽度为 资源图片宽度+文字图片+padding, 如果minWidth 大于默认宽度，会使用透明背景撑满最大宽度，且内容居左上。
     * @param minHeight      同上
     * @return 最终图片
     */
    public static Bitmap createBitmap(Bitmap bitmap, TextConfig textConfig, int verticalOffSet, int textPadding, int minWidth, int minHeight) {
        Bitmap textBitmap = createBitmapFromText(textConfig);
        Canvas canvas = new Canvas();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (textBitmap == null && bitmap == null) {
            return null;
        } else if (textBitmap == null && bitmap != null) {
            int bitmapWidth = Math.max(minWidth, bitmap.getWidth() + textPadding);
            int bitmapHeight = Math.max(minHeight, bitmap.getHeight());
            Bitmap result = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
            canvas.setBitmap(result);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return result;
        } else if (textBitmap != null && bitmap == null) {
            int bitmapWidth = Math.max(minWidth, textBitmap.getWidth() + textPadding);
            int bitmapHeight = Math.max(minHeight, textBitmap.getHeight());
            Bitmap result = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
            canvas.setBitmap(result);
            canvas.drawBitmap(textBitmap, 0, 0, paint);
            return result;
        } else {
            int drawableWidth = bitmap.getWidth();
            int drawableHeight = bitmap.getHeight();
            int textWidth = textBitmap.getWidth();
            int textHeight = textBitmap.getHeight();

            int bitmapStartY = Math.abs((drawableHeight - textHeight) / 2);
            int bitmapWidth = Math.max(minWidth, drawableWidth + textWidth + textPadding);
            int bitmapHeight = Math.max(minHeight, Math.max(drawableHeight, textHeight));
            Bitmap result = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
            canvas.setBitmap(result);
            if (drawableHeight >= textHeight) {
                canvas.drawBitmap(bitmap, 0, 0, paint);
                canvas.drawBitmap(textBitmap, drawableWidth + textPadding, bitmapStartY + verticalOffSet, paint);
            } else {
                canvas.drawBitmap(bitmap, 0, bitmapStartY - verticalOffSet, paint);
                canvas.drawBitmap(textBitmap, drawableWidth + textPadding, 0, paint);
            }
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
            return result;
        }
    }


    public static String createBitmapAndSave(Resources res, @DrawableRes int resId, TextConfig textConfig, int verticalOffSet, int textPadding, String destPath) {
        Bitmap bitmap = createBitmap(res, resId, textConfig, verticalOffSet, textPadding);
        if (bitmap != null) {
            boolean succ = saveBitmap(bitmap, destPath);
            if (succ) {
                return destPath;
            }
        }
        return null;
    }

    public static String createBitmapAndSave(Resources res, @DrawableRes int resId, TextConfig textConfig, int verticalOffSet, int textPadding, int minWidth, int minHeight, String destPath) {
        Bitmap bitmap = createBitmap(res, resId, textConfig, verticalOffSet, textPadding, minWidth, minHeight);
        if (bitmap != null) {
            boolean succ = saveBitmap(bitmap, destPath);
            if (succ) {
                return destPath;
            }
        }
        return null;
    }


    public static Bitmap getBitmapFromRes(Resources resources, @DrawableRes int resId) {
        if (resources == null || resId <= 0) {
            return null;
        }
        BitmapDrawable bitmapDrawable = (BitmapDrawable) resources.getDrawable(resId);
        return bitmapDrawable.getBitmap();
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

}
