package com.example.song.libdb.core;

import android.content.Context;

import com.example.song.libdb.core.options.IInsertOption;
import com.example.song.libdb.core.options.IQueryOption;
import com.example.song.libdb.core.options.IRemoveOption;
import com.example.song.libdb.core.options.IUpdateOption;

/**
 * Created by 33105 on 2018/5/16.
 */

public interface IDataBase {


    void init(Context appContext);


    IQueryOption query(Class clz);

    IUpdateOption update(Class clz);

    IInsertOption insert(Class clz);

    IRemoveOption remove(Class clz);

}
