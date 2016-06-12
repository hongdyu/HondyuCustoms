package com.yhd.activity;

import com.yhd.activity.MainActivity;
import com.yhd.R;
import com.yhd.fragment.CoachFragment;
import com.yhd.fragment.DiscoveryFragment;
import com.yhd.fragment.DrivingExamFragment;
import com.yhd.fragment.DrivingGroupFragment;
import com.yhd.helper.CustomDialogActivity;
import com.yhd.view.SlidingMenu;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.jpush.android.api.JPushInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private Button btn_profile;
//			addBtn, queryBtn, updateBtn, deleteBtn, btn_sendAll,
//			btn_dialog, btn_sendMsgToAndroid, btn_register, btn_myLocation,
//			btn_homepage, btn_map, btn_headimage;
	private RelativeLayout rl_delete_item;
	private TextView tv_driving_exam, tv_coach, tv_driving_exam_group,
			tv_discovery;
	@SuppressWarnings("unused")
	private SlidingMenu mSlidingMenu;
	BmobPushManager<BmobInstallation> bmobPushManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 创建推送消息的对象
		bmobPushManager = new BmobPushManager<BmobInstallation>(this);
		initView();
		initMainButtom();
		String regId = JPushInterface.getRegistrationID(getApplicationContext()); 
		Toast.makeText(getApplicationContext(), "getRegistrationID: "+regId, Toast.LENGTH_LONG).show();
	}
	
	private void initView() {
		btn_profile = (Button) findViewById(R.id.btn_profile);
		btn_profile.setOnClickListener(this);
		tv_driving_exam = (TextView) findViewById(R.id.tv_driving_exam);
		tv_driving_exam.setOnClickListener(this);
		tv_coach = (TextView) findViewById(R.id.tv_coach);
		tv_coach.setOnClickListener(this);
		tv_driving_exam_group = (TextView) findViewById(R.id.tv_driving_exam_group);
		tv_driving_exam_group.setOnClickListener(this);
		tv_discovery = (TextView) findViewById(R.id.tv_discovery);
		tv_discovery.setOnClickListener(this);

//		mSlidingMenu = (SlidingMenu) findViewById(R.id.id_menu);
		/*
		 * addBtn = (Button) findViewById(R.id.addBtn); queryBtn = (Button)
		 * findViewById(R.id.queryBtn); updateBtn = (Button)
		 * findViewById(R.id.updateBtn); deleteBtn = (Button)
		 * findViewById(R.id.deleteBtn); btn_sendAll = (Button)
		 * findViewById(R.id.btn_sendAll); btn_sendMsgToAndroid = (Button)
		 * findViewById(R.id.btn_sendMsgToAndroid); btn_register = (Button)
		 * findViewById(R.id.btn_register); btn_myLocation = (Button)
		 * findViewById(R.id.btn_myLocation); btn_homepage = (Button)
		 * findViewById(R.id.btn_homepage); btn_map = (Button)
		 * findViewById(R.id.btn_map); btn_headimage = (Button)
		 * findViewById(R.id.btn_headimage); btn_dialog = (Button)
		 * findViewById(R.id.btn_dialog);
		 * 
		 * addBtn.setOnClickListener(this); queryBtn.setOnClickListener(this);
		 * updateBtn.setOnClickListener(this);
		 * deleteBtn.setOnClickListener(this);
		 * btn_sendAll.setOnClickListener(this);
		 * btn_sendMsgToAndroid.setOnClickListener(this);
		 * btn_register.setOnClickListener(this);
		 * btn_myLocation.setOnClickListener(this);
		 * btn_homepage.setOnClickListener(this);
		 * btn_map.setOnClickListener(this);
		 * btn_headimage.setOnClickListener(this);
		 * btn_dialog.setOnClickListener(this);
		 */
	}

	/**
	 * 初始化底部菜单栏
	 */
	private void initMainButtom() {
		changeFragment(new DrivingExamFragment());
		initBottomTextview(tv_driving_exam);
	}

	private void initBottomTextview(TextView tv_checked) {
		tv_driving_exam.setSelected(false);
		tv_coach.setSelected(false);
		tv_driving_exam_group.setSelected(false);
		tv_discovery.setSelected(false);
		tv_checked.setSelected(true);
	}

	@Override
	public void onClick(View v) {
		if(v == btn_profile){
			startActivity(new Intent(MainActivity.this,ProfileActivity.class));
		}else if (v == tv_driving_exam) {
			changeFragment(new DrivingExamFragment());
			initBottomTextview(tv_driving_exam);
		} else if (v == tv_coach) {
			changeFragment(new CoachFragment());
			initBottomTextview(tv_coach);
		} else if (v == tv_driving_exam_group) {
			changeFragment(new DrivingGroupFragment());
			initBottomTextview(tv_driving_exam_group);
		} else if (v == tv_discovery) {
			changeFragment(new DiscoveryFragment());
			initBottomTextview(tv_discovery);
		}
	}

	private void changeFragment(Fragment targetFragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_fragment, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}
	
	/**
	 * 显示dialog
	 */
	@SuppressWarnings("unused")
	private void showMyDialog() {
		// 创建Dialog并设置样式主题
		CustomDialogActivity customDialog = new CustomDialogActivity(this,
				R.style.dialog);
		Window win = customDialog.getWindow();
		LayoutParams params = new LayoutParams();
		params.x = 0;// 设置x坐标
		params.y = 0;// 设置y坐标
		win.setAttributes(params);
		customDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		customDialog.show();
		rl_delete_item = (RelativeLayout) customDialog
				.findViewById(R.id.rl_delete_item);
		rl_delete_item.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		String regId = JPushInterface.getRegistrationID(getApplicationContext()); 
		Toast.makeText(getApplicationContext(), "getRegistrationID: "+regId, Toast.LENGTH_LONG).show();
		super.onResume();
	}
	
	
}
