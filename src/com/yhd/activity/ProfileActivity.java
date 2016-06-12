package com.yhd.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jpush.android.api.JPushInterface;

import com.yhd.R;
import com.yhd.view.CircleImageView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity implements OnClickListener {
	final static String PAGE_TITLE = "我的资料";

	private LinearLayout layout_back;
	private TextView tv_page_title;
	private CircleImageView imageview_head;
	private Button btn_photo_album, btn_camera, btn_cancel;

	private Dialog dialog;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	// 创建一个以当前时间为名称的文件
	File tempFile = new File(Environment.getExternalStorageDirectory(),
			getPhotoFileName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		getActionBar().hide();
		initView();
		initData();
	}

	private void initData() {
		Intent intent = getIntent();
        if (null != intent) {
	        Bundle bundle = getIntent().getExtras();
	        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
	        String content = bundle.getString(JPushInterface.EXTRA_ALERT);
	        Toast.makeText(ProfileActivity.this, "Title : " + title + "  " + "Content : " + content,Toast.LENGTH_LONG).show();
        }		
	}

	private void initView() {
		layout_back = (LinearLayout) findViewById(R.id.layout_back);
		layout_back.setOnClickListener(this);
		imageview_head = (CircleImageView) findViewById(R.id.imageview_head);
		imageview_head.setOnClickListener(this);
		tv_page_title = (TextView) findViewById(R.id.tv_page_title);
		tv_page_title.setText(PAGE_TITLE);
	}

	// 使用系统当前日期加以调整作为照片的名称
	@SuppressLint("SimpleDateFormat")
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".png";
	}

	@Override
	public void onClick(View view) {
		if (view == layout_back) {
			finish();
		} else if (view == imageview_head) {
			showSelectPictureDialog();
		} else if (view == btn_photo_album) {
			dialog.dismiss();
			getPhotoAlbum();
		} else if (view == btn_camera) {
			dialog.dismiss();
			openCamera();
		} else if (view == btn_cancel) {
			dialog.dismiss();
		}
	}

	@SuppressWarnings("deprecation")
	private void showSelectPictureDialog() {
		View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog,
				null);
		dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = getWindowManager().getDefaultDisplay().getHeight();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
		btn_photo_album = (Button) view.findViewById(R.id.btn_photo_album);
		btn_photo_album.setOnClickListener(this);
		btn_camera = (Button) view.findViewById(R.id.btn_camera);
		btn_camera.setOnClickListener(this);
	}

	/**
	 * 获取系统相册图片
	 */
	private void getPhotoAlbum() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}

	/**
	 * 打开系统相机
	 */
	private void openCamera() {
		// 调用系统的拍照功能
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定调用相机拍照后照片的储存路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:
			startPhotoZoom(Uri.fromFile(tempFile), 150);
			break;
		case PHOTO_REQUEST_GALLERY:
			if (data != null)
				startPhotoZoom(data.getData(), 150);
			break;
		case PHOTO_REQUEST_CUT:
			if (data != null)
				setPicToView(data);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片
	 * 
	 * @param uri
	 * @param size
	 */
	private void startPhotoZoom(Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	// 将进行剪裁后的图片显示到UI界面上
	private void setPicToView(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			imageview_head.setImageBitmap(photo);
		}
	}
}
