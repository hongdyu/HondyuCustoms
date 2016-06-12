package com.yhd.fragment;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.yhd.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class DiscoveryFragment extends Fragment {

	View view;
	@SuppressWarnings("unused")
	private RelativeLayout rl_delete_item, rl_replace_item, rl_move_position,
			rl_update_number;
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListener myListener = new MyLocationListener();
	@SuppressWarnings("unused")
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;

	@SuppressWarnings("unused")
	private Marker mMarker;
	TextureMapView mMapView;
	BaiduMap mBaiduMap;
	boolean isFirstLoc = true;// 是否首次定位
	boolean isFirstReplaceIcon = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SDKInitializer.initialize(getActivity().getApplicationContext());
		view = inflater.inflate(R.layout.fragment_discovery, container, false);
		initView();
		return view;
	}

	private void initView() {
		mCurrentMode = LocationMode.NORMAL;
		// 地图初始化
		mMapView = (TextureMapView) view.findViewById(R.id.mapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(getActivity());
		mLocClient.registerLocationListener(myListener);
		// // 修改为自定义marker
		// mCurrentMarker =
		// BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
		// mBaiduMap.setMyLocationConfigeration(new
		// MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开 GPS
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true); // 返回的定位结果包含手机机头的方向
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// MapView 销毁后不再处理新接收的位置
			if (null == location || null == mMapView) {
				return;
			}

			// 设置开发者获取到的方向信息，顺时针 0~360
			// MyLocationData locData = new MyLocationData.Builder()
			// .accuracy(location.getRadius()).direction(0)
			// .latitude(location.getLatitude())
			// .longitude(location.getLongitude()).build();

			/*
			 * MyLocationData locData = new MyLocationData.Builder()
			 * .accuracy(0).latitude(location.getLatitude())
			 * .longitude(location.getLongitude()).build();
			 * mBaiduMap.setMyLocationData(locData);
			 */

			// 定义marker坐标点
			LatLng point = new LatLng(location.getLatitude(),
					location.getLongitude());

			// 构建markerOption，用于在地图上添加marker
			OverlayOptions options = new MarkerOptions()//
					.position(point)
					// 设置marker的位置
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.icon_marka))// 设置marker的图标
					.zIndex(16)// 設置marker的所在層級
					.draggable(true);// 设置手势拖拽
			// 在地图上添加marker，并显示
			mMarker = (Marker) mBaiduMap.addOverlay(options);

			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory
						.newLatLngZoom(ll, 16); // 设置地图中心点
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	public void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		mBaiduMap.setMyLocationEnabled(false);
		super.onDestroy();
		mMapView.onDestroy();
		mMapView = null;
	}

}
