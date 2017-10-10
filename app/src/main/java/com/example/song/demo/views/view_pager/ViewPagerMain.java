package com.example.song.demo.views.view_pager;

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

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Created by 宋华 on 2017/9/1.
 */
public class ViewPagerMain extends BaseActivity {
    private List<String> mData = new ArrayList<>();
    private PagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        for (int i = 0; i < 2; i++) {
            mData.add(""+i);
        }
        findViewById(R.id.view_add_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = mData.size();
                mData.add(""+size);
                mData.add(""+(size+1));
                mAdapter.notifyDataSetChanged();
                viewPager.setAdapter(mAdapter);
            }
        });

        mAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mData.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View v = getLayoutInflater().inflate(R.layout.item_view_pager_demo, container, false);
                TextView tv = (TextView) v.findViewById(R.id.tv_item);
                tv.setText("-- " + mData.get(position) + " --");
                container.addView(v);
                return v;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        viewPager.setAdapter(mAdapter);
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
