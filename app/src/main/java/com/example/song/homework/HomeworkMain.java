package com.example.song.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.song.R;
import com.example.song.base.BaseActivity;
import com.example.song.demo.views.rebound.ReboundActivity;
import com.example.song.demo.views.shared_element.SharedElementActivityA;
import com.example.song.demo.views.vector.VectorActivity;

import butterknife.BindView;

/**
 * Created by PVer on 2017/4/2.
 *
 */

public class HomeworkMain extends BaseActivity {
    @BindView(R.id.list)
    ListView mLv;

    private String[] menu = {};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_view);
        mLv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu){
        });
        mLv.setOnItemClickListener(new MyItemClickListener());
    }

   private class MyItemClickListener implements   AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
