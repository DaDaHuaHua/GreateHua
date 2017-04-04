package com.example.song.greathua.mytest.homework;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.song.greathua.R;
import com.example.song.greathua.base.CommonActivity;
import com.example.song.greathua.mytest.homework.shared_element.SharedElementActivityA;
import com.example.song.greathua.mytest.homework.vector.VectorActivity;

import butterknife.BindView;

/**
 * Created by PVer on 2017/4/2.
 *
 */

public class HomeworkMain extends CommonActivity{
    @BindView(R.id.list)
    ListView mLv;

    private String[] menu = {"Vector","SharedElement"};

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
                    startActivity(new Intent(HomeworkMain.this, VectorActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(HomeworkMain.this , SharedElementActivityA.class));
                    break;
            }
        }
    }
}
