package com.example.song.demo.image;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.commonlibrary.mobile.DensityUtil;
import com.example.commonlibrary.util.BitmapUtil;
import com.example.commonlibrary.util.StringUtil;
import com.example.commonlibrary.util.bean.TextConfig;
import com.example.song.R;
import com.example.song.base.BaseActivity;
import com.example.song.homework.bean.HashMap;
import com.example.song.homework.bean.SingleLinkedList;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 33105 on 2017/10/18.
 */

public class TextToBitmapActivity extends BaseActivity {
    @BindView(R.id.et_content)
    EditText mEtText;
    @BindView(R.id.iv_show)
    ImageView mIvShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_bitmap);
    }

    @OnClick(R.id.tv_create_bitmap)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_create_bitmap:
                Bitmap bitmap = createBitmap();
                if (bitmap == null) {
                    mIvShow.setImageResource(R.drawable.bg_watermark);
                } else {
                    mIvShow.setImageBitmap(createBitmap());
                }
                break;
            default:
                break;
        }
    }

    private Bitmap createBitmap() {
        String text = mEtText.getText().toString();
        if (StringUtil.isBlank(text)) {
            text = "掌门全球直播";
        }
        TextConfig config = new TextConfig();
        config.textColor = Color.WHITE;
        config.text = text;
        return BitmapUtil.createBitmap(getResources(), R.drawable.icon_text_simple, config, Color.WHITE, DensityUtil.sp2px(15));
    }


}
