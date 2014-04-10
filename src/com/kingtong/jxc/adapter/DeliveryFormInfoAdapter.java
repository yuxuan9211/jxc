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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import com.kingtong.jxc.R;
import com.kingtong.jxc.common.DeviceUtil;
import com.kingtong.jxc.domain.SongHuoDetail;

public class DeliveryFormInfoAdapter  extends BaseAdapter  {
	
	private LayoutInflater inflater;       
	private List<Map<String, String>> list; 
	private SongHuoDetail sunhuoDetail;

	public DeliveryFormInfoAdapter(Context mcontext, List<Map<String, String>> sunHuoList, SongHuoDetail detail) {
		inflater = LayoutInflater.from(mcontext);
		this.list = sunHuoList;
		this.sunhuoDetail = detail;
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
		final int index = position;
		//如果已经加载过则使用缓存机制
		if(convertView!=null){
			view = convertView;
		}else{
			//没有加载过，解析条目布局文件
			view = inflater.inflate(R.layout.list_item_delivery_form, null);
		}
		TextView name = (TextView) view.findViewById(R.id.item_name_delivery_form_name);
		TextView price = (TextView) view.findViewById(R.id.item_name_delivery_form_price);
		TextView count = (TextView) view.findViewById(R.id.item_name_delivery_form_count);
		final EditText countConfirm = (EditText) view.findViewById(R.id.item_name_delivery_form_count_confirm);
		name.setText(list.get(position).get("name"));
		price.setText(list.get(position).get("price"));
		count.setText(list.get(position).get("count"));
		countConfirm.setText(list.get(position).get("count_confirm"));
		countConfirm.addTextChangedListener(new TextWatcher(){  
			  
	        public void afterTextChanged(Editable s) {   
	        	String  str = countConfirm.getText().toString();
	        	if(str!= null && str.length() > 0 && DeviceUtil.isNumeric(str))
	        	{
	        		int confirmCount = Integer.parseInt(str);
	        		sunhuoDetail.getOrderInfo().get(index).setConfirmCount(confirmCount);
	        	}
	        }  
	  
	        public void beforeTextChanged(CharSequence s, int start, int count,  
	                int after) {    
	              
	        }  
	  
	        public void onTextChanged(CharSequence s, int start, int before,  
	                int count) { 
	        	  
	        }  
	          
	    } );
		
		return view;
	}
}



