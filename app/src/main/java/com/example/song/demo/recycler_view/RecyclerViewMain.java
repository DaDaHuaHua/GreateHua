package com.example.song.demo.recycler_view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.song.R;
import com.example.song.base.CommonActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PVer on 2017/4/12.
 * 学习使用RecycleView
 */
public class RecyclerViewMain extends CommonActivity {

    private List<String> mDatas;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_main);
        initData();
        mAdapter = new MyAdapter(this, mDatas);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycler);
        //  mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(10, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'Z'; i++) {
            mDatas.add("" + (char) i);
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
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclerView.removeViewInLayout(v);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        View itemView;

        MyViewHolder(final View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.id_num);
            this.itemView = itemView;
        }
    }
}
