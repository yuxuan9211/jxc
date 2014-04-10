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
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.kingtong.jxc.R;
import com.kingtong.jxc.common.OrderListController;
import com.kingtong.jxc.domain.DingDan;

public class NewOrderAdapter  extends BaseAdapter  {
	private LayoutInflater inflater;       
	private List<DingDan> list;        

	public NewOrderAdapter(Context mcontext, List<DingDan> merchantList) {
		inflater = LayoutInflater.from(mcontext);
		this.list = merchantList;
	}

	public int getCount() {
		return list.size();
	}

	public DingDan getItem(int position) {
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
			view = inflater.inflate(R.layout.list_item_add_order_list, null);
		}
		
		TextView name  = (TextView) view.findViewById(R.id.item_name_order_form_name);
		TextView price = (TextView) view.findViewById(R.id.item_name_order_form_price);
		TextView num   = (TextView) view.findViewById(R.id.item_name_order_form_count);
		TextView delete = (TextView) view.findViewById(R.id.item_name_order_form_delete);
		
		if(position == 0) {
			name.setText("商品名称");
			price.setText("价格");
			num.setText("数量");
			delete.setText("删除");
			delete.setTextColor(Color.BLACK);
		} else {
			name.setText(list.get(position).getName());
			price.setText(String.valueOf(list.get(position).getPrice()));
			num.setText(list.get(position).getCount()+"");
			delete.setText(" X ");
			delete.setTextColor(Color.RED);
			final int use = position;
			delete.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					log("delete item:" + use);
					OrderListController.getInstance().delete(use);
					notifyDataSetChanged();
				}

			});
		}
		return view;
	}
	
	private void log(String string) {
		Log.d(NewOrderAdapter.class.getSimpleName(), string);
	}
	
}



