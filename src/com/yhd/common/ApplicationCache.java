package com.yhd.common;

import android.app.Activity;

/**
 * 全局缓存，全局变量
 * @author XLiangLee
 */
public class ApplicationCache {
	/**
	 * 全局Context
	 */
	public static Activity Context;
	/***
	 * 靠边停车是否已启动
	 */
	public static boolean IsRunPullOver = false;
	/**
	 * 是否考试模式
	 */
	public static boolean IsExam = false;
	/**
	 * 报名主页
	 */
	public static String D3_SIGNUP = "file:///android_asset/H5/SignUp/index.html";
	
	/**
	 * 科目一主页
	 */
	public static String D3_S1 = "file:///android_asset/H5/S1/index.html";
	
	/**
	 * 科目二主页
	 */
	public static String D3_S2 = "file:///android_asset/H5/S2/index.html";
	
	/**
	 * 科目三主页
	 */
	public static String D3_S3 = "file:///android_asset/H5/S3/index.html";
	
	/**
	 * 科目四主页
	 */
	public static String D3_S4 = "file:///android_asset/H5/S4/index.html";
	
	/**
	 * 拿证主页
	 */
	public static String D3_TAKECARD = "file:///android_asset/H5/TakeCard/index.html";
	
	/**
	 * 我的驾校主页
	 */
	public static String My_SCHOOL = "file:///android_asset/H5/S3/examinationposition.html";
	
	/**
	 * 教练主页
	 */
	public static String COACH = "file:///android_asset/H5/Coach/index.html";
	
	/**
	 * 驾考圈主页
	 */
	public static String DRIVING_CIRCLE = "file:///android_asset/H5/S3/drivingring.html";
	
	/**
	 * 买车主页
	 */
	public static String BUY_CAR = "file:///android_asset/H5/BuyCar/index.html";
	
	/**
	 * 科目三模拟考试考试主页
	 */
	public static String D3_SIMULATION_TEST = "file:///android_asset/H5/S3/SimulationTest/index.html";
	
	/**
	 * 架考车型
	 */
	public static String VEHICLEDRIVING_TEXT = "file:///android_asset/H5/S3/vehicledrivingtest.html";
	
	/**
	 * 关于我们
	 */
	public static String ABOUT_WE = "file:///android_asset/H5/S3/aboutus.html";
	
	/***
	 *服务协议 
	 */
	public static String SERVICE_AGREEMENT= "file:///android_asset/H5/S3/agreement.html";
	
}
