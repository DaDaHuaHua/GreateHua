package com.example.song.greatehua.base;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.commonlibrary.mobile.Mobile;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Song on 2017/1/20.
 *
 */

public  class BaseDialogFragment extends DialogFragment implements View.OnClickListener {
    private Unbinder mUnbinder;
    private boolean mCanFinish;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    protected int getLayoutId() {
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        if (layoutId > 0) {
            View root = LayoutInflater.from(getContext()).inflate(layoutId, container, false);
            mUnbinder = ButterKnife.bind(this, root);
            return root;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getDialog() == null) {
            return;
        }
        Window window = getDialog().getWindow();
        if (window != null) {
            if (getWindowGravity() == Gravity.CENTER) {
                window.setLayout(getDialogWidth(), getDialogHeight());
            } else {
                // 必须添加这一句，否则无法全屏显示
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // 0则全屏显示
                if (getWindowGravity() == 0) {
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                }
                // 其他则WRAP_CONTENT，并设置Gravity
                else {
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, getDialogHeight());
                    window.setGravity(getWindowGravity());
                }
            }
        }
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // 当弹出软键盘的时候按返回键，只会走ACTION_UP，而不会走ACTION_DOWN，这里的判断是为了过滤弹出软键盘点返回的情况。
                    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                        mCanFinish = true;
                        return true;
                    } else if (event.getAction() == KeyEvent.ACTION_UP && mCanFinish) {
                        onBackPressed();
                        mCanFinish = false;
                        return true;
                    }
                }
                return false;
            }
        });
    }

    protected int getDialogHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    protected int getDialogWidth() {
        return Mobile.SCREEN_WIDTH * 6 / 7;
    }

    protected void onBackPressed() {
        dismissAllowingStateLoss();
    }

    public void showAllowingStateLoss(FragmentManager fm, String tag) {
        fm.beginTransaction().add(this, tag).commitAllowingStateLoss();
    }

    /**
     * Dialog显示的位置，0：全屏；{@link Gravity#BOTTOM}：底部；{@link Gravity#TOP}：顶部
     */
    protected int getWindowGravity() {
        return Gravity.CENTER;
    }

    /**
     * 等于<code>findViewById(id)</code>，已经处理过强转，不需要再处理
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T $(View v, int id) {
        return (T) v.findViewById(id);
    }

    /**
     * 为单个或者多个View <code>setOnClickListener(this)</code>
     */
    protected void $c(View v, int... ids) {
        for (int id : ids) {
            v.findViewById(id).setOnClickListener(this);
        }
    }

    /**
     * 1.<code>findViewById(id)</code><br>
     * 2.<code>setOnClickListener(this)</code><br>
     * 3.返回强转过的View
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T $r(View v, int id) {
        T t = (T) v.findViewById(id);
        t.setOnClickListener(this);
        return t;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}