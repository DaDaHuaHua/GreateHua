package com.example.song.libdb;

/**
 * Created by 33105 on 2018/5/16.
 */

public class DBUtil {

    private static volatile DBUtil sInstance;

    private DBUtil() {

    }

    public static DBUtil getInstance() {
        if (sInstance == null) {
            synchronized (DBUtil.class) {
                if (sInstance == null) {
                    sInstance = new DBUtil();
                }
            }
        }
        return sInstance;
    }




}
