package com.yhd.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.yhd.R;
import com.yhd.view.CircleImageView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

public class HeadImageSetActivity extends Activity implements OnClickListener {

	@SuppressWarnings("unused")
	private static final String IMAGE_UNSPECIFIED = "image/*";

	/** 获取到的图片路径 */
	private String picPath;
	@SuppressWarnings("unused")
	private Intent lastIntent;
	private Uri photoUri;
	// 使用照相机拍照获取图片
	public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
	// 使用相册中的图片
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
	/** 从Intent获取图片路径的KEY */
	public static final String KEY_PHOTO_PATH = "photo_path";
	private static final String TAG = "HeadImageSetActivity";

	private CircleImageView imageview_head_image;
	private Button btn_cancel, btn_photo_album, btn_camera;
	Dialog dialog;

	@SuppressWarnings("unused")
	private Bundle mybundle;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	// 创建一个以当前时间为名称的文件
	File tempFile = new File(Environment.getExternalStorageDirectory(),
			getPhotoFileName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.head_image_set);
		initView();
		initData();
	}

	// 使用系统当前日期加以调整作为照片的名称
	@SuppressLint("SimpleDateFormat")
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	private void initView() {
		imageview_head_image = (CircleImageView) findViewById(R.id.imageview_head_image);
		imageview_head_image.setImageResource(R.drawable.ic_launcher);
		imageview_head_image.setOnClickListener(this);
	}

	private void initData() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageview_head_image:
			showSelectPictureDialog();
			break;
		case R.id.btn_cancel:
			dialog.cancel();
			break;
		case R.id.btn_photo_album:
			dialog.cancel();
			getPhotoAlbum();
			break;
		case R.id.btn_camera:
			dialog.cancel();
			openCamera();
			break;
		default:
			break;
		}
	}

	/**
	 * 获取系统相册图片
	 */
	private void getPhotoAlbum() {
		// Intent intent = new Intent(Intent.ACTION_PICK, null);
		// intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
		// IMAGE_UNSPECIFIED);
		// startActivity(intent);
		/***/
		// Intent intent = new
		// Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		// intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
		// Intent intent = new Intent();
		// intent.setType("image/*");
		// intent.setAction(Intent.ACTION_GET_CONTENT);
		// startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);

		/***/

		/*
		 * Intent intent=new
		 * Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
		 * intent.addCategory(Intent.CATEGORY_OPENABLE);
		 * intent.setType("image/*"); startActivityForResult(intent,
		 * SELECT_PIC_BY_PICK_PHOTO);
		 */

		/**   */
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}

	/**
	 * 打开系统相机
	 */
	private void openCamera() {
		// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new
		// File(Environment.
		// getExternalStorageDirectory(), "temp.jpg")));
		// startActivity(intent);
		// 执行拍照前，应该先判断SD卡是否存在
		/*
		 * String SDState = Environment.getExternalStorageState();
		 * if(SDState.equals(Environment.MEDIA_MOUNTED)) { Intent intent = new
		 * Intent
		 * (MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"
		 * // 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 ContentValues values = new
		 * ContentValues(); photoUri =
		 * this.getContentResolver().insert(MediaStore
		 * .Images.Media.EXTERNAL_CONTENT_URI, values);
		 * intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
		 * startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO); }else{
		 * Toast.makeText(this,"内存卡不存在", Toast.LENGTH_LONG).show(); }
		 */

		// 调用系统的拍照功能
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定调用相机拍照后照片的储存路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if(resultCode == Activity.RESULT_OK)
		// {
		// Uri uri = data.getData();
		// Log.e("uri", uri.toString());
		// ContentResolver cr = this.getContentResolver();
		// try {
		// Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
		// /* 将Bitmap设定到ImageView */
		// imageview_head_image.setImageBitmap(bitmap);
		// } catch (FileNotFoundException e) {
		// Log.e("Exception", e.getMessage(),e);
		// }
		// doPhoto(requestCode,data);
		// }

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
			mybundle = bundle;
			Bitmap photo = bundle.getParcelable("data");
			imageview_head_image.setImageBitmap(photo);
		}
	}

	/**
	 * 选择图片后获取图片路径
	 * 
	 * @param requestCode
	 * @param data
	 */
	@SuppressWarnings( "unused" )
	private void doPhoto(int requestCode, Intent data) {
		// 从相册取图片
		if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
			if (null == data) {
				Toast.makeText(this, "选择图片文件出错，请重新选择", Toast.LENGTH_LONG)
						.show();
				return;
			}

			photoUri = data.getData();
			if (null == photoUri) {
				Toast.makeText(this, "选择图片文件出错，photoUri为空", Toast.LENGTH_LONG)
						.show();
				return;
			}
		}

		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		// Cursor cursor = managedQuery(photoUri, pojo,null,null,null);
		Cursor cursor = getContentResolver().query(photoUri, filePathColumn,
				null, null, null);
		if (null != cursor) {
			int columnIndex = cursor.getColumnIndexOrThrow(filePathColumn[0]);
			cursor.moveToFirst();
			picPath = cursor.getString(columnIndex);
			cursor.close();
		}
		Log.i(TAG, "imagePath = " + picPath);

		if (null != picPath
				&& (picPath.endsWith(".png") || picPath.endsWith(".PNG")
						|| picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {
			// lastIntent.putExtra(KEY_PHOTO_PATH, lastIntent);
			// setResult(Activity.RESULT_OK, lastIntent);

			Bitmap bm = BitmapFactory.decodeFile(picPath);
			imageview_head_image.setImageBitmap(bm);
		} else {
			Toast.makeText(this, "选择图片出错或文件格式不符合要求", Toast.LENGTH_LONG).show();
		}
	}

}
