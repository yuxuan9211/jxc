package com.kingtong.jxc.common;

import java.util.ArrayList;
import java.util.List;
import com.kingtong.jxc.domain.JingPinHuoDong;

public class DoingsListController {
	private static DoingsListController instance = null;
	
	/** 页面数据 */
	private List<JingPinHuoDong> list = new ArrayList<JingPinHuoDong>();
	
	private DoingsListController() {}
	
	public static DoingsListController getInstance() {
		if(null == instance) {
			instance = new DoingsListController();
			instance.add(new JingPinHuoDong());
		}
		return instance;
	}
	
	public void add(JingPinHuoDong bean) {
		list.add(bean);
	}
	
	public void delete(int index) {
		list.remove(index);
	}
	
	public List<JingPinHuoDong> getList() {
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
