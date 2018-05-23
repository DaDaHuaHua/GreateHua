package com.example.song.autodb.db;

import java.util.List;

/**
 * Created by SongH on 2018/5/22.
 */

public interface IBaseDao<T> {
    long insert(T entity);

//    long update(T entity, T where);
//
//    int delete(T where);
//
//    List<T> query(T where);
//
//    List<T> query(T where, String orderBy, Integer startIndex, Integer limit);
//
//    List<T> query(String sql);
}
