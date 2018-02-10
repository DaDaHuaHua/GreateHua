package com.example.song.optimize.bitmap_cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.song.R;
import com.example.song.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by 33105 on 2018/2/2.
 * 图片内存优化
 */

public class BitmapCacheActivity extends BaseActivity {
    @BindView(R.id.lv_list)
    ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_cache);
        ImageCache.getInstance().init(this, getCacheDir().getAbsolutePath());
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.my_gril);
//        i(bitmap);

        mListView.setAdapter(new MayAdapter(this));
    }




    private static final String TAG = "BitmapCacheActivity";

    void i(Bitmap bitmap) {
        Log.i(TAG, "Bitmap: " + bitmap.getWidth() + "x" + bitmap.getHeight() + "内存大小是:" + (bitmap.getByteCount()));
    }
}
