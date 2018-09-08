package com.widget.dragsort.tv;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.widget.R;
import com.widget.dragsort.TypedArrayUtil;


/**
 * Created by SongH on 2018/8/4.
 */

public class BlingTextView extends android.support.v7.widget.AppCompatTextView {

    public static final int SPEED_LOW = 10;
    public static final int SPEED_MEDIUM = 10;
    public static final int SPEED_HIGH = 50;

    public BlingTextView(Context context) {
        this(context, null);
    }

    public BlingTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BlingTextView);
        mBlingColor = TypedArrayUtil.getColor(getContext(), typedArray, R.styleable.BlingTextView_blingColor);
        mSpeed = typedArray.getInt(R.styleable.BlingTextView_speed, SPEED_MEDIUM);
    }

    private int mBlingColor;
    private int mTextColor;

    public void setBlingColor(int color) {
        this.mBlingColor = color;
    }

    public void setAnimState(boolean useAnim) {
        this.mAnimStart = useAnim;
        postInvalidate();
        if (useAnim) {

        } else {

        }
    }

    public void setSpeed(int speed) {
        this.mSpeed = speed;
    }

    private LinearGradient mGradient;
    private Matrix mMatrix;
    private int mWidth;
    private boolean mAnimStart = true;
    private int mSpeed;

    /***
     * 每次宽高发生变化时
     * 需要给LinearGradient重新赋值，否则Bling效果无法完全展示
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mTextColor = getCurrentTextColor();
        if (mWidth > 0 && mBlingColor != 0) {
            mGradient = new LinearGradient(0, 0, mWidth, 0, new int[]{mTextColor, mBlingColor, mTextColor}, null, Shader.TileMode.CLAMP);
            Paint paint = getPaint();
            if (paint != null) {
                paint.setShader(mGradient);
            }
            mMatrix = new Matrix();
        }
    }


    private int mDistance;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mWidth > 0 && mAnimStart && mMatrix != null) {
            mDistance += mWidth / mSpeed;
            if (mDistance > mWidth) {
                mDistance = -mWidth;
            }
            mMatrix.setTranslate(mDistance, 0);
            mGradient.setLocalMatrix(mMatrix);
            postInvalidateDelayed(50);
        }
    }
}