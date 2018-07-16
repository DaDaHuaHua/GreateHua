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

import java.util.Random;

/**
 * Created by SongH on 2018/5/22.
 */

public class TestAutoDBActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_db);
    }

    public void clickInsert(View v) {
//        BaseDao<User> baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
//        int id = new Random().nextInt(200);
//        baseDao.insert(new User(id, "songhua", "123456"));
//        Toast.makeText(this, "执行成功!", Toast.LENGTH_SHORT).show();
    }


    public void clickUpdate(View view) {
//        BaseDao<User> baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
//        User user = new User(888, "songhua2", "888");
//        User whereUser = new User();
//        whereUser.setName("songhua");
//        baseDao.update(user, whereUser);
//        Toast.makeText(this, "执行成功!", Toast.LENGTH_SHORT).show();
    }

    public  void clickDelete(View v){
//        BaseDao<User> baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
//        User where = new User();
//        where.setId(888);
//        baseDao.delete(where);
//        Toast.makeText(this, "执行成功!", Toast.LENGTH_SHORT).show();
    }
}
