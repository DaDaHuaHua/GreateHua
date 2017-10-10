package com.example.song.demo.views.view_pager;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import com.example.song.demo.views.view_pager.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.internal.ListenerClass;

/**
 * Created by 宋华 on 2017/10/10.
 * <p>
 *
 * <p>实现无限循环的ViewPager</>
 * <p>实现思路：
 *   通过内部维护了一个PagerAdapter（InnerAdapter）,代理外部传入的Adapter实现
 *   eg:
 *   外部数据：
 *            0,1,2,3
 *   代理后的实际数据：
 *            3,0,1,2,3,0
 *
 *   重写方法：
 *            {@link #setAdapter(PagerAdapter)}
 *            {@link #setCurrentItem(int)}
 *            {@link #addOnPageChangeListener(OnPageChangeListener)}
 *            {@link #removeOnPageChangeListener(OnPageChangeListener)}
 *
 * </>
 */
public class PPTHackyViewPager extends HackyViewPager {
    public PPTHackyViewPager(Context context) {
        super(context);
        init();
    }

    public PPTHackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.addOnPageChangeListener(new InnerOnPageChangeListener());
    }

    private InnerPagerAdapter mInnerAdapter;

    @Override
    public void setAdapter(PagerAdapter adapter) {
        mInnerAdapter = new InnerPagerAdapter(adapter);
        super.setAdapter(mInnerAdapter);
        super.setCurrentItem(1);
    }


    @Override
    public void setCurrentItem(int position) {
        if (position >= mInnerAdapter.getCount()) {
            throw new IndexOutOfBoundsException("Index is " + position + " , max is " + mInnerAdapter.getCount());
        }
        super.setCurrentItem(position + 1);
    }


    private List<ViewPager.OnPageChangeListener> mOnPageChangeListeners = new ArrayList<>();

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.mOnPageChangeListeners.add(listener);
    }

    public void removeOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.remove(listener);
        }
    }

    private class InnerOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int position;

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (position == mInnerAdapter.getCount() - 1) {
                    PPTHackyViewPager.super.setCurrentItem(1, false);
                } else if (position == 0) {
                    PPTHackyViewPager.super.setCurrentItem(mInnerAdapter.getCount() - 2, false);
                }
            }
            if (mOnPageChangeListeners != null && mOnPageChangeListeners.size() > 0) {
                for (ViewPager.OnPageChangeListener mPageChangeListener : mOnPageChangeListeners) {
                    mPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if (mOnPageChangeListeners != null && mOnPageChangeListeners.size() > 0) {
                for (ViewPager.OnPageChangeListener mPageChangeListener : mOnPageChangeListeners) {
                    mPageChangeListener.onPageScrolled(arg0, arg1, arg2);
                }
            }
        }

        @Override
        public void onPageSelected(int curPosition) {
            position = curPosition;
            if (mOnPageChangeListeners != null && mOnPageChangeListeners.size() > 0) {
                for (ViewPager.OnPageChangeListener mPageChangeListener : mOnPageChangeListeners) {
                    //最前面一个和最后面一个不能走onPageSelected
                    if (position != 0 && position != mInnerAdapter.getCount() - 1) {
                        mPageChangeListener.onPageSelected(position - 1);
                    }
                }
            }
        }
    }

    private class InnerPagerAdapter extends PagerAdapter {

        private PagerAdapter mOuterAdapter;

        InnerPagerAdapter(PagerAdapter adapter) {
            this.mOuterAdapter = adapter;
            adapter.registerDataSetObserver(new DataSetObserver() {

                @Override
                public void onChanged() {
                    notifyDataSetChanged();
                }

                @Override
                public void onInvalidated() {
                    notifyDataSetChanged();
                }

            });
        }

        @Override
        public int getCount() {
            return mOuterAdapter.getCount() + 2;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return mOuterAdapter.isViewFromObject(arg0, arg1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 0) {
                position = mOuterAdapter.getCount() - 1;
            } else if (position == mOuterAdapter.getCount() + 1) {
                position = 0;
            } else {
                position -= 1;
            }
            return mOuterAdapter.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mOuterAdapter.destroyItem(container, position, object);
        }

        private int mChildCount = 0;

        //解决外部Adapter notify以后数据下一页不刷新的问题
        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object)   {
            if ( mChildCount > 0) {
                mChildCount --;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

    }

}
