package com.example.song.demo.views.material_design;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.song.R;
import com.example.song.base.BaseActivity;
import com.example.song.demo.simple.SimpleListFragment;

import butterknife.BindView;

/**
 * Created by PVer on 2017/4/25.
 * 测试coordinatorLayout
 */

public class CoordinatorLayoutMain extends BaseActivity {

    @BindView(R.id.content)
    NestedScrollView mLayoutContent;

    @BindView(R.id.iv_top)
    ImageView mIvContent;
    SimpleListFragment mListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_main);
        initView();
    }

    private void initView() {
        mLayoutContent.setFillViewport(true);
        mListFragment = SimpleListFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, mListFragment).commit();
    }
}
