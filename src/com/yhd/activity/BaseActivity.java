package com.yhd.activity;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

public class BaseActivity extends Activity {

	private String Bmob_AppId = "99b1d0fb6b70474eec41d3b241a55f6b";
	Toast mToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 初始化 Bmob SDK
		Bmob.initialize(this, Bmob_AppId);
		// 使用推送服务时的初始化操作
		BmobInstallation.getCurrentInstallation(this).save();
		// 启动推送服务
		BmobPush.startWork(this, Bmob_AppId);
	}

	public void ShowToast(String text) {
		if (TextUtils.isEmpty(text)) {
			if (mToast == null) {
				mToast = Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_LONG);
			} else {
				mToast.setText(text);
			}
			mToast.show();
		}
	}
}
