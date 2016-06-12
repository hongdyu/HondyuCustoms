package com.yhd.activity;

import com.yhd.R;
import com.yhd.util.PhoneInfo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 获取用户手机信息并显示
 * 
 * @author hondyu
 *
 * @version 创建时间：2016年4月14日 上午10:57:13
 */
public class ObtainUserPhoneActivity extends BaseActivity implements
		OnClickListener {

	private TextView tv_show_phone_info, tv_tip;
	private Button btn_show_tip;
	private boolean isShowTip = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.obtain_user_phone);
		initView();
		initData();
	}

	private void initView() {
		tv_show_phone_info = (TextView) findViewById(R.id.tv_show_phone_info);
		tv_tip = (TextView) findViewById(R.id.tv_tip);
		btn_show_tip = (Button) findViewById(R.id.btn_show_tip);
		btn_show_tip.setOnClickListener(this);
	}

	private void initData() {
		PhoneInfo siminfo = new PhoneInfo(ObtainUserPhoneActivity.this);
		System.out.println("getProvidersName:" + siminfo.getProvidersName());
		System.out.println("getNativePhoneNumber:"
				+ siminfo.getNativePhoneNumber());
		System.out.println("------------------------");
		System.out.println("getPhoneInfo:" + siminfo.getPhoneInfo());
		tv_show_phone_info.setText("显示手机设备信息：\n" + siminfo.getPhoneInfo());
	}

	@Override
	public void onClick(View v) {
		if (v == btn_show_tip) {
			if (!isShowTip) {
				tv_tip.setVisibility(View.VISIBLE);
				isShowTip = true;
			} else {
				tv_tip.setVisibility(View.GONE);
				isShowTip = false;
			}
		}
	}

}
