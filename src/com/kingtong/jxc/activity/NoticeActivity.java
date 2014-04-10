/****************************************************************************************
 * Copyright (c) 2010~2012 All Rights Reserved by
 *  G-Net Integrated Service Co.,  Ltd. 
 ****************************************************************************************/
/**
 * @file ProductActivity.java
 * @author wenhui.li
 * @date 2013-4-4 下午4:55:45 
 * Revision History 
 *     - 2013-4-4: change content for what reason
 ****************************************************************************************/

package com.kingtong.jxc.activity;

import java.util.ArrayList;
import java.util.List;
import com.kingtong.jxc.R;
import com.kingtong.jxc.adapter.NoticeAdapter;
import com.kingtong.jxc.common.AppConstant;
import com.kingtong.jxc.common.MyApplication;
import com.kingtong.jxc.conn.DataManager;
import com.kingtong.jxc.domain.GongGao;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class NoticeActivity  extends Activity {
	
	private ImageView backBtn = null;
	private ListView list = null;
    private Context context = NoticeActivity.this;///<页面当前上下文
	private int page = 0;// 当前页面
	private int lastVisibileItem = 0; 
    private List<GongGao> gonggaoData = new ArrayList<GongGao>();
    private NoticeAdapter adapter;
    private ProgressDialog progressDialog = null; ///<网络操作提示框
	private static final int PROGRESS_DIALOG = 1; ///<进度条对话框
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);
        MyApplication.getInstance().addActivity(this);
        list = (ListView) findViewById(R.id.notice_list); 
         
        //添加并且显示  
        adapter = new NoticeAdapter(this, gonggaoData);
        list.setAdapter(adapter); 
        backBtn = (ImageView)findViewById(R.id.back);
        registeEvent();
        
        initData();
        
        // register Scroll
        list.setOnScrollListener(new ScrollListener());
    }
    
    private void initData() {
    	showDialog(PROGRESS_DIALOG);
    	//开启一条线程 提供新数据
		new Thread(new Runnable() {
			public void run() {
				// 动态取得数据
				List<GongGao> list = new DataManager(context).getGonggaos(page, AppConstant.LIST_REFRESH_COUNT);
				if(null == list) 
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
		        	gonggaoData.add(list.get(i));
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
    		adapter.notifyDataSetChanged();
    		if(progressDialog != null && progressDialog.isShowing())
			{
				dismissDialog(PROGRESS_DIALOG);
			}
    	}
    };

	public void registeEvent()
    {
    	list.setOnItemClickListener(new OnItemClickListener()
    	{
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) 
			{
			    Intent intent = new Intent(context,NoticeInfoActivity.class);
			    intent.putExtra("title", gonggaoData.get(position).getTitle());
			    intent.putExtra("text", gonggaoData.get(position).getText());
				startActivity(intent);
			}
    	});
    	
    	backBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				showExitDialog();
			}});
    }
	
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
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if(scrollState == OnScrollListener.SCROLL_STATE_IDLE && (lastVisibileItem+1) == list.getCount())
			{
				showDialog(PROGRESS_DIALOG);
				//开启一条线程 提供新数据
				new Thread(new Runnable() 
				{
					public void run() 
					{
						// 动态取得数据
						List<GongGao> list = new DataManager(context).getGonggaos(page, AppConstant.LIST_REFRESH_COUNT);
						if(null == list) 
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
				        	gonggaoData.add(list.get(i));
				        } 
						//发送消息
						Message msg = new Message();
						handler.sendMessage(msg);
					}
				}).start();
			}
		}
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
		Intent intent = new Intent(NoticeActivity.this, WelcomeTableActivity.class);
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

