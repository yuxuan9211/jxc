package com.kingtong.jxc.domain;

public class SongHuoConfirm {
	
	private int id;//订单商品索引号
	private int oid;//订单id
	private int uid;//用户id
	private int pid;//订单商品id
	private String name;//订单商品名称
	private double price;//单价
	private int count;//订货数量
	private int confirmCount;//实际送货数量即确认数量
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getConfirmCount() {
		return confirmCount;
	}
	public void setConfirmCount(int confirmCount) {
		this.confirmCount = confirmCount;
	}
}
