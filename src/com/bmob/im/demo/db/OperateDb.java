package com.bmob.im.demo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OperateDb {
	
	DBOpenHelper dbOpenHelper;
	
	public OperateDb(Context context){
		dbOpenHelper=new DBOpenHelper(context);
	}
	
	//增删改
	public void updateDB(String sql,Object...objects){
		//打开了数据库
		SQLiteDatabase db=dbOpenHelper.getWritableDatabase();

		
//		contentPorvider内容提供者  数据库		
//		db.insert(table, nullColumnHack, values)
//		db.delete(table, whereClause, whereArgs)
//		db.update(table, values, whereClause, whereArgs)
		//... [] 一个东西
		db.execSQL(sql,objects);
		db.close();
		
	}
	//查询
	public Cursor queryDB(String sql,String...selectionArgs){
		
		SQLiteDatabase db=dbOpenHelper.getReadableDatabase();
		Cursor cursor=db.rawQuery(sql, selectionArgs);
		return cursor;
		
//		db.close();
		
		
		
	}

}
