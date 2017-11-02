package com.example.commonlibrary.util.bean;

import android.support.annotation.ColorInt;

/**
 * <p> Created by 宋华 on 2017/10/26.
 * <p>
 * text      文字
 * textColor   文字颜色
 * textSize    文字大小 px
 * textAlpha   文字透明度 [0,255]
 */
public class TextConfig {
    public String text;
    @ColorInt
    public int textColor;
    public float textSize;
    public int textAlpha;
    public TextShadow textShadow;

    public static class TextShadow {
        public TextShadow(float shadowRadius, float shadowDx, float shadowDy, int shadowColor) {
            this.shadowRadius = shadowRadius;
            this.shadowDx = shadowDx;
            this.shadowDy = shadowDy;
            this.shadowColor = shadowColor;
        }

        public float shadowRadius;
        public float shadowDx;
        public float shadowDy;
        public int shadowColor;
    }


}
