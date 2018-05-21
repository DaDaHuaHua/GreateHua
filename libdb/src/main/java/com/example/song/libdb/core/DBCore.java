package com.example.song.libdb.core;

import android.content.Context;

/**
 * Created by 33105 on 2018/5/20.
 */

public class DBCore implements IDataBase {

    public volatile static DBCore sInstance;

    private DBCore() {

    }

    public static DBCore getInstance() {
        if (sInstance == null) {
            synchronized (DBCore.class) {
                if (sInstance == null) {
                    sInstance = new DBCore();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void init(Context appContext) {
    }
}
