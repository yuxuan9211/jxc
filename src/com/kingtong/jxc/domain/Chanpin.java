package com.kingtong.jxc.domain;

/**
 *  {
 *       "title": "产品名称",
 *       "pic": "/d/file/can/2013-04-29/2475f730f02844b7cee548520b8208fc.jpg",
 *       "rl": "100ml",
 *       "jg": "100.00"
 *  }
 * @author Liu Leilei
 *
 */
public class Chanpin {
	private long id;
	private String title;
	private String pic;
	private String rl;
	private String jg;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getRl() {
		return rl;
	}
	public void setRl(String rl) {
		this.rl = rl;
	}
	public String getJg() {
		return jg;
	}
	public void setJg(String jg) {
		this.jg = jg;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
