/****************************************************************************************
 * Copyright (c) 2010~2012 All Rights Reserved by
 *  G-Net Integrated Service Co.,  Ltd. 
 ****************************************************************************************/
/**
 * @file MerchantAdapter.java
 * @author wenhui.li
 * @date 2013-4-4 下午5:16:44 
 * Revision History 
 *     - 2013-4-4: change content for what reason
 ****************************************************************************************/

package com.kingtong.jxc.adapter;

import java.util.List;
import com.kingtong.jxc.R;
import com.kingtong.jxc.domain.Shanghu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MerchantAdapter extends BaseAdapter  {
	
	private LayoutInflater inflater;       
	private List<Shanghu> list;        

	public MerchantAdapter(Context mcontext, List<Shanghu> merchantList) {
		inflater = LayoutInflater.from(mcontext);
		this.list = merchantList;
	}

	public int getCount() {
		return list.size();
	}

	public Shanghu getItem(int position) {
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
			view = inflater.inflate(R.layout.list_item_two, null);
		}
		TextView name = (TextView) view.findViewById(R.id.item_name_for_two_column);
		name.setText(list.get(position).getTitle());
		
		return view;
	}
	
}


