package com.example.song.autodb.db;

import android.database.sqlite.SQLiteDatabase;

import com.example.song.BuildConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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

    public <T extends BaseDao<M>, M> T getBaseDao(Class<T> daoClass, Class<M> entityClass) {
        BaseDao<M> baseDao = null;
        try {
            Constructor<T> constructor = daoClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            baseDao = constructor.newInstance();
            baseDao.init(mSQLiteDatabase, entityClass);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }
}
