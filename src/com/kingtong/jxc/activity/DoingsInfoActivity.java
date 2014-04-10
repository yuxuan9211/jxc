package com.kingtong.jxc.activity;

import java.util.ArrayList;
import java.util.List;
import com.kingtong.jxc.R;
import com.kingtong.jxc.adapter.DoingsAdapter;
import com.kingtong.jxc.common.AppConstant;
import com.kingtong.jxc.common.MyApplication;
import com.kingtong.jxc.conn.DataManager;
import com.kingtong.jxc.domain.HuoDong;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

/**
 * 活动列表页面
 * @author Liu Leilei
 *
 */
public class DoingsInfoActivity extends Activity {
	private Context context = DoingsInfoActivity.this;///<页面当前上下文
	private ImageView backBtn = null;
	private TextView jingpinText = null;
	private ListView list = null;
	private int lastVisibileItem = 0; 
	private int page = 0;// 当前页面
	private List<HuoDong> huodongData = new ArrayList<HuoDong>();
	private DoingsAdapter adapter;
	private ProgressDialog progressDialog = null; ///<网络操作提示框
	private static final int PROGRESS_DIALOG = 1; ///<进度条对话框
	
	private long id=0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doings_info);
        MyApplication.getInstance().addActivity(this);
        list = (ListView) findViewById(R.id.doings_list);  
         
        //添加并且显示
        adapter = new DoingsAdapter(this, huodongData);
        list.setAdapter(adapter);
        backBtn     = (ImageView)findViewById(R.id.back);
        jingpinText = (TextView) findViewById(R.id.jingpin_title_text);
        registeEvent();
        
        // init content
        Intent intent = getIntent();
        String titleString = intent.getStringExtra("title");
        jingpinText.setText(titleString);
        id=intent.getLongExtra("id", 0);
        
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
				List<HuoDong> list = new DataManager(context).getHuodongs(page, AppConstant.LIST_REFRESH_COUNT, id);
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
		        	huodongData.add(list.get(i));
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
    	backBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				finish();
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
						List<HuoDong> list = new DataManager(context).getHuodongs(page, AppConstant.LIST_REFRESH_COUNT, 0);
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
				        	huodongData.add(list.get(i));
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
