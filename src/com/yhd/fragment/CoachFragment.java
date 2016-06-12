package com.yhd.fragment;

import java.util.ArrayList;

import com.yhd.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class CoachFragment extends Fragment implements OnClickListener{
	
	private View parentView;
	private Activity mContext;
	private HorizontalScrollView mHorizontalScrollView;
	private ViewPager pager;
	private LinearLayout mLinearLayout;
	private ImageView mImageView;
	private int mScreenWidth;
	private int item_width;

	private int endPosition;
	private int beginPosition;
	private int currentFragmentIndex;
	private boolean isEnd;
	private String[] title = new String[] { "报名", "科一", "科二", "科三", "科四" };
	private ArrayList<Fragment> fragments;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		parentView = inflater.inflate(R.layout.fragment_coach, container,	false);
		DisplayMetrics dm = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;
		initView(parentView);
		return parentView;
	}
	private void initView(View view) {

		mHorizontalScrollView = (HorizontalScrollView) view
				.findViewById(R.id.hsv_view);
		mLinearLayout = (LinearLayout) view.findViewById(R.id.hsv_content);
		mImageView = (ImageView) view.findViewById(R.id.img1);
		item_width = (int) ((mScreenWidth / 4.0 + 0.5f));
		mImageView.getLayoutParams().width = item_width;
		pager = (ViewPager) view.findViewById(R.id.pager);
		// 初始化导航
		addTab();
		// 初始化viewPager
		initViewPager();
	}
	
	/**
	 * 添加选项卡
	 */
	private void addTab() {
		for (int i = 0; i < title.length; i++) {
			RelativeLayout layout = new RelativeLayout(mContext);
			TextView view = new TextView(mContext);
			view.setText(title[i]);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_IN_PARENT);
			layout.addView(view, params);
			mLinearLayout.addView(layout, (int) (mScreenWidth / 4 + 0.5f),LayoutParams.WRAP_CONTENT);
			layout.setOnClickListener(this);
			layout.setTag(i);
		}
	}
	
	private void initViewPager() {
		fragments = new ArrayList<Fragment>();
		for (int i = 0; i < title.length; i++) {//初始化frgment模拟数据
				if(i==0){
					Bundle data1 = new Bundle();
					data1.putString("text", title[i]);
					SignUpFragment fragment = new SignUpFragment();
					fragment.setArguments(data1);
					fragments.add(fragment);
				}else if(i==1){
					Bundle data2 = new Bundle();
					data2.putString("text", title[i]);
					DrivingSubjecOne fragment = new DrivingSubjecOne();
					fragment.setArguments(data2);
					fragments.add(fragment);
				}else if(i==2){
					Bundle data3 = new Bundle();
					data3.putString("text", title[i]);
					DrivingSubjectTwo fragment = new DrivingSubjectTwo();
					fragment.setArguments(data3);
					fragments.add(fragment);
				}else if(i==3){
					Bundle data4 = new Bundle();
					data4.putString("text", title[i]);
					DrivingSubjectThree fragment = new DrivingSubjectThree();
					fragment.setArguments(data4);
					fragments.add(fragment);
				}else if(i==4){
					Bundle data5 = new Bundle();
					data5.putString("text", title[i]);
					DrivingSubjectFour fragment = new DrivingSubjectFour();
					fragment.setArguments(data5);
					fragments.add(fragment);
				}
		}
		MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(
				getChildFragmentManager(), fragments);
		pager.setAdapter(fragmentPagerAdapter);
		fragmentPagerAdapter.setFragments(fragments);
		pager.setOnPageChangeListener(new MyOnPageChangeListener());
		pager.setCurrentItem(0);
	}
	
	private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		private ArrayList<Fragment> fragments;
		private FragmentManager fm;

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
			this.fm = fm;
		}

		public MyFragmentPagerAdapter(FragmentManager fm,
				ArrayList<Fragment> fragments) {
			super(fm);
			this.fm = fm;
			this.fragments = fragments;
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public void setFragments(ArrayList<Fragment> fragments) {
			if (this.fragments != null) {
				FragmentTransaction ft = fm.beginTransaction();
				for (Fragment f : this.fragments) {
					ft.remove(f);
				}
				ft.commit();
				ft = null;
				fm.executePendingTransactions();
			}
			this.fragments = fragments;
			notifyDataSetChanged();
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			Object obj = super.instantiateItem(container, position);
			return obj;
		}

	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(final int position) {
			Animation animation = new TranslateAnimation(endPosition, position
					* item_width, 0, 0);
			beginPosition = position * item_width;
			currentFragmentIndex = position;
			if (animation != null) {
				animation.setFillAfter(true);
				animation.setDuration(0);
				mImageView.startAnimation(animation);
				mHorizontalScrollView.smoothScrollTo((currentFragmentIndex - 1)
						* item_width, 0);
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			if (!isEnd) {
				if (currentFragmentIndex == position) {
					endPosition = item_width * currentFragmentIndex
							+ (int) (item_width * positionOffset);
				}
				if (currentFragmentIndex == position + 1) {
					endPosition = item_width * currentFragmentIndex
							- (int) (item_width * (1 - positionOffset));
				}
				Animation mAnimation = new TranslateAnimation(beginPosition,
						endPosition, 0, 0);
				mAnimation.setFillAfter(true);
				mAnimation.setDuration(0);
				mImageView.startAnimation(mAnimation);
				mHorizontalScrollView.invalidate();
				beginPosition = endPosition;
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_DRAGGING) {
				isEnd = false;
			} else if (state == ViewPager.SCROLL_STATE_SETTLING) {
				isEnd = true;
				beginPosition = currentFragmentIndex * item_width;
				if (pager.getCurrentItem() == currentFragmentIndex) {
					// 未跳入下一个页面
					mImageView.clearAnimation();
					Animation animation = null;
					// 恢复位置
					animation = new TranslateAnimation(endPosition,
							currentFragmentIndex * item_width, 0, 0);
					animation.setFillAfter(true);
					animation.setDuration(1);
					mImageView.startAnimation(animation);
					mHorizontalScrollView.invalidate();
					endPosition = currentFragmentIndex * item_width;
				}
			}
		}

	}
	
	@Override
	public void onClick(View v) {
		pager.setCurrentItem((Integer) v.getTag());
	}

}
