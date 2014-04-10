package com.kingtong.jxc.common;

import java.util.ArrayList;
import java.util.List;

import com.kingtong.jxc.domain.DingDan;

public class OrderListController {
	private static OrderListController instance = null;
	
	/** 页面数据 */
	private List<DingDan> list = new ArrayList<DingDan>();
	
	private OrderListController() {}
	
	public static OrderListController getInstance() {
		if(null == instance) {
			instance = new OrderListController();
			instance.add(new DingDan());
		}
		return instance;
	}
	
	public void add(DingDan bean) {
		list.add(bean);
	}
	
	public void delete(int index) {
		list.remove(index);
	}
	
	public List<DingDan> getList() {
		return list;
	}

	public int getListSize() {
		return list.size();
	}
	
	public void reset()
	{
		if(instance != null)
		{
			list.removeAll(getList());
			instance = null;
		}
	}
}
