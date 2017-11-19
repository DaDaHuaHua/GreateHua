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
import com.example.song.demo.image.TextToBitmapActivity;
import com.example.song.demo.views.rebound.ReboundActivity;
import com.example.song.demo.views.shared_element.SharedElementActivityA;
import com.example.song.demo.views.vector.VectorActivity;
import com.example.song.homework.activity.BinarySearchTreeActivity;
import com.example.song.homework.activity.HaffmanTreeActivity;
import com.example.song.homework.activity.HashMapActivity;
import com.example.song.homework.activity.LinkedListActivity;
import com.example.song.homework.bean.HaffmanTree;

import butterknife.BindView;

/**
 * Created by PVer on 2017/4/2.
 */

public class HomeworkMain extends BaseActivity {
    @BindView(R.id.list)
    ListView mLv;

    private String[] menu = {"实现单链表","HashMap","BinarySearchTree","HaffmanTree"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_view);
        mLv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu) {
        });
        mLv.setOnItemClickListener(new MyItemClickListener());
    }

    private class MyItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    startActivity(new Intent(HomeworkMain.this, LinkedListActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(HomeworkMain.this, HashMapActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(HomeworkMain.this, BinarySearchTreeActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(HomeworkMain.this, HaffmanTreeActivity.class));
                    break;
                default:
                    break;
            }
        }
    }
}
