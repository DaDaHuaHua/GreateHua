package com.example.song.kotlin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.song.R;
import com.example.song.base.BaseActivity;
import com.example.song.kotlin.demo.GithubList;

import butterknife.BindView;

/**
 */

public class KotlinMain extends BaseActivity {

    @BindView(R.id.list)
    ListView mListView;

    private String[] tags = {"KotlinActivity", "Kotlin协程", "Kotlin-kapt", "anko","DSL" ,"github RecyclerView"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotlin_mian);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tags));
        mListView.setOnItemClickListener(new MyItemClickListener());

    }


    private void startActivity(Class<?> c) {
        startActivity(new Intent(this, c));
    }

    private class MyItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    startActivity(KotlinActivity.class);
                    break;
                case 1:
                    startActivity(KotlinThread.class);
                    break;
                case 2:
                    startActivity(KaptActivity.class);
                    break;
                case 3:
                    startActivity(AnkoActivity.class);
                    break;
                case 4:
                    startActivity(DSLActivity.class);
                    break;
                case 5:
                    startActivity(GithubList.class);
                    break;

            }
        }
    }

}
