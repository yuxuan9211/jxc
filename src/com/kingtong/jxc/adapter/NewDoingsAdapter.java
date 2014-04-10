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
import com.kingtong.jxc.common.DeviceUtil;
import com.kingtong.jxc.common.DoingsListController;
import com.kingtong.jxc.common.TimeUtil;
import com.kingtong.jxc.conn.DataManager;
import com.kingtong.jxc.domain.JingPinHuoDong;

public class NewDoingsAdapter  extends BaseAdapter  {
	private LayoutInflater inflater;       
	private List<JingPinHuoDong> list;     
	private Context context;

	public NewDoingsAdapter(Context mcontext, List<JingPinHuoDong> jingPinList) {
		this.context = mcontext;
		inflater = LayoutInflater.from(mcontext);
		this.list = jingPinList;
	}

	public int getCount() {
		return list.size();
	}

	public JingPinHuoDong getItem(int position) {
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
			view = inflater.inflate(R.layout.list_item_add_doings_list, null);
		}
		
		TextView name      = (TextView) view.findViewById(R.id.item_name_doings_form_name);
		TextView start     = (TextView) view.findViewById(R.id.item_name_doings_form_start);
		TextView end       = (TextView) view.findViewById(R.id.item_name_doings_form_end);
		TextView content   = (TextView) view.findViewById(R.id.item_name_doings_form_content);
		TextView delete    = (TextView) view.findViewById(R.id.item_name_doings_form_delete);
		
		if(position == 0) {
			name.setText("商品名称");
			start.setText("开始时间");
			end.setText("结束时间");
			content.setText("活动内容");
			delete.setText("删除  ");
			delete.setTextColor(Color.BLACK);
		} else {
			name.setText(list.get(position).getName());
			start.setText(TimeUtil.getFormatDate(list.get(position).getStartTiem()));
			end.setText(TimeUtil.getFormatDate(list.get(position).getEndTiem()));
			content.setText(list.get(position).getInfo());
			delete.setText(" X ");
			delete.setTextColor(Color.RED);
			final int use = position;
			delete.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					log("delete item:" + use);
					/*if(context instanceof AddDoingsListActivity) {
						AddDoingsListActivity screen = (AddDoingsListActivity) context;
						screen.deleteItem(use);
					}*/
					if(!DeviceUtil.isNetworkAvailable(context))
					{
						DeviceUtil.showNetSetCancelDialog(context);
						return;
					}
					
					// delete it from server
					DataManager manager = new DataManager(context);
					boolean result = manager.deleteHuodong(list.get(use).getId());
					if(result) {
						DoingsListController.getInstance().delete(use);
						notifyDataSetChanged();
						DeviceUtil.alert("删除成功！", context);
					} else {
						DeviceUtil.alert("删除失败！", context);
					}
				}

			});
		}
		return view;
	}
	
	private void log(String string) {
		Log.d(NewDoingsAdapter.class.getSimpleName(), string);
	}
	
}



