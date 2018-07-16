package com.example.song.autodb.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.commonlibrary.util.StringUtil;
import com.example.song.autodb.anno.DBField;
import com.example.song.autodb.anno.DBTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

    private BaseDao() {

    }

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

    @Override
    public long update(T entity, T where) {
        ContentValues contentValues = getContentValues(getValues(entity));
        Condition condition = new Condition(getValues(where));
        return mSQLiteDatabase.update(tableName, contentValues, condition.whereClause, condition.whereArgs);
    }

    @Override
    public int delete(T where) {
        Map<String, String> map = getValues(where);
        Condition condition = new Condition(map);
        return mSQLiteDatabase.delete(tableName, condition.whereClause, condition.whereArgs);
    }

    @Override
    public List<T> query(T where) {
        return query(where, null, null, null);
    }

    @Override
    public List<T> query(T where, String orderBy, Integer startIndex, Integer limit) {
        Map<String, String> map = getValues(where);

        String limitString = null;
        if (startIndex != null && limit != null) {
            limitString = startIndex + " , " + limit;
        }
        Condition condition = new Condition(map);
        Cursor cursor = mSQLiteDatabase.query(tableName, null, condition.whereClause, condition.whereArgs,
                null, null, orderBy, limitString);

        return getResult(cursor,where);
    }

    private List<T> getResult(Cursor cursor, T obj) {
        ArrayList list = new ArrayList();
        Object item = null;
        cursor.moveToFirst();
        while ((cursor.moveToNext())) {
            try {
                item = obj.getClass().newInstance();
                Iterator<Map.Entry<String, Field>> iterator = mCachedMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Field> entry = iterator.next();
                    //列名
                    String columnName = entry.getKey();
                    //取得列名在游标中的位置
                    Integer columnIndex = cursor.getColumnIndex(columnName);

                    Field field = entry.getValue();
                    Class type = field.getType();

                    if (columnIndex != -1) {
                        if (type == String.class) {
                            field.set(item, cursor.getString(columnIndex));
                        } else if (type == Double.class) {
                            field.set(item, cursor.getDouble(columnIndex));
                        } else if (type == Integer.class) {
                            field.set(item, cursor.getInt(columnIndex));
                        } else if (type == Long.class) {
                            field.set(item, cursor.getLong(columnIndex));
                        } else if (type == byte[].class) {
                            field.set(item, cursor.getBlob(columnIndex));
                        } else {
                            continue;
                        }

                    }
                }
                list.add(item);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return list;

    }

    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        Set<String> keys = map.keySet();
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
        for (Field field : mCachedMap.values()) {
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


    public static class Condition {
        private String whereClause;
        private String[] whereArgs;


        public Condition(Map<String, String> whereClause) {
            ArrayList<String> list = new ArrayList<>();

            StringBuilder builder = new StringBuilder();
            builder.append("1=1");

            Set<String> keys = whereClause.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = whereClause.get(key);
                if (value != null) {
                    builder.append(" and " + key + "=?");
                    list.add(value);
                }
            }
            this.whereClause = builder.toString();
            this.whereArgs = list.toArray(new String[list.size()]);
        }
    }
}
