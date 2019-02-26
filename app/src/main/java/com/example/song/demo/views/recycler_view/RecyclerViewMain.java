package com.example.song.demo.views.recycler_view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.commonlibrary.util.KeyboardUtil;
import com.example.song.R;
import com.example.song.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by PVer on 2017/4/12.
 * 学习使用RecycleView
 */

public class RecyclerViewMain extends BaseActivity {

    private List<String> mDatas;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    private EditText mEtNum;
    private TextView mTvOk;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_main);
        initData();
        mAdapter = new MyAdapter(this, mDatas);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycler);
        mEtNum = (EditText) findViewById(R.id.et_num);
        mTvOk = (TextView) findViewById(R.id.tv_ok);
        mTvOk.setOnClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        KeyboardUtil.hideBottomNavigation(this);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'Z'; i++) {
            mDatas.add("" + (char) i);
        }
//        for (int i = 0; i < 100; i++) {
//            mDatas.add("" + i);
//        }
    }

    private void newData(int num) {
        List<String> newData = new ArrayList<>();
        for (int i = 'A'; i < 'Z'; i++) {
            newData.add("" + (char) i);
        }
        if (num == 99) {
            newData.add("new Data");
        } else if (num == 0) {
            newData.add(0, "new Data");
            newData.add(0, "new Data");
            newData.add(0, "new Data");
            newData.add(0, "new Data");
        } else {
            int a = new Random().nextInt(200);
            newData.set(num, a + "");
        }

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MyDiffCallback(mDatas, newData), true);


        result.dispatchUpdatesTo(mAdapter);
        mDatas.clear();
        mDatas.addAll(newData);
    }

    class MyDiffCallback extends DiffUtil.Callback {

        private List<String> mOldData;
        private List<String> mNewData;

        public MyDiffCallback(List<String> oldData, List<String> newData) {
            mOldData = oldData;
            mNewData = newData;
        }

        @Override
        public int getOldListSize() {
            return mOldData.size();
        }

        @Override
        public int getNewListSize() {
            return mNewData.size();
        }

        @Override
        public boolean areItemsTheSame(int i, int i1) {
            return TextUtils.equals(mOldData.get(i), mNewData.get(i1));
        }

        @Override
        public boolean areContentsTheSame(int i, int i1) {
            return TextUtils.equals(mOldData.get(i), mNewData.get(i1));
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tv_ok) {
            int num = Integer.parseInt(mEtNum.getText().toString());
            newData(num);
        }
    }



    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private Context mContext;
        private LayoutInflater mInflater;
        private List<String> mDatas;

        private MyAdapter(Context context, List<String> datas) {
            this.mContext = context;
            this.mDatas = datas;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.item_recycle_view_main, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.id_num);
        }
    }
}
