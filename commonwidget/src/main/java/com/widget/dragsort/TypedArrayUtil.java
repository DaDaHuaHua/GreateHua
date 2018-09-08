package com.widget.dragsort;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StyleableRes;
import android.support.v4.content.ContextCompat;

/**
 * <p/>Created by RenTao on 16/8/25.
 */
public class TypedArrayUtil {

    public static int getDimension(Context context, TypedArray typedArray, @StyleableRes int resId) {
        return getDimension(context, typedArray, resId, 0);
    }

    public static int getDimension(Context context, TypedArray typedArray, @StyleableRes int resId, int defaultValue) {
        int value = typedArray.getResourceId(resId, 0);
        if (value == 0) {
            return typedArray.getDimensionPixelSize(resId, defaultValue);
        } else {
            return context.getResources().getDimensionPixelOffset(value);
        }
    }

    public static String getString(Context context, TypedArray typedArray, @StyleableRes int resId) {
        int value = typedArray.getResourceId(resId, 0);
        if (value == 0) {
            return typedArray.getString(resId);
        } else {
            return context.getResources().getString(value);
        }
    }
    public static String getString(Context context, TypedArray typedArray, @StyleableRes int resId, String defaultValue) {
        int value = typedArray.getResourceId(resId, 0);
        String str;
        if (value == 0) {
            str = typedArray.getString(resId);
        } else {
            str = context.getResources().getString(value);
        }
        return str == null ? defaultValue : str;
    }

    public static int getColor(Context context, TypedArray typedArray, @StyleableRes int resId) {
        return getColor(context, typedArray, resId, 0);
    }

    public static int getColor(Context context, TypedArray typedArray, @StyleableRes int resId, int defaultValue) {
        int value = typedArray.getResourceId(resId, 0);
        int color;
        if (value == 0) {
            color = typedArray.getColor(resId, defaultValue);
        } else {
            color = ContextCompat.getColor(context, value);
        }
        return color == 0 ? defaultValue : color;
    }

}
