package com.example.commonlibrary.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

/**
 * Created by Song on 2016/11/27.
 */

public class BaseActivity extends RxFragmentActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void onClick(View v) {
    }
}
