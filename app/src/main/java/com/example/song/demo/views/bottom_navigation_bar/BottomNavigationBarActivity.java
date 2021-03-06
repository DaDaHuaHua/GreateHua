package com.example.song.demo.views.bottom_navigation_bar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.example.song.R;
import com.example.song.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by PVer on 2017/4/9.
 *
 */

public class BottomNavigationBarActivity extends BaseActivity {

    @BindView(R.id.layout_content)
    FrameLayout mLayoutContent;
    @BindView(R.id.layout_bottom)
    BottomNavigationBar mBottomNavigationBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botton_navigation);
    }


}
