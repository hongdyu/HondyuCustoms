package com.yhd.fragment;

import com.yhd.R;
import com.yhd.common.ApplicationCache;

import android.annotation.SuppressLint;
import android.app.Activity;
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
 *科目四
 */
public class DrivingSubjectFour extends Fragment {

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
		mContext= getActivity();
		View view = inflater.inflate(R.layout.driving_sub_four_fragment, container, false);
		initView(view);
		return view;
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	@JavascriptInterface 
	private void initView(View view){
		DisplayMetrics dm = new DisplayMetrics();
		mWebView = (WebView) view.findViewById(R.id.webView);
		//获取屏幕
		mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
		mWebView.getSettings().setJavaScriptEnabled(true);  
		mWebView.loadUrl(ApplicationCache.D3_S4);
		mWebView.addJavascriptInterface(this, "DrivingSubjectThree"); 
		mWebView.setOnKeyListener(new View.OnKeyListener() {         
            @Override  
            public boolean onKey(View v, int keyCode, KeyEvent event) {  
                if(keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  
                    mWebView.goBack();  
                    return true;  
                }  
                return false;  
            }
        });  
	}
}
