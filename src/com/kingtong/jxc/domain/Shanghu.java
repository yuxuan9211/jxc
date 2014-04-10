package com.kingtong.jxc.domain;

/**
 * {
 *       "title": "商户名称1",
 *       "add": "商户地址",
 *       "tel": "商户电话",
 *       "fax": "商户传真",
 *       "lxr": "联系人",
 *       "mp": "联系手机"
 *   }
 * @author Liu Leilei
 *
 */
public class Shanghu {
	private long id;
	private String title;
	private String add;
	private String tel;
	private String fax;
	private String lxr;
	private String mp;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getLxr() {
		return lxr;
	}
	public void setLxr(String lxr) {
		this.lxr = lxr;
	}
	public String getMp() {
		return mp;
	}
	public void setMp(String mp) {
		this.mp = mp;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
