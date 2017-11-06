package com.example.song.demo.simple;

import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.commonlibrary.base.fragment.BaseFragment;
import com.example.song.R;
import com.example.song.demo.views.material_design.CoordinatorLayoutMain;
import com.example.song.demo.views.recycler_view.RecyclerViewMain;

import java.util.ArrayList;
import java.util.List;


/**
 * <p> Created by 宋华 on 2017/11/2.
 */
public class SimpleListFragment extends BaseFragment {


    private RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<>();


    public static SimpleListFragment newInstance() {
        Bundle args = new Bundle();
        SimpleListFragment fragment = new SimpleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.simple_recycler_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListData();
    }

    private void initListData() {
        for (int i = 0; i < 100; i++) {
            mData.add("this is " + i);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(new MyAdapter(getActivity(),mData));
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
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
