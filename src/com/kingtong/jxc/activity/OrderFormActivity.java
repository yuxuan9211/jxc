/****************************************************************************************
 * Copyright (c) 2010~2012 All Rights Reserved by
 *  G-Net Integrated Service Co.,  Ltd. 
 ****************************************************************************************/
/**
 * @file OrderFormActivity.java
 * @author wenhui.li
 * @date 2013-4-5 下午3:01:27 
 * Revision History 
 *     - 2013-4-5: change content for what reason
 ****************************************************************************************/

package com.kingtong.jxc.activity;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import com.kingtong.jxc.R;
import com.kingtong.jxc.adapter.OrderListAdapter;
import com.kingtong.jxc.common.AppConstant;
import com.kingtong.jxc.common.DeviceUtil;
import com.kingtong.jxc.common.MyApplication;
import com.kingtong.jxc.conn.DataManager;
import com.kingtong.jxc.domain.DingDan;


public class OrderFormActivity   extends Activity {
	
	private ImageView backBtn = null;
	private ListView list = null;
    private Context context = OrderFormActivity.this;///<页面当前上下文
    private ProgressDialog progressDialog = null; ///<网络操作提示框
	private static final int PROGRESS_DIALOG = 1; ///<进度条对话框
	private int page = 0;// 当前页面
	private int lastVisibileItem = 0;  
	private List<DingDan> dingDanData = new ArrayList<DingDan>();
	private OrderListAdapter adapter;
	 
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderform);
        MyApplication.getInstance().addActivity(this);
        list = (ListView) findViewById(R.id.order_form_list);
        //添加并且显示  
        adapter = new OrderListAdapter(this, dingDanData);
        list.setAdapter(adapter); 
        backBtn = (ImageView)findViewById(R.id.back);
        registeEvent();
        initData();
        // register Scroll
        list.setOnScrollListener(new ScrollListener());
    }
    
    private void initData() {
    	//检测是否有可用网络，无网络引导用户设置
		if(!DeviceUtil.isNetworkAvailable(context))
		{
			DeviceUtil.showNetSetCancelDialog(context);
			return;
		}
		showDialog(PROGRESS_DIALOG);
    	//开启一条线程 提供新数据
		new Thread(new Runnable() {
			public void run() {
				// 动态取得数据
				List<DingDan> list = new DataManager(context).getDingDans(page, AppConstant.LIST_REFRESH_COUNT);
		        if(null == list) 
		        {
		        	if(progressDialog != null && progressDialog.isShowing())
					{
						dismissDialog(PROGRESS_DIALOG);
					}
		        	Log.e(UserVisitActivity.class.getSimpleName(), "get list is null");
		        	return;
		        } else {
					page++;
					for(int i=0,len=list.size();i<len;i++)  
			        {
						dingDanData.add(list.get(i));
			        } 
		        }
				// 发送消息
				Message msg = new Message();
				handler.sendMessage(msg);
			}
		}).start();
	}
    
    //在主线程中更新界面
   	Handler handler = new Handler()
       {
       	public void handleMessage(Message msg)
       	{
       		if(progressDialog != null && progressDialog.isShowing())
			{
				dismissDialog(PROGRESS_DIALOG);
			}
       		adapter.notifyDataSetChanged();
       		
       	}
       };
  
       /**
   	 * @brief 处理滑动事件的类
   	 */
       private final class ScrollListener implements OnScrollListener
       {
       	//滑动事件
   		public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) 
   		{
   			lastVisibileItem = firstVisibleItem + visibleItemCount -1;
   		}
   		

   		//滑动的状态改变之后
   		public void onScrollStateChanged(AbsListView view, int scrollState)
   		{
   			if(scrollState == OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem+1) == list.getCount())
   			{
   				showDialog(PROGRESS_DIALOG);
   				//开启一条线程 提供新数据
   				new Thread(new Runnable() 
   				{
   					public void run() 
   					{
   						// 动态取得数据
   						List<DingDan> list = new DataManager(context).getDingDans(page, AppConstant.LIST_REFRESH_COUNT);
   						if(list == null)
					    {
   							if(progressDialog != null && progressDialog.isShowing())
							{
								dismissDialog(PROGRESS_DIALOG);
							}
				        	return;
					    }
   						page++;
   						for(int i=0,len=list.size();i<len;i++)  
   				        {
   							dingDanData.add(list.get(i));
   				        }  
   						//发送消息
   						Message msg = new Message();
   						handler.sendMessage(msg);
   					}
   				}).start();
   				
   			}
   		}
       }
       
    public void registeEvent()
    {
    	list.setOnItemClickListener(new OnItemClickListener()
    	{
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) 
			{
				DingDan dingDan =  dingDanData.get(position);
				int pid = dingDan.getId();
				String productName = dingDan.getName();
				Intent intent = new Intent(context,ProductListActivity.class);
				intent.putExtra("pid", pid);
				intent.putExtra("pname", productName);
				startActivity(intent);
			}
    	});
    	
    	backBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				showExitDialog();
			}});
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			showExitDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 退出对话框
	 */
	private void showExitDialog() {
		finish();
		Intent intent = new Intent(OrderFormActivity.this, WelcomeTableActivity.class);
		startActivity(intent);
	}
    
	 @Override
		protected Dialog onCreateDialog(int id)
		{
			switch (id) 
			{
		        case PROGRESS_DIALOG:
		        {
		        	progressDialog = ProgressDialog.show(context,"", context.getString(R.string.data_loading),true,false);
		        	progressDialog.setCancelable(true);
		        	return progressDialog;
		        }
			}
			return null;
		}
}

