package com.example.song.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.commonlibrary.util.ToastUtil;
import com.example.song.R;
import com.example.song.autodb.test.TestAutoDBActivity;
import com.example.song.base.BaseActivity;
import com.example.song.demo.DB.DBMainActivity;
import com.example.song.demo.canvas.DrawBitmapMeshActivity;
import com.example.song.demo.constraintlayout.ConstraintActivity;
import com.example.song.demo.image.TextToBitmapActivity;
import com.example.song.demo.media.PlayerMain;
import com.example.song.demo.views.ViewsMain;
import com.example.song.kotlin.KotlinMain;
import com.example.song.optimize.bitmap_cache.BitmapCacheActivity;

import butterknife.BindView;

/**
 * Created by PVer on 2017/4/2.
 */

public class DemoMain extends BaseActivity {
    @BindView(R.id.list)
    ListView mLv;

    private String[] menu = {"播放器", "views", "Kotlin", "生成图片", "图片内存优化","ObjectBox测试","测试AutoDB","constraint Chain"
            ,"DrawBitmapMesh"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_list_view);
        mLv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu) {
        });
        mLv.setOnItemClickListener(new MyItemClickListener());
    }

    private void startActivity(Class<?> c) {
        startActivity(new Intent(this, c));
    }


    private class MyItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (position) {
                case 0:
                    startActivity(PlayerMain.class);
                    break;
                case 1:
                    startActivity(ViewsMain.class);
                    break;
                case 2:
                    startActivity(KotlinMain.class);
                    break;
                case 3:
                    startActivity(TextToBitmapActivity.class);
                    break;
                case 4:
                    startActivity(BitmapCacheActivity.class);
                    break;
                case 5:
                    startActivity(DBMainActivity.class);
                    break;
                case 6:
                    startActivity(TestAutoDBActivity.class);
                    break;
                case 7:
                    startActivity(ConstraintActivity.class);
                    break;
                case 8:
                    startActivity(DrawBitmapMeshActivity.class);
                    break;
                default:
                    break;
            }
        }
    }
}
