package com.hyg.check.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 功能描述：数据库创建帮助类，创建数据库
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DatabaseHelper(Context context,String name){
		this(context,name,null,DBInfo.VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        /**
         * 创建成绩表
         */
        String grade ="create table grade(year varchar(10),session varchar(10), frequency varchar(5),s_id varchar(10),grade_bed int(4),grade_wall int(4),grade_desk int(4),grade_ground int(4),grade_security varchar(4),grade_electricity varchar(4),grade_public int(4),total int(4),flag varchar(2),primary key(s_id,year,session,frequency));";
        /**
         * 创建学生信息表
         */
        String student="create table student(s_college varchar(20),s_class varchar(20),dormitory_id varchar(20),bed_id varchar (5),s_id varchar(10),s_name varchar(20),s_sex varchar(5),foreign key (s_id) references grade(s_id));";


        /**
         * 创建标志表
         */
        String check="create table checkflag(s_id varchar(10),check_flag varchar(5),primary key(s_id))";

        /**
         * 创建复查人员名单
         */
        String review="create table review(s_college varchar(20),year varchar(6),session varchar(6),frequency varchar(6),s_id varchar(6),bed_id varchar(10),s_name varchar(10),dormitory_id varchar(10),s_sex varchar(10))";

        db.execSQL(grade);
        db.execSQL(student);
        db.execSQL(review);
        db.execSQL(check);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		

	}

}
