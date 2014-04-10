/****************************************************************************************
 * Copyright (c) 2010~2012 All Rights Reserved by
 *  G-Net Integrated Service Co.,  Ltd. 
 ****************************************************************************************/
/**
 * @file ProductAdapter.java
 * @author wenhui.li
 * @date 2013-4-4 下午5:16:57 
 * Revision History 
 *     - 2013-4-4: change content for what reason
 ****************************************************************************************/

package com.kingtong.jxc.adapter;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kingtong.jxc.R;

public class OrderFormAdapter  extends BaseAdapter  {
	
	private LayoutInflater inflater;       
	private List<Map<String, String>> list;        

	public OrderFormAdapter(Context mcontext, List<Map<String, String>> subList) {
		inflater = LayoutInflater.from(mcontext);
		this.list = subList;
	}

	public int getCount() {
		return list.size();
	}

	public Map<String, String> getItem(int position) {
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
			view = inflater.inflate(R.layout.list_item_order_form, null);
		}
		TextView name = (TextView) view.findViewById(R.id.item_name_order_form_name);
		TextView price = (TextView) view.findViewById(R.id.item_name_order_form_price);
		TextView count = (TextView) view.findViewById(R.id.item_name_order_form_count);
		name.setText(list.get(position).get("name"));
		price.setText(list.get(position).get("price"));
		count.setText(list.get(position).get("count"));
		
		return view;
	}
}



