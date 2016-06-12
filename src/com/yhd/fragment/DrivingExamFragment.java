package com.yhd.fragment;

import com.yhd.R;
import com.yhd.activity.JpushMsgActivity;
import com.yhd.activity.LocationCityActivity;
import com.yhd.activity.ObtainUserPhoneActivity;
import com.yhd.activity.PlayVideoActivity;
import com.yhd.activity.SlideBarTestActivity;
import com.yhd.activity.SqliteTestActivity;
import com.yhd.view.CustomDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class DrivingExamFragment extends Fragment implements OnClickListener {

	private View view;
	private Activity mContext;

	private Button btn_show_dialog,btn_operate_db,btn_slide_bar,btn_location_city,btn_play_video
			,btn_slidebar_dialog,btn_obtain_phone,btn_connect_network,btn_jpush_test;
	@SuppressWarnings("unused")
	private TextView tv_count;
	private EditText et_test;
	@SuppressWarnings("unused")
	private SeekBar mSeekbar;
	private Dialog dialog;
	private CustomDialog slidebarCustomDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_driving_exam, container,
				false);
		mContext = getActivity();
		initView();
		//注册帧听
		NetState receiver = new NetState();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		mContext.registerReceiver(receiver, filter);
		receiver.onReceive(mContext, null);
		return view;
	}
	
	/**
	 * 内部类 注册广播监听网络状态
	 * @author hondyu
	 */
	class NetState extends BroadcastReceiver{

	    @Override
	    public void onReceive(Context context, Intent arg1) {
	        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	        
	        AlertDialog.Builder ab = new AlertDialog.Builder(context);
	        if(!gprs.isConnected() && !wifi.isConnected())
	        {
	            ab.setMessage("网络连接断开，请检查网络");
	        }
	        else{
	            ab.setMessage("网络连接成功");
//	            ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//	                @Override
//	                public void onClick(DialogInterface dialog, int which) {
//	                    dialog.dismiss();
//	                }
//	            });               
	        }
	        ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
	        ab.show();
	    }
	}

	private void initView() {
		btn_show_dialog = (Button) view.findViewById(R.id.btn_show_dialog);
		btn_show_dialog.setOnClickListener(this);
		btn_operate_db = (Button) view.findViewById(R.id.btn_operate_db);
		btn_operate_db.setOnClickListener(this);
		btn_slide_bar = (Button) view.findViewById(R.id.btn_slide_bar);
		btn_slide_bar.setOnClickListener(this);
		btn_location_city = (Button) view.findViewById(R.id.btn_location_city);
		btn_location_city.setOnClickListener(this);
		btn_play_video = (Button) view.findViewById(R.id.btn_play_video);
		btn_play_video.setOnClickListener(this);
		btn_slidebar_dialog = (Button)view.findViewById(R.id.btn_slidebar_dialog);
		btn_slidebar_dialog.setOnClickListener(this);
		btn_obtain_phone = (Button) view.findViewById(R.id.btn_obtain_phone);
		btn_obtain_phone.setOnClickListener(this);
		btn_connect_network = (Button) view.findViewById(R.id.btn_connect_network);
		btn_connect_network.setOnClickListener(this);
		btn_jpush_test = (Button) view.findViewById(R.id.btn_jpush_test);
		btn_jpush_test.setOnClickListener(this);
		et_test = (EditText) view.findViewById(R.id.et_test);
		et_test.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				 if (actionId == EditorInfo.IME_ACTION_DONE) {
		                Toast.makeText(getActivity(), "输入完成", Toast.LENGTH_LONG).show();
		                return true;
		            }
				return false;
			}
	    });
	}

	@Override
	public void onClick(View v) {
		if (v == btn_show_dialog) {
			showAddLinesAlertDialog();
		}else if(v == btn_operate_db){
			startActivity(new Intent(getActivity(),SqliteTestActivity.class));
		}else if(v == btn_slide_bar){
			startActivity(new Intent(getActivity(),SlideBarTestActivity.class));
		}else if(v == btn_location_city){
			startActivity(new Intent(getActivity(),LocationCityActivity.class));
		}else if(v == btn_play_video){
			startActivity(new Intent(getActivity(),PlayVideoActivity.class));
		}else if(v == btn_slidebar_dialog){
			showSlidebarDialog();
		}else if(v == btn_obtain_phone){
			startActivity(new Intent(getActivity(),ObtainUserPhoneActivity.class));
		}else if(v == btn_connect_network){
			checkNetworkState();
		}else if(v == btn_jpush_test){
			startActivity(new Intent(getActivity(),JpushMsgActivity.class));
		}
	}
	
	/** 
     * 检测网络是否连接 
     * @return 
     */  
    private boolean checkNetworkState() {  
        boolean flag = false;  
        //得到网络连接信息  
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);  
        //去进行判断网络是否连接  
        if (manager.getActiveNetworkInfo() != null) {  
            flag = manager.getActiveNetworkInfo().isAvailable();  
        }  
        if (!flag) {  
           Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_LONG).show();
        } else {  
           Toast.makeText(getActivity(), "当前网络已连接", Toast.LENGTH_LONG).show();
        }  
  
        return flag;  
    }  

	/**
	 * 弹出滑动条对话框
	 */
	private void showSlidebarDialog() {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.slidebar_dialog, null);
		tv_count = (TextView) v.findViewById(R.id.tv_count);
		mSeekbar = (SeekBar) v.findViewById(R.id.seekbar);
		slidebarCustomDialog = new CustomDialog(mContext);
		CustomDialog.Builder builer = new CustomDialog.Builder(mContext);
		builer.setTitle("移动位置");
		builer.setContentView(v);
		builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builer.setPositiveButton("确定", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		slidebarCustomDialog = builer.create();
		slidebarCustomDialog.show();
	}

	/****
	 * 显示提示增加线路
	 */
	private void showAddLinesAlertDialog() {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View layout = inflater.inflate(R.layout.edit_text_dialog, null);
		dialog = new AlertDialog.Builder(mContext)
		.setTitle("确认删除")
		.setView(layout)
		.setPositiveButton("是",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,int which) {

			}
		}).setNeutralButton("否", null).create();
		dialog.show();
		Window window = dialog.getWindow();
	    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	}
	
}
