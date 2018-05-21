package com.example.song.libdb.core;

/**
 * Created by 33105 on 2018/5/16.
 */

public class DB {

    public static IDataBase getDB() {
        return DBCore.getInstance();
    }


}
