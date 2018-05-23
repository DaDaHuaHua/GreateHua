package com.example.song.autodb.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.song.R;
import com.example.song.autodb.bean.User;
import com.example.song.autodb.db.BaseDao;
import com.example.song.autodb.db.BaseDaoFactory;
import com.example.song.base.BaseActivity;

/**
 * Created by SongH on 2018/5/22.
 */

public class TestAutoDBActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_db);
    }

    public void insertObject(View v){
        BaseDao<User> baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
        baseDao.insert(new User(101,"songhua","123456"));
        Toast.makeText(this,"执行成功!",Toast.LENGTH_LONG).show();
    }
}
