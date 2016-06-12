package com.yhd.fragment;

import com.yhd.R;
import com.yhd.activity.ProfileActivity;
import com.yhd.common.ApplicationCache;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/***
 * 科目三
 */
@SuppressLint("JavascriptInterface")
public class DrivingSubjectThree extends Fragment {
	private Activity mContext;
	private WebView mWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mContext = getActivity();
		View view = inflater.inflate(R.layout.driving_sub_three_fragment,
				container, false);
		initView(view);
		return view;
	}
	@SuppressLint("SetJavaScriptEnabled")
	@JavascriptInterface
	private void initView(View view) {
		DisplayMetrics dm = new DisplayMetrics();
		mWebView = (WebView) view.findViewById(R.id.webView);
		// 获取屏幕
		mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(ApplicationCache.D3_S3);
		mWebView.addJavascriptInterface(this, "DrivingSubjectThree");
		mWebView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
					mWebView.goBack();
					return true;
				}
				return false;
			}
		});
	}
	
	/***
	 * JS调用跳转
	 */
	@JavascriptInterface
	public void jumpActivity(String jumpTag) {
		 //Toast.makeText(getActivity(), jumpTag, 0).show();
		String Url = null;
		String Tag = null;
		String Title = null;
		if (jumpTag != null) {
			String temp[] = jumpTag.split("[?]");
			if (temp.length == 2) {
				Url = temp[0];
				Tag = temp[1];
			} else {
				Url = temp[0];
				Tag = temp[1];
				Title = temp[2];
			}
		}
		if ("ExaminationActivity".equals(Tag)) {
			Intent intent = new Intent(getActivity(), ProfileActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Tag", Tag);
			bundle.putString("Url", Url);
			intent.putExtras(bundle);
			startActivity(intent);
		} else if ("TeachingActivity".equals(Tag)) {
			Intent intent = new Intent(getActivity(), ProfileActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Tag", Tag);
			bundle.putString("Url", Url);
			intent.putExtras(bundle);
			startActivity(intent);
		} else if ("ExamCheatsActivity".equals(Tag)) {
			Intent intent = new Intent(getActivity(), ProfileActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Tag", Tag);
			bundle.putString("Url", Url);
			intent.putExtras(bundle);
			startActivity(intent);
		} else if ("ExamSiteActivity".equals(Tag)) {
			Intent intent = new Intent(getActivity(), ProfileActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Tag", Tag);
			bundle.putString("Url", Url);
			intent.putExtras(bundle);
			startActivity(intent);
		} else if ("MakeWishActivity".equals(Tag)) {
			Intent intent = new Intent(getActivity(), ProfileActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Tag", Tag);
			bundle.putString("Url", Url);
			intent.putExtras(bundle);
			startActivity(intent);
		} else if ("DrivingCircleActivity".equals(Tag)) {
			Intent intent = new Intent(getActivity(),
					ProfileActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Tag", Tag);
			bundle.putString("Url", Url);
			intent.putExtras(bundle);
			startActivity(intent);
		} else if ("VideoActivity".equals(Tag)) {
			Intent intent = new Intent(getActivity(), ProfileActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Tag", Tag);
			bundle.putString("Url", Url);
			intent.putExtras(bundle);
			startActivity(intent);
		} else if ("ExamLocationActivity".equals(Tag)) {
			Intent intent = new Intent(getActivity(),
					ProfileActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Tag", Tag);
			bundle.putString("Url", Url);
			intent.putExtras(bundle);
			startActivity(intent);
		} else if ("DrivingSubjectThreeActivity".equals(Tag)) {
			Intent intent = new Intent(getActivity(),
					ProfileActivity.class);
			intent.putExtra("AllTag", jumpTag);
			intent.putExtra("Title", Title);
			startActivity(intent);
		}else if("SelectDrivingSchoolActivity".equals(Tag)){
			Intent intent = new Intent(getActivity(),
					ProfileActivity.class);
//			intent.putExtra("AllTag", jumpTag);
//			intent.putExtra("Title", Title);
			startActivity(intent);
		}
	}
	
}
