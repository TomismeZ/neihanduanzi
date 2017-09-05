package com.bmob.im.demo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OperateDb {
	
	DBOpenHelper dbOpenHelper;
	
	public OperateDb(Context context){
		dbOpenHelper=new DBOpenHelper(context);
	}
	
	//��ɾ��
	public void updateDB(String sql,Object...objects){
		//�������ݿ�
		SQLiteDatabase db=dbOpenHelper.getWritableDatabase();

		
//		contentPorvider�����ṩ��  ���ݿ�		
//		db.insert(table, nullColumnHack, values)
//		db.delete(table, whereClause, whereArgs)
//		db.update(table, values, whereClause, whereArgs)
		//... [] һ������
		db.execSQL(sql,objects);
		db.close();
		
	}
	//��ѯ
	public Cursor queryDB(String sql,String...selectionArgs){
		
		SQLiteDatabase db=dbOpenHelper.getReadableDatabase();
		Cursor cursor=db.rawQuery(sql, selectionArgs);
		return cursor;
		
//		db.close();
		
		
		
	}

}
