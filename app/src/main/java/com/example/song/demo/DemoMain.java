package com.example.song.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.song.R;
import com.example.song.base.BaseActivity;
import com.example.song.demo.views.ViewsMain;
import com.example.song.demo.views.dragsort.DSMain;
import com.example.song.demo.views.material_design.CoordinatorLayoutMain;

import com.example.song.demo.views.dialogfragment.DialogFragmentMain;
import com.example.song.demo.media.PlayerMain;
import com.example.song.demo.views.recycler_view.RecyclerViewMain;
import com.example.song.demo.views.view_pager.ViewPagerMain;
import com.example.song.kotlin.KotlinMain;

import butterknife.BindView;

/**
 * Created by PVer on 2017/4/2.
 *
 */

public class DemoMain extends BaseActivity {
    @BindView(R.id.list)
    ListView mLv;

    private String[] menu = {"播放器","views","Kotlin"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_view);
        mLv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu){
        });
        mLv.setOnItemClickListener(new MyItemClickListener());
    }

    private void startActivity(Class<?> c){
        startActivity(new Intent(this,c));
    }

    private class MyItemClickListener implements  AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    startActivity(PlayerMain.class);
                    break;
                case 1:
                    startActivity(ViewsMain.class);
                    break;
                case 2:
                    startActivity(KotlinMain.class);
                    break;
            }
        }
    }
}
