package com.kingtong.jxc.activity;

import java.util.ArrayList;
import java.util.List;
import com.kingtong.jxc.R;
import com.kingtong.jxc.adapter.ComProductsAdapter;
import com.kingtong.jxc.common.AppConstant;
import com.kingtong.jxc.common.MyApplication;
import com.kingtong.jxc.conn.DataManager;
import com.kingtong.jxc.domain.JingPin;
import android.app.Activity;
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

/**
 * 竞品信息列表页面
 * @author Liu Leilei
 *
 */
public class ComProductsActivity extends Activity {
	
	private ImageView backBtn = null;
	private ListView list = null;
	private Context context = ComProductsActivity.this;///<页面当前上下文
	private ProgressDialog progressDialog = null; ///<网络操作提示框
	private static final int PROGRESS_DIALOG = 1; ///<进度条对话框
	private int page = 0;// 当前页面
	private int lastVisibileItem = 0; 
    private List<JingPin> jingpinData = new ArrayList<JingPin>();
    private ComProductsAdapter adapter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.com_products);
        MyApplication.getInstance().addActivity(this);
        backBtn = (ImageView)findViewById(R.id.back);
        list = (ListView) findViewById(R.id.com_products_list);
         
        //添加并且显示
        adapter = new ComProductsAdapter(this, jingpinData);
        list.setAdapter(adapter); 
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
				List<JingPin> list = new DataManager(context).getJingpins(page, AppConstant.LIST_REFRESH_COUNT);
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
		        	jingpinData.add(list.get(i));
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
			    Intent intent = new Intent(context,DoingsInfoActivity.class);
			    intent.putExtra("title", jingpinData.get(position).getTitle());
			    intent.putExtra("id", jingpinData.get(position).getId());
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
						List<JingPin> list = new DataManager(context).getJingpins(page, AppConstant.LIST_REFRESH_COUNT);
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
				        	jingpinData.add(list.get(i));
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
		Intent intent = new Intent(ComProductsActivity.this, WelcomeTableActivity.class);
		startActivity(intent);
	}
	
}
