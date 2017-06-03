package com.hyg.observe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hyg on 2015/4/18.
 */
public class DataBaseHelper extends SQLiteOpenHelper{
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseHelper(Context context){
        this(context, DBInfo.DB_NAME,null, DBInfo.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建用户成绩表
        String userscore="create table usergrade(frequency varchar(5),total varchar(5));";
        db.execSQL(userscore);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
