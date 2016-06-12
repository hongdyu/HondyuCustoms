package com.yhd.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "database.db";// 数据库名称
	public static final int VERSION = 1;
	public static final String TABLE_NAME = "person";// 数据表名称
	private Context context;

	public SQLHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	// 数据库第一次被创建时onCreate会被调用
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS person"
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age INTEGER, info TEXT)";
		db.execSQL(sql);
	}

	 //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "ALTER TABLE person ADD COLUMN other STRING";
		db.execSQL(sql);
	}

}
