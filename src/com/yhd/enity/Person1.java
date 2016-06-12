package com.yhd.enity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * 创建JavaBean
 * 
 * @author Administrator
 *
 */
public class Person1 extends BmobObject {

	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private BmobGeoPoint gpsAdd;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BmobGeoPoint getGpsAdd() {
		return gpsAdd;
	}

	public void setGeoPoint(BmobGeoPoint gpsAdd) {
		this.gpsAdd = gpsAdd;
	}

}
