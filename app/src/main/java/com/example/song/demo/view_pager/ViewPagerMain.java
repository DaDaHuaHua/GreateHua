package com.example.song.demo.view_pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.commonlibrary.util.ToastUtil;
import com.example.song.R;
import com.example.song.base.BaseActivity;

/**
 * <p> Created by 宋华 on 2017/9/1.
 */
public class ViewPagerMain extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View v = getLayoutInflater().inflate(R.layout.item_view_pager_demo, container,false);
                TextView tv = (TextView) v.findViewById(R.id.tv_item);
                tv.setText("-- " + position + " --");
                container.addView(v);
                return v;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ToastUtil.showMessage("当前是 ： " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
