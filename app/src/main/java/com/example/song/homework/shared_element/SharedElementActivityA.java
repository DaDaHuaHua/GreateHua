package com.example.song.homework.shared_element;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.TextView;

import com.example.song.R;
import com.example.song.base.CommonActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by PVer on 2017/4/2.
 *
 */

public class SharedElementActivityA extends CommonActivity {
    @BindView(R.id.tv_search)
     TextView mTvSearch;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared_element_activity_a);

    }

    @OnClick({R.id.tv_search})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_search:
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        mTvSearch,
                       getString(R.string.transition_name_tv_search)
                );
                startActivity(new Intent(SharedElementActivityA.this,SharedElementActivityB.class),optionsCompat.toBundle());
                break;
        }
    }
}
