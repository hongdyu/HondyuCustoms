package com.yhd.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yhd.R;
import com.yhd.common.Tools;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends BaseActivity implements OnClickListener {

	final static String SHORTMSG_MODE_NAME = "shortMsg1";
	
	private Button btn_verificationCode, btn_sendMsg, btn_validateMsg;
	private EditText et_shortMsg, et_phone, et_vertificate;
	private MyCount countTime;
	BmobPushManager<BmobInstallation> bmobPushManager;

	private String phoneNum, shortMsg, sendTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		initView();
	}

	private void initView() {
		btn_verificationCode = (Button) findViewById(R.id.btn_verificationCode);
		btn_sendMsg = (Button) findViewById(R.id.btn_sendMsg);
		et_shortMsg = (EditText) findViewById(R.id.et_shortMsg);
		btn_validateMsg = (Button) findViewById(R.id.btn_validateMsg);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_vertificate = (EditText) findViewById(R.id.et_vertificate);

		btn_verificationCode.setOnClickListener(this);
		btn_sendMsg.setOnClickListener(this);
		btn_validateMsg.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_verificationCode:
			sendVertificateCode();
			break;
		case R.id.btn_sendMsg:
			sendCustomMsg();
			break;
		case R.id.btn_validateMsg:
			validateShortMsg();
			break;
		default:
			break;
		}
	}

	/**
	 * 发送自定义内容
	 */
	private void sendCustomMsg() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sendTime = format.format(new Date());
		phoneNum = et_phone.getText().toString();
		shortMsg = et_shortMsg.getText().toString();
		BmobSMS.requestSMS(getApplicationContext(), phoneNum, shortMsg,
				sendTime, new RequestSMSCodeListener() {

					@Override
					public void done(Integer smsId, BmobException ex) {
						if (null == ex) {
							Log.i("bmob", "短信发送成功，短信ID：" + smsId);// 用于查询本次短信发送详情
							Tools.showToast(getApplicationContext(),
									"短信发送成功，短信ID：" + smsId);
						} else {
							Log.i("bmob", "errorCode = " + ex.getErrorCode()
									+ ",errorMsg = " + ex.getLocalizedMessage());
							Tools.showToast(
									getApplicationContext(),
									"errorCode = " + ex.getErrorCode()
											+ ",errorMsg = "
											+ ex.getLocalizedMessage());
						}
					}
				});
	}

	/**
	 * 发送验证码
	 */
	private void sendVertificateCode() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sendTime = format.format(new Date());
		BmobSMS.requestSMSCode(getApplicationContext(), et_phone.getText().toString(), SHORTMSG_MODE_NAME, new RequestSMSCodeListener() {
			
			@Override
			public void done(Integer smsId, BmobException ex) {
				if(null == ex)
				{
					Log.i("smile", "短信id: "+smsId);
					Tools.showToast(getApplicationContext(), "短信id: "+smsId);
				}
			}
		});
		countTime = new MyCount(60000, 1000);
		countTime.start();
	}
	
	/**
	 * 定义一个倒计时的内部类
	 */
	class MyCount extends CountDownTimer {

		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			btn_verificationCode.setText(millisUntilFinished/1000 + " 秒后重新发送");
			btn_verificationCode.setEnabled(false);
		}

		@Override
		public void onFinish() {
			btn_verificationCode.setText("验证码");
			btn_verificationCode.setEnabled(true);
		}
	}
	
	/**
	 * 验证短信验证码
	 */
	private void validateShortMsg() {
		shortMsg = et_vertificate.getText().toString();
		BmobSMS.verifySmsCode(getApplicationContext(), et_phone.getText().toString(), shortMsg, new VerifySMSCodeListener() {

			@Override
			public void done(BmobException ex) {
				if (null == ex) {
					Log.i("bmob", "验证成功！");
					Tools.showToast(getApplicationContext(), "验证码验证成功！");
				} else {
					Log.i("bmob", "验证失败：code=" + ex.getErrorCode() + ",msg="
							+ ex.getLocalizedMessage());
					Tools.showToast(
							getApplicationContext(),
							"验证失败：code=" + ex.getErrorCode() + ",msg="
									+ ex.getLocalizedMessage());
				}
			}
		});
	}

}
