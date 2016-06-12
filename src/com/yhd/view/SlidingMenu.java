package com.yhd.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.yhd.R;
import com.nineoldandroids.view.ViewHelper;

/****
 *侧滑菜单容器 
 */
/**
 * 侧滑菜单
 * 
 * @author hondyu
 *
 */
public class SlidingMenu extends HorizontalScrollView {
	/** 屏幕宽度 **/
	private int mScreenWidth;
	/** dp菜单左边padding **/
	private int mMenuRightPadding;
	/** 菜单的宽度 **/
	private int mMenuWidth;
	private int mHalfMenuWidth;
	private boolean isOpen;
	private boolean once;
	private Handler handler;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	
	@SuppressWarnings("unused")
	private boolean isSlide = false;
	
	/** 触摸时按下的点 **/
	PointF downP = new PointF();
	/** 触摸时移动的点 **/
	PointF moveP = new PointF();
	/** 触摸时当前的点 **/
	PointF curP = new PointF();
	/** 触摸时结束的的点 **/
	PointF mlastP = new PointF();
	/** 屏幕宽度 **/

	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlidingMenu(Context context) {
		this(context, null, 0);
	}

	public void initHandler(Handler mhandler) {
		this.handler = mhandler;
	}
	
	private int position;

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setScreenWidth(int mScreenWidth) {
		this.mScreenWidth = mScreenWidth;
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// 得到屏幕宽度
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth = outMetrics.widthPixels;

		TypedArray mTypedArray = context.getTheme().obtainStyledAttributes(
				attrs, R.styleable.SlidingMenu, defStyle, 0);
		int n = mTypedArray.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = mTypedArray.getIndex(i);
			switch (attr) {
			case R.styleable.SlidingMenu_rightPadding:
				// 默认50
				mMenuRightPadding = mTypedArray.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50f,
								getResources().getDisplayMetrics()));// 默认�?0DP
				break;
			}
		}
		mTypedArray.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/** 显示的设置一个宽度 **/
		if (!once) {
			LinearLayout wrapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) wrapper.getChildAt(0);
			mContent = (ViewGroup) wrapper.getChildAt(1);

			mMenuWidth = mScreenWidth - mMenuRightPadding;
			mHalfMenuWidth = mMenuWidth / 2;
			mMenu.getLayoutParams().width = mMenuWidth;
			mContent.getLayoutParams().width = mScreenWidth;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			// 将菜单隐藏
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		/*switch(ev.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				downP.x = ev.getX();
				downP.y = ev.getY();
				// 此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
				getParent().requestDisallowInterceptTouchEvent(true);
				break;
			case MotionEvent.ACTION_MOVE:
				double deltax = ev.getX() - downP.x;
				double deltay = ev.getY() - downP.y;
				if (deltax > 0 && getPosition() == 0) {// 滑动方向向右时，执行slideMenu滑动事件
					isSlide = true;
					return true;
				}else if (downP.x > mScreenWidth - 60 && downP.x <= mScreenWidth) {// 从屏幕右侧向左滑动
					if(isSlide){
						return true;
					}else{
						return false;
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				break;
		}*/
		
		return super.onInterceptTouchEvent(ev);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			if (scrollX > mHalfMenuWidth) {
				this.smoothScrollTo(mMenuWidth, 0);
				handler.sendEmptyMessage(1);
				isOpen = false;
			} else {
				this.smoothScrollTo(0, 0);
				handler.sendEmptyMessage(0);
				isOpen = true;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 打开菜单
	 */
	public void openMenu() {
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		handler.sendEmptyMessage(0);
		isOpen = true;
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu() {
		if (isOpen) {
			this.smoothScrollTo(mMenuWidth, 0);
			handler.sendEmptyMessage(1);
			isOpen = false;
		}
	}

	/**
	 * 切换菜单状态
	 */
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mMenuWidth;
		float leftScale = 1 - 0.3f * scale;
		float rightScale = 0.8f + scale * 0.2f;

		// 第三方开源jar包里面的
		ViewHelper.setScaleX(mMenu, leftScale);
		ViewHelper.setScaleY(mMenu, leftScale);
		ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
		ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.7f);

		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
		ViewHelper.setScaleX(mContent, rightScale);
		ViewHelper.setScaleY(mContent, rightScale);
	}
}
