/****************************************************************************************
 * Copyright (c) 2010~2012 All Rights Reserved by
 *  G-Net Integrated Service Co.,  Ltd. 
 ****************************************************************************************/
/**
 * @file DingDanDetail.java
 * @author hp
 * @date 2013-6-30 下午7:05:17 
 * Revision History 
 *     - 2013-6-30: change content for what reason
 ****************************************************************************************/

package com.kingtong.jxc.domain;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DingDanDetail implements Serializable{

	private String name; //商户名称
	private String address;//送货地址
	private String phone;//电话
	private String fax;//传真
	private String contactor;//联系人
	private String mobile;//手机号
	private List<DingDan> dingDanList;//产品列表
	private String sendTime;//送货时间
	
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getContactor() {
		return contactor;
	}
	public void setContactor(String contactor) {
		this.contactor = contactor;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public List<DingDan> getDingDanList() {
		return dingDanList;
	}
	public void setDingDanList(List<DingDan> dingDanList) {
		this.dingDanList = dingDanList;
	}
}

