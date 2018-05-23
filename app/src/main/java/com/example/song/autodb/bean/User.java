package com.example.song.autodb.bean;

import com.example.song.autodb.anno.DBField;
import com.example.song.autodb.anno.DBTable;

/**
 * Created by 33105 on 2018/5/22.
 */

@DBTable("tb_user")
public class User {
    @DBField("_id")
  private Integer id;
  private String name;
  private String password;

    public User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
