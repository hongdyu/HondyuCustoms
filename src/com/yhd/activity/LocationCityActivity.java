package com.yhd.activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.yhd.R;

import android.os.Bundle;
import android.widget.TextView;

public class LocationCityActivity extends BaseActivity{
	
	@SuppressWarnings("unused")
	private TextView tv_longtitude,tv_latitude,tv_city,tv_tip;
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_city);
		initView();
		initData();
	}

	private void initView() {
		tv_longtitude = (TextView) findViewById(R.id.tv_longtitude);
		tv_latitude = (TextView) findViewById(R.id.tv_latitude);
		tv_city = (TextView) findViewById(R.id.tv_city);
		tv_tip = (TextView) findViewById(R.id.tv_tip);
	}
	
	private void initData() {
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000); //发起请求定位的时间间隔
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		mLocClient.setLocOption(option);
		mLocClient.start();		
	}
	
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
        	//Receive Location
//          StringBuffer sb = new StringBuffer(256);
            tv_longtitude.setText(String.valueOf(location.getLongitude()));
            tv_latitude.setText(String.valueOf(location.getLatitude()));
            tv_city.setText(location.getCity());
            }
        }

		public void onReceivePoi(BDLocation poiLocation) {
		}
}
