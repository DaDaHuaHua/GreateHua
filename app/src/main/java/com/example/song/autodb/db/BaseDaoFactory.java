package com.example.song.autodb.db;

import android.database.sqlite.SQLiteDatabase;

import com.example.song.BuildConfig;

/**
 * Created by SongH on 2018/5/22.
 */

public class BaseDaoFactory {

    private static final BaseDaoFactory mInstance = new BaseDaoFactory();

    public static BaseDaoFactory getInstance() {
        return mInstance;
    }

    private SQLiteDatabase mSQLiteDatabase;
    private String sqliteDataBasePath;

    private BaseDaoFactory() {
        sqliteDataBasePath = "data/data/" + BuildConfig.APPLICATION_ID + "/test.db";
        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqliteDataBasePath, null);
    }

    public <T> BaseDao<T> getBaseDao(Class<T> entityClass) {
        BaseDao<T> baseDao = null;
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(mSQLiteDatabase, entityClass);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return baseDao;
    }
}
