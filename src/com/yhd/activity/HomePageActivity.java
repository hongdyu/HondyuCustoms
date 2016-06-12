package com.yhd.activity;

import java.util.ArrayList;
import java.util.List;

import com.yhd.R;
import com.yhd.helper.SizeHelper;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class HomePageActivity extends Activity {

	private TextView tv_tabname;
	private HorizontalScrollView hs_tabbar;
	private LinearLayout ll_tabbar_content;
	private RadioGroup myRadioGroup;

	private List<String> titleList;
	private String channel;
	//当前被选中的RadioButton距离左侧的距离
	private float mCurrentCheckedRadioLeft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page);
		initView();
		initData();
	}

	private void initView() {
		tv_tabname = (TextView) findViewById(R.id.tv_tabname);
		hs_tabbar = (HorizontalScrollView) findViewById(R.id.hs_tabbar);
		ll_tabbar_content = (LinearLayout) findViewById(R.id.ll_tabbar_content);
	}

	private void initData() {
		titleList = new ArrayList<String>();
		titleList.add("推荐");
		titleList.add("热点");
		titleList.add("北京");
		titleList.add("体育");
		titleList.add("娱乐");
		titleList.add("足球");
		titleList.add("巴萨");
		titleList.add("汽车");

		// 选项卡布局
		myRadioGroup = new RadioGroup(this);
		myRadioGroup.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
		ll_tabbar_content.addView(myRadioGroup);
		for (int i = 0; i < titleList.size(); i++) {
			String channel = titleList.get(i);
			RadioButton radio = new RadioButton(this);
			radio.setButtonDrawable(android.R.color.transparent);
			radio.setBackgroundResource(R.drawable.radiobtn_selector);
			ColorStateList csl = getResources().getColorStateList(
					R.color.radiobtn_color_selector);
			radio.setTextColor(csl);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					(int) SizeHelper.dpToPx(this, 80),
					ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
			radio.setLayoutParams(layoutParams);
			radio.setTextSize(15);
			radio.setGravity(Gravity.CENTER);
			radio.setText(channel);
			radio.setTag(channel);
			myRadioGroup.addView(radio);
		}
		
		myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int radioButtonId = group.getCheckedRadioButtonId();
				//根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton) findViewById(radioButtonId);
				channel = (String) rb.getTag();
				//更改当前按钮距离左边的距离
				mCurrentCheckedRadioLeft = rb.getLeft();
				int width = (int) SizeHelper.dpToPx(HomePageActivity.this, 140);
				hs_tabbar.smoothScrollTo((int) mCurrentCheckedRadioLeft - width, 0);
				tv_tabname.setText(channel);
			}
		});

		// 设定默认被选中的选项卡为第一项
		if (!titleList.isEmpty()) {
			myRadioGroup.check(myRadioGroup.getChildAt(0).getId());
		}
	}
}
