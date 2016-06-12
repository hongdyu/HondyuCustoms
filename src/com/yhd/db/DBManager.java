package com.yhd.db;

import java.util.ArrayList;
import java.util.List;

import com.yhd.enity.Person;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {
	private SQLHelper sqlHelper;
	private SQLiteDatabase db;

	public DBManager(Context context) {
		sqlHelper = new SQLHelper(context);
		db = sqlHelper.getWritableDatabase();
	}

	/**
	 * add person
	 * 
	 * @param persons
	 */
	public void add(List<Person> persons) {
		db.beginTransaction();// 开始事务
		try {
			for (Person person : persons) {
				String sql = "INSERT INTO person VALUES(null,?,?,?)";
				db.execSQL(sql, new Object[] { person.name, person.age,
						person.info });
			}
			db.setTransactionSuccessful();// 设置事务成功完成
			Log.e("设置事务成功完成", "add person success");
		} finally {
			db.endTransaction();// 结束事务
		}
	}

	/**
	 * udpate person's age
	 * 
	 * @param person
	 */
	public void updateAge(Person person) {
		ContentValues cv = new ContentValues();
		cv.put("age", person.age);
		db.update("person", cv, "name = ?", new String[] { person.name });
		Log.e("update事务成功完成", "update person success");
	}

	/**
	 * delete old person
	 */
	public void deleteOldPerson(Person person) {
		
		db.delete("person", "age = ?",
				new String[] { String.valueOf(person.age) });
		Log.e("delete 事务成功完成", "delete person success");
	}
	
	 /** 
     * query all persons, return list 
     * @return List<Person> 
     */  
    public List<Person> query() {  
        ArrayList<Person> persons = new ArrayList<Person>();  
        Cursor c = queryTheCursor();  
        while (c.moveToNext()) {  
            Person person = new Person();  
            person._id = c.getInt(c.getColumnIndex("_id"));  
            person.name = c.getString(c.getColumnIndex("name"));  
            person.age = c.getInt(c.getColumnIndex("age"));  
            person.info = c.getString(c.getColumnIndex("info"));  
            persons.add(person);  
        }  
        c.close();  
        return persons;  
    }  
      
    /** 
     * query all persons, return cursor 
     * @return  Cursor 
     */  
    public Cursor queryTheCursor() {  
        Cursor c = db.rawQuery("SELECT * FROM person", null);  
        return c;  
    }  
      
    /** 
     * close database 
     */  
    public void closeDB() {  
        db.close();  
    }  
}
