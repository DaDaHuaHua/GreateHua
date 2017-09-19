package com.example.song.demo.views.custom_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.commonlibrary.mobile.Mobile;
import com.example.song.R;
import com.example.song.base.BaseActivity;

import butterknife.BindView;

/**
 * <p> Created by 宋华 on 2017/9/13.
 */
public class CustomViewActivity extends BaseActivity {

    @BindView(R.id.my_view)
    View myView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
    }


    private static final String TAG = "CustomViewActivity";

}
