package com.yhd.helper;

import android.content.Context;

public class SizeHelper {
	public static float designedDensity = 1.5f;
	public static int designedScreenWidth = 540;
	private static Context context;
	protected static SizeHelper helper;
	
	private SizeHelper(){
	}

	public static void prepare(Context c)
	{
		if(context == null || context !=c.getApplicationContext())
		{
			context = c;
		}
	}
	
	/**
	 * px 转 dp
	 * @param context
	 * @param px
	 * @return
	 */
	public static float pxToDp(Context context , float px)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (px / scale);
	}
	
	/**
	 * dp 转 px
	 * @param context
	 * @param dp
	 * @return
	 */
	public static float dpToPx(Context context , float dp)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (dp / scale);
	}
}
