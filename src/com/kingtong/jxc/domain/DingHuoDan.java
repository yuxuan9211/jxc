/****************************************************************************************
 * Copyright (c) 2010~2012 All Rights Reserved by
 *  G-Net Integrated Service Co.,  Ltd. 
 ****************************************************************************************/
/**
 * @file DingHuoDan.java
 * @author hp
 * @date 2013-6-30 下午4:46:49 
 * Revision History 
 *     - 2013-6-30: change content for what reason
 ****************************************************************************************/

package com.kingtong.jxc.domain;

public class DingHuoDan {

	private String shanghuName; //商户名称
	private int oid; //订单id
	private int  num; //订单数量
	public String getShanghuName() {
		return shanghuName;
	}
	public void setShanghuName(String shanghuName) {
		this.shanghuName = shanghuName;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

}

