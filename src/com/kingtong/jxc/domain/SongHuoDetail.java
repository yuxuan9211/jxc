/****************************************************************************************
 * Copyright (c) 2010~2012 All Rights Reserved by
 *  G-Net Integrated Service Co.,  Ltd. 
 ****************************************************************************************/
/**
 * @file SongHuoDan.java
 * @author hp
 * @date 2013-7-2 下午12:33:54 
 * Revision History 
 *     - 2013-7-2: change content for what reason
 ****************************************************************************************/

package com.kingtong.jxc.domain;

import java.io.Serializable;
import java.util.List;

public class SongHuoDetail  implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;//订单id
	private String title;//商户名称
	private String address;//商户地址
	private String phone;//商户电话
	private String mobile;//商户手机号
	private String contactor;//商户联系人
	private String addTime;//添加时间
	private List<SongHuoConfirm> orderInfo = null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContactor() {
		return contactor;
	}
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public List<SongHuoConfirm> getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(List<SongHuoConfirm> orderInfo) {
		this.orderInfo = orderInfo;
	}
}

