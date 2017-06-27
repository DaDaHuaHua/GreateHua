package com.example.song.demo.dragsort;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.song.R;
import com.example.song.base.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.widget.dragsort.dsgv.ChannelModel;
import com.widget.dragsort.dsgv.DragSortGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zz on 2017/5/16.
 */

public class DsgvMain extends BaseActivity {

    private DragSortGridView mDragGridView;
    private ChannelEditAdapter mAdapter;
    private String jsonData = "[{\"id\":\"1\",\"name\":\"\\u660e\\u661f\",\"img\":\"http:\\/\\/7xngyf.com2.z0.glb.qiniucdn.com\\/class_mingxing\"},{\"id\":\"51\",\"name\":\"\\u57ce\\u5e02\",\"img\":\"http:\\/\\/7xngyf.com2.z0.glb.qiniucdn.com\\/class_city\"},{\"id\":\"58\",\"name\":\"\\u751f\\u6d3b\",\"img\":\"http:\\/\\/7xngyf.com2.z0.glb.qiniucdn.com\\/class_shenghuo\"},{\"id\":\"66\",\"name\":\"IT\",\"img\":\"http:\\/\\/7xngyf.com2.z0.glb.qiniucdn.com\\/class_it\"},{\"id\":\"72\",\"name\":\"\\u7403\\u961f\",\"img\":\"http:\\/\\/7xngyf.com2.z0.glb.qiniucdn.com\\/class_qiudui\"},{\"id\":\"39\",\"name\":\"\\u9ad8\\u6821\",\"img\":\"http:\\/\\/7xngyf.com2.z0.glb.qiniucdn.com\\/class_xuexiao\"},{\"id\":\"3\",\"name\":\"\\u54c1\\u724c\",\"img\":\"http:\\/\\/7xngyf.com2.z0.glb.qiniucdn.com\\/class_pingpai\"},{\"id\":\"61\",\"name\":\"\\u6e38\\u620f\",\"img\":\"http:\\/\\/7xngyf.com2.z0.glb.qiniucdn.com\\/class_youxi\"}]";
    private List<ChannelModel> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_edit);
        mDragGridView= (DragSortGridView) findViewById(R.id.mDragGridView);
        Gson gson=new Gson();
        dataList=gson.fromJson(jsonData,new TypeToken<ArrayList<ChannelModel>>(){}.getType());
        mAdapter=new ChannelEditAdapter(this,dataList);
        mDragGridView.setAdapter(mAdapter);
    }
}
