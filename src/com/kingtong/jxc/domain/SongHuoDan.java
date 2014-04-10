/****************************************************************************************
 * Copyright (c) 2010~2012 All Rights Reserved by
 *  G-Net Integrated Service Co.,  Ltd. 
 ****************************************************************************************/
/**
 * @file SongHuoDan.java
 * @author hp
 * @date 2013-7-2 下午2:40:43 
 * Revision History 
 *     - 2013-7-2: change content for what reason
 ****************************************************************************************/

package com.kingtong.jxc.domain;

public class SongHuoDan {

	private int id;//Id
	private int sid;//送货详情id
	private String name;//商户名称
	private String addTime;//送货时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
		
}

