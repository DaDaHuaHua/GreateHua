package com.example.song.demo.views.custom_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.song.R;
import com.example.song.base.BaseActivity;

import butterknife.BindView;

/**
 * <p> Created by 宋华 on 2017/9/13.
 */
public class CustomViewActivity extends BaseActivity {

    @BindView(R.id.my_view)
    View myView;

    @BindView(R.id.image_view)
    ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        mImageView.setBackgroundResource(R.drawable.lot_btn_reviewingshape);
        mImageView.setSelected(false);
    }


    private static final String TAG = "CustomViewActivity";

}
