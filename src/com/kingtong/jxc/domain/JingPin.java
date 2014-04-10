package com.kingtong.jxc.domain;

/**
 * {
 *       "title": "竞品1",
 *       "pic": "/d/file/jingcp/2013-04-29/8b57409ba15a2ff44b35594463ad6d01.jpg",
 *       "rl": "100",
 *       "jg": "105"
 *   }
 * @author Liu Leilei
 *
 */
public class JingPin {
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
