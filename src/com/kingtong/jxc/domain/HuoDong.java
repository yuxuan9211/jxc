package com.kingtong.jxc.domain;

/**
 * {
 *       "title": "竞品一",
 *       "jxs": "经销商a",
 *       "stime": "2013-5-1",
 *       "etime": "2013-5-1",
 *       "info": "降价促销"
 *   }
 * @author Liu Leilei
 *
 */
public class HuoDong {
	private long id;
	private String title;
	private String jxs;
	private String sTime;
	private String eTime;
	private String info;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJxs() {
		return jxs;
	}
	public void setJxs(String jxs) {
		this.jxs = jxs;
	}
	public String getsTime() {
		return sTime;
	}
	public void setsTime(String sTime) {
		this.sTime = sTime;
	}
	public String geteTime() {
		return eTime;
	}
	public void seteTime(String eTime) {
		this.eTime = eTime;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
