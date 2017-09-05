package com.bmob.im.demo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

//打开   关闭数据库
public class DBOpenHelper extends SQLiteOpenHelper{
	private static final String DB_NAME = "neihan.db";
	private static final int VERSION = 1;
	private static final String CREATE_TABLE_PERSON = "create table person (_id integer primary key autoincrement , name varchar(100) , password varchar(20) ,  sex varchar,userimage varchar(200),is_recomment BOOLEAN )";
	private static final String DROP_TABLE_PERSON = "DROP TABLE IF EXISTS person";
//建库
	public DBOpenHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	//建表
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_PERSON);
		db.execSQL("create table joke (id integer primary key autoincrement , avatar varchar(100) , username varchar(20) , content text ," +
				"						favorite integer , bury integer , comment integer, share varchar,shareCount integer,groupId number )");
	}

	//升级，只有当你的数据库版本号升级 后才会执行，否则 不执行
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(DROP_TABLE_PERSON);
		db.execSQL(CREATE_TABLE_PERSON);
	}
	
	
	

}
