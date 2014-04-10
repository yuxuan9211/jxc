package com.kingtong.jxc.domain;

public class JingPinHuoDong {
	private int id;
	private String name;
	private double price;
	private long startTiem;
	private long endTiem;
	private String info;
	
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
	public long getStartTiem() {
		return startTiem;
	}
	public void setStartTiem(long startTiem) {
		this.startTiem = startTiem;
	}
	public long getEndTiem() {
		return endTiem;
	}
	public void setEndTiem(long endTiem) {
		this.endTiem = endTiem;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
