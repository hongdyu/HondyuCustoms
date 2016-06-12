package com.yhd.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yhd.R;
import com.yhd.db.DBManager;
import com.yhd.enity.Person;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SqliteTestActivity extends Activity implements OnClickListener {
	final static String PAGE_TITLE = "SQLite测试";

	private LinearLayout layout_back;
	private TextView tv_page_title;
	private DBManager mgr;
	private ListView listView;
	
	private int deleteAge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqlite_test);
		initView();
	}

	private void initView() {
		layout_back = (LinearLayout) findViewById(R.id.layout_back);
		layout_back.setOnClickListener(this);
		tv_page_title = (TextView) findViewById(R.id.tv_page_title);
		tv_page_title.setText(PAGE_TITLE);
		listView = (ListView) findViewById(R.id.listView);
		// 初始化DBManager
		mgr = new DBManager(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 应用的最后一个Activity关闭时应释放DB
		mgr.closeDB();
	}

	@Override
	public void onClick(View view) {
		if (view == layout_back) {
			finish();
		}
	}
	
	public void add(View view) {  
        ArrayList<Person> persons = new ArrayList<Person>();  
          
        Person person1 = new Person("Ella", 22, "lively girl");  
        Person person2 = new Person("Jenny", 22, "beautiful girl");  
        Person person3 = new Person("Jessica", 23, "sexy girl");  
        Person person4 = new Person("Kelly", 23, "hot baby");  
        Person person5 = new Person("Jane", 25, "a pretty woman");  
          
        persons.add(person1);  
        persons.add(person2);  
        persons.add(person3);  
        persons.add(person4);  
        persons.add(person5);  
          
        mgr.add(persons);  
    }  
      
    public void update(View view) {  
        Person person = new Person();  
        person.name = "Jane";  
        person.age = 30;  
        mgr.updateAge(person);  
    }  
      
    public void delete(View view) {  Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
        final EditText ageEditText = (EditText) LayoutInflater.from(this).inflate(R.layout.edit_text_item,
				null);
        Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
        Dialog dialog = new AlertDialog.Builder(SqliteTestActivity.this)
        .setTitle("删除年龄条件")
        .setView(ageEditText)
        .setPositiveButton("确定", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				deleteAge = Integer.valueOf(((EditText) ageEditText).getText().toString());
				Toast.makeText(getApplicationContext(), "delete age = "+String.valueOf(deleteAge), Toast.LENGTH_SHORT).show();
		        Person person = new Person();  
		        person.age = deleteAge;  
		        mgr.deleteOldPerson(person);
			}
        })
        .setNegativeButton("取消" , null).create();
        dialog.show();
    }  
      
    public void query(View view) {  
        List<Person> persons = mgr.query();  
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();  
        for (Person person : persons) {  
            HashMap<String, String> map = new HashMap<String, String>();  
            map.put("name", person.name);  
            map.put("info", person.age + " years old, " + person.info);  
            list.add(map);  
        }  
        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,  
                    new String[]{"name", "info"}, new int[]{android.R.id.text1, android.R.id.text2});  
        listView.setAdapter(adapter);  
    }  
      
    @SuppressWarnings("deprecation")
	public void queryTheCursor(View view) {  
        Cursor c = mgr.queryTheCursor();  
        startManagingCursor(c); //托付给activity根据自己的生命周期去管理Cursor的生命周期  
        CursorWrapper cursorWrapper = new CursorWrapper(c) {  
            @Override  
            public String getString(int columnIndex) {  
                //将简介前加上年龄  
                if (getColumnName(columnIndex).equals("info")) {  
                    int age = getInt(getColumnIndex("age"));  
                    return age + " years old, " + super.getString(columnIndex);  
                }  
                return super.getString(columnIndex);  
            }  
        };  
        //确保查询结果中有"_id"列  
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,   
                cursorWrapper, new String[]{"name", "info"}, new int[]{android.R.id.text1, android.R.id.text2});  
        ListView listView = (ListView) findViewById(R.id.listView);  
        listView.setAdapter(adapter);  
    }  
}
