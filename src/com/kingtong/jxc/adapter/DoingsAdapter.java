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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kingtong.jxc.R;
import com.kingtong.jxc.domain.HuoDong;

public class DoingsAdapter  extends BaseAdapter  {
	
	private LayoutInflater inflater;       
	private List<HuoDong> list;        

	public DoingsAdapter(Context mcontext, List<HuoDong> merchantList) {
		inflater = LayoutInflater.from(mcontext);
		this.list = merchantList;
	}

	public int getCount() {
		return list.size();
	}

	public HuoDong getItem(int position) {
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
			view = inflater.inflate(R.layout.list_item_doings, null);
		}
		TextView dealer = (TextView) view.findViewById(R.id.item_name_doings_dealer);
		TextView start = (TextView) view.findViewById(R.id.item_name_doings_start_time);
		TextView end = (TextView) view.findViewById(R.id.item_name_doings_end_time);
		TextView content = (TextView) view.findViewById(R.id.item_name_doings_content);
		dealer.setText(list.get(position).getJxs());
		
		long starttime = Long.parseLong(list.get(position).getsTime());
		long endtime = Long.parseLong(list.get(position).geteTime());
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String startString = format.format(new Date(starttime));
		String endString = format.format(new Date(endtime));
		
		start.setText(startString);
		end.setText(endString);
		content.setText(list.get(position).getInfo());
		
		return view;
	}
}



