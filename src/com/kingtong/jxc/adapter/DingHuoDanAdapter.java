/****************************************************************************************
 * Copyright (c) 2010~2012 All Rights Reserved by
 *  G-Net Integrated Service Co.,  Ltd. 
 ****************************************************************************************/
/**
 * @file OrderListAdapter.java
 * @author wenhui.li
 * @date 2013-4-5 下午3:09:02 
 * Revision History 
 *     - 2013-4-5: change content for what reason
 ****************************************************************************************/

package com.kingtong.jxc.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kingtong.jxc.R;
import com.kingtong.jxc.domain.DingHuoDan;

public class DingHuoDanAdapter extends BaseAdapter  {
	
	private LayoutInflater inflater;       
	private List<DingHuoDan> list;        

	public DingHuoDanAdapter(Context mcontext, List<DingHuoDan> dingDanList) {
		inflater = LayoutInflater.from(mcontext);
		this.list = dingDanList;
	}

	public int getCount() {
		return list.size();
	}

	public DingHuoDan getItem(int position) {
		return list.get(position);
	}


	public long getItemId(int position) {
		return position;
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		//声明View对象
		View view;
		//如果已经加载过则使用缓存机制
		if(convertView!=null){
			view = convertView;
		}else{
			//没有加载过，解析条目布局文件
			view = inflater.inflate(R.layout.list_item_for_order, null);
		}
		TextView name = (TextView) view.findViewById(R.id.item_name_for_order_form);
		TextView count = (TextView) view.findViewById(R.id.item_count_for_order_form);
		name.setText(list.get(position).getShanghuName());
		count.setText(""+ list.get(position).getNum());
		
		return view;
	}
}


