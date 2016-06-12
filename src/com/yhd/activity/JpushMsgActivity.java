package com.yhd.activity;

import cn.jpush.android.api.JPushInterface;

import com.yhd.R;
import com.yhd.util.ExampleUtil;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 测试JPush消息推送
 * @author hondyu
 *
 * @version 创建时间：2016年4月22日 上午10:12:57
 */
public class JpushMsgActivity extends Activity implements
		OnClickListener {
	
	private Button mInit;
	private Button mSetting;
	private Button mStopPush;
	private Button mResumePush;
	private EditText msgText;
	private TextView mImei,mAppKey,mPackage,mDeviceId,mVersion,tv_registration_id;
	public static boolean isForeground = false;
	
	//for receive customer msg from jpush server 自定义消息
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jpush_msg);
		init();
		initView();
		initData();
		registerMessageReceiver();  // used for receive msg
	}

	private void initView() {
		mImei = (TextView) findViewById(R.id.tv_imei);
		mAppKey = (TextView) findViewById(R.id.tv_appkey);
		mPackage = (TextView) findViewById(R.id.tv_package);
		mDeviceId = (TextView) findViewById(R.id.tv_device_id);
		mVersion = (TextView) findViewById(R.id.tv_version);
		tv_registration_id = (TextView) findViewById(R.id.tv_registration_id);
	    mInit = (Button)findViewById(R.id.init);
		mInit.setOnClickListener(this);
		mStopPush = (Button)findViewById(R.id.stopPush);
		mStopPush.setOnClickListener(this);
		mResumePush = (Button)findViewById(R.id.resumePush);
		mResumePush.setOnClickListener(this);
		mSetting = (Button)findViewById(R.id.setting);
		mSetting.setOnClickListener(this);
		msgText = (EditText)findViewById(R.id.msg_rec);
	}

	private void initData() {
		String udid =  ExampleUtil.getImei(getApplicationContext(), "");
        if (null != udid) mImei.setText("IMEI: " + udid);
        
        String appKey = ExampleUtil.getAppKey(getApplicationContext());
		if (null == appKey) appKey = "AppKey异常";
		mAppKey.setText("AppKey: " + appKey);
		
		String packageName =  getPackageName();
		mPackage.setText("PackageName: " + packageName);
		
		String deviceId = ExampleUtil.getDeviceId(getApplicationContext());
		mDeviceId.setText("deviceId:" + deviceId);
		
		String versionName =  ExampleUtil.GetVersion(getApplicationContext());
		mVersion.setText("Version: " + versionName);
		
		String regId = JPushInterface.getRegistrationID(getApplicationContext()); 
		tv_registration_id.setText("RegisterationId: "+regId);
		Log.d("JPush注册的设备Id：", regId);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.init:
			init();
			break;
		case R.id.setting:
//			Intent intent = new Intent(MainActivity.this, PushSetActivity.class);
//			startActivity(intent);
			break;
		case R.id.stopPush:
			JPushInterface.stopPush(getApplicationContext());
			break;
		case R.id.resumePush:
			JPushInterface.resumePush(getApplicationContext());
			break;
		default:
			break;
		}
	}
	
	// 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
	private void init(){
		 JPushInterface.init(getApplicationContext());
	}
	
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
              String messge = intent.getStringExtra(KEY_MESSAGE);
              String extras = intent.getStringExtra(KEY_EXTRAS);
              StringBuilder showMsg = new StringBuilder();
              showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
              if (!ExampleUtil.isEmpty(extras)) {
            	  showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
              }
              setCustomMsg(showMsg.toString());
			}
		}
	}
	
	private void setCustomMsg(String msg){
		 if (null != msgText) {
			 msgText.setText(msg);
			 msgText.setVisibility(android.view.View.VISIBLE);
        }
	}
	
	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
	}


	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
	}


	@Override
	protected void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}

}
