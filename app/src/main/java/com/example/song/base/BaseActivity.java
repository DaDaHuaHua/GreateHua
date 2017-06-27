package com.example.song.base;

import android.view.View;

import com.example.commonlibrary.base.activity.CommonActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Song on 2016/11/29.
 */

public class BaseActivity extends CommonActivity {
    private Unbinder mUnbinder;

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
