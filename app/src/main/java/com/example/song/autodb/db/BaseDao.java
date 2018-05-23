package com.example.song.autodb.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.commonlibrary.util.StringUtil;
import com.example.song.autodb.anno.DBField;
import com.example.song.autodb.anno.DBTable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by SongH on 2018/5/22.
 */

public class BaseDao<T> implements IBaseDao<T> {

    private SQLiteDatabase mSQLiteDatabase;

    private String tableName;

    private Class<T> entityClass;

    private boolean isInited;

    //<字段名,属性>
    private Map<String, Field> mCachedMap;


    protected boolean init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        this.mSQLiteDatabase = sqLiteDatabase;
        this.entityClass = entityClass;
        if (!isInited) {

            if (entityClass.getAnnotation(DBTable.class) == null) {
                tableName = entityClass.getSimpleName();
            } else {
                tableName = entityClass.getAnnotation(DBTable.class).value();
            }
            if (!sqLiteDatabase.isOpen()) {
                return false;
            }

            String createTableSql = getCreateTableSql();
            if (StringUtil.isNotBlank(createTableSql)) {
                sqLiteDatabase.execSQL(createTableSql);
            }
            mCachedMap = new HashMap<>();
            initCacheMap();
            isInited = true;
        }

        return isInited;
    }

    private void initCacheMap() {
        //1.取所有的字段名
        String sql = "select * from " + tableName + " limit 1,0";//查询所有的列名
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        String[] columnNames = cursor.getColumnNames();
        //2.取所有的成员变量
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
        }
        //遍历每一个字段名，在里面找匹配的成员变量名
        for (String columnName : columnNames) {
            Field columnField = null;
            for (Field field : fields) {
                String fieldName;
                if (field.getAnnotation(DBField.class) != null) {
                    fieldName = field.getAnnotation(DBField.class).value();
                } else {
                    fieldName = field.getName();
                }

                if (fieldName.equals(columnName)) {
                    columnField = field;
                    break;
                }
            }
            if (columnField != null) {
                mCachedMap.put(columnName, columnField);
            }
        }
    }

    private String getCreateTableSql() {
        if (entityClass == null) {
            return null;
        }
        Field[] fields = entityClass.getDeclaredFields();
        if (fields.length <= 0) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        //create table if not exists tb_user(_id INTEGER,name TEXT,password TEXT)
        buffer.append("create table if not exists " + tableName + " (");
        for (Field field : fields) {
            Class type = field.getType();
            if (field.getAnnotation(DBField.class) != null) {
                if (type == String.class) {
                    buffer.append(field.getAnnotation(DBField.class).value() + " TEXT,");
                } else if (type == Integer.class) {
                    buffer.append(field.getAnnotation(DBField.class).value() + " INTEGER,");
                } else if (type == Long.class) {
                    buffer.append(field.getAnnotation(DBField.class).value() + " LONG,");
                } else if (type == Double.class) {
                    buffer.append(field.getAnnotation(DBField.class).value() + " DOUBLE,");
                } else if (type == byte[].class) {
                    buffer.append(field.getAnnotation(DBField.class).value() + " BLOB,");
                } else {
                    continue;
                }
            } else {
                if (type == String.class) {
                    buffer.append(field.getName() + " TEXT,");
                } else if (type == Integer.class) {
                    buffer.append(field.getName() + " INTEGER,");
                } else if (type == Long.class) {
                    buffer.append(field.getName() + " LONG,");
                } else if (type == Double.class) {
                    buffer.append(field.getName() + " DOUBLE,");
                } else if (type == byte[].class) {
                    buffer.append(field.getName() + " BLOB,");
                } else {
                    continue;
                }
            }
        }
        if (buffer.charAt(buffer.length() - 1) == ',') {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        buffer.append(')');
        return buffer.toString();
    }

    @Override
    public long insert(T entity) {
        //        ContentValues contentValues=new ContentValues();
//        contentValues.put("_id",'1');//缓存   _id   id变量
//        contentValues.put("name","jett");
//        sqLiteDatabase.insert(tableName,null, contentValues);
        //准备好ContentValues中需要的数据
        Map<String, String> map = getValues(entity);
        //把数据转移到ContentValues中
        ContentValues values = getContentValues(map);
        //开始插入
        long result = mSQLiteDatabase.insert(tableName, null, values);

        return result;
    }

    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        Set keys = map.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key);
            if (value != null) {
                contentValues.put(key, value);
            }
        }
        return contentValues;
    }

    public Map<String, String> getValues(T entity) {
        HashMap<String, String> map = new HashMap<>();
        //返回的是所有的成员变量,user的成员变量
        Iterator<Field> fieldIterator = mCachedMap.values().iterator();
        while (fieldIterator.hasNext()) {
            Field field = fieldIterator.next();
            field.setAccessible(true);
            //获取成员变量的值
            try {
                Object object = field.get(entity);
                if (object == null) {
                    continue;
                }
                String value = object.toString();
                //获取列名
                String key = null;
                if (field.getAnnotation(DBField.class) != null) {
                    key = field.getAnnotation(DBField.class).value();
                } else {
                    key = field.getName();
                }
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    map.put(key, value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
