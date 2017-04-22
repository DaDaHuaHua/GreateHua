package com.example.song.mytest.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.song.R;
import com.example.song.base.CommonActivity;
import com.example.song.mytest.demo.dialogfragment.DialogFragmentMain;
import com.example.song.mytest.demo.media.PlayerMain;
import com.example.song.mytest.demo.media.demo.TestActivity;
import com.example.song.mytest.demo.media.player.PiliVideoPlayerActivity;
import com.example.song.mytest.demo.recyclerView.RecyclerViewMain;

import butterknife.BindView;

/**
 * Created by PVer on 2017/4/2.
 *
 */

public class DemoMain extends CommonActivity {
    @BindView(R.id.list)
    ListView mLv;

    private String[] menu = {"播放器","DialogFragment","RecyclerView"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_main);
        mLv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu){
        });
        mLv.setOnItemClickListener(new MyItemClickListener());
    }

    private class MyItemClickListener implements   AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    startActivity(new Intent(DemoMain.this, PlayerMain.class));

                    break;
                case 1:
                    startActivity(new Intent(DemoMain.this, DialogFragmentMain.class));
                    break;
                case 2:
                    startActivity(new Intent(DemoMain.this, RecyclerViewMain.class));
                    break;
            }
        }
    }
}
