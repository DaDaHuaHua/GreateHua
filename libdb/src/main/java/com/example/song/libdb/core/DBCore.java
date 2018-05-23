package com.example.song.libdb.core;

import android.content.Context;

import com.example.song.libdb.core.options.IInsertOption;
import com.example.song.libdb.core.options.IQueryOption;
import com.example.song.libdb.core.options.IRemoveOption;
import com.example.song.libdb.core.options.IUpdateOption;
import com.example.song.libdb.core.options.OptionFactory;

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

    @Override
    public IQueryOption query(Class clz) {
        return OptionFactory.query(clz);
    }

    @Override
    public IUpdateOption update(Class clz) {
        return OptionFactory.update(clz);
    }

    @Override
    public IInsertOption insert(Class clz) {
        return OptionFactory.insert(clz);
    }

    @Override
    public IRemoveOption remove(Class clz) {
        return OptionFactory.remove(clz);
    }


}
