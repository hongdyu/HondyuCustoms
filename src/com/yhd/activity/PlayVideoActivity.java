package com.yhd.activity;

import com.yhd.R;
import com.yhd.view.CustomerVideoView;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;

public class PlayVideoActivity extends BaseActivity{
	
	public static final String KEY_FILE_PATH_URI = "filePathUri";
	private MediaController mediaController;
	private CustomerVideoView mCustomerVideoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_play_video);
		initView();
		initData();
	}

	private void initView() {
		mediaController = new MediaController(this);
		mCustomerVideoView = (CustomerVideoView) findViewById(R.id.videoView);
		 //VideoView与MediaController进行关联 
		mCustomerVideoView.setMediaController(mediaController);
	}
	
	private void initData() {
		Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"Video/Ring.mp4");
		mCustomerVideoView.setVideoURI(uri);
		mCustomerVideoView.requestFocus();
		mCustomerVideoView.start();
	}
}
