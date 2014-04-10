package com.kingtong.jxc.activity;

import com.kingtong.jxc.R;
import com.kingtong.jxc.adapter.NewDoingsAdapter;
import com.kingtong.jxc.common.DeviceUtil;
import com.kingtong.jxc.common.DoingsListController;
import com.kingtong.jxc.common.MyApplication;
import com.kingtong.jxc.conn.DataManager;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class AddDoingsListActivity extends Activity {
	private Context context = AddDoingsListActivity.this;///<页面当前上下文
	private ImageView backBtn = null;
	private ListView list = null;
	private Button add = null;
	//private Button submit = null;
	private DoingsListController manager = null;
	private NewDoingsAdapter adapter = null;
	//private List<JingPinHuoDong> showList = null;
	private ProgressDialog progressDialog = null; ///<网络操作提示框
	private static final int PROGRESS_DIALOG = 1; ///<进度条对话框
	private int lastVisibileItem = 0; 
	private long cid = 0; 
	private TextView merchantTitle = null;
	private TextView merchantAddress = null; 
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_doings_list);
		MyApplication.getInstance().addActivity(this);
		list = (ListView) findViewById(R.id.add_doings_list_view);
		//showList = new ArrayList<JingPinHuoDong>();
		merchantTitle = (TextView) findViewById(R.id.add_doings_list_title);
		merchantAddress = (TextView) findViewById(R.id.add_doings_list_address);
		
		Intent intent = getIntent();
		merchantTitle.setText(intent.getStringExtra("title"));
		cid = intent.getLongExtra("cid", 0);
		merchantAddress.setText(intent.getStringExtra("address"));
		// 初始化控制器
		manager = DoingsListController.getInstance();
		log("list size=" + manager.getListSize());
		
		//添加并且显示  
		adapter = new NewDoingsAdapter(this, manager.getList());
        list.setAdapter(adapter); 
        backBtn = (ImageView)findViewById(R.id.back);
        add     = (Button) findViewById(R.id.add_doing_list_add_btn);
        //submit  = (Button) findViewById(R.id.add_doings_list_submit_btn);
        //initData();
        registeEvent();
        //register Scroll
        list.setOnScrollListener(new ScrollListener());
	}

	private void log(String string) {
		Log.d(AddDoingsListActivity.class.getSimpleName(), string);
	}

	private void registeEvent() {
    	backBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				finish();
			}});
    	// Add
    	add.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(AddDoingsListActivity.this, AddDoingsActivity.class);
			    intent.putExtra("flag", "add");
			    intent.putExtra("title", merchantTitle.getText().toString());
			    intent.putExtra("address", merchantAddress.getText().toString());			   
			    intent.putExtra("id", cid);
				startActivityForResult(intent, 0);
			}
		});
    	
    	// Commit
    	/*submit.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
				//DeviceUtil.alert("commit list", context);
				
				if(manager.getListSize() < 2) {
					DeviceUtil.alert(R.string.data_emputy,context);
					return;
				}
				//弹出旋转框登陆
				showDialog(PROGRESS_DIALOG);
				new Thread(new Runnable() {
					public void run() {
						DataManager managerh = new DataManager(context);
						long cid = getIntent().getLongExtra("id", 0);
						boolean result = managerh.sendJingPinHuoDong(manager.getList(), cid);
						//发送消息
						Message msg = new Message();
						if (result) {
							msg.arg1=0;
						} else {
							msg.arg1=1;
						}
						handler.sendMessage(msg);
					}
				}).start();
			}
		});*/
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
    		if(msg.arg1 == 0) {
    			DeviceUtil.alert(R.string.post_ok,context);
    			if(manager != null)
    			{
    				manager.reset();
    			} 
				Intent intent = new Intent(context, MainActivity.class);
				intent.putExtra("flag", 1);
				startActivity(intent);
    		} else if(msg.arg1 == 1) {
    			DeviceUtil.alert(R.string.post_fialed,context);
    		} else if(msg.arg1 == 2) {
    			DeviceUtil.alert("删除失败",context);
    		}
    	}
    };
	
	/**
	 * 接受页面返回数据
	 */
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	log("requestcode=" + requestCode + ";resultcode=" + resultCode + ";data=" + data);
    	
    	if(resultCode == Activity.RESULT_OK) {
    		adapter.notifyDataSetChanged();
    	}

	}
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(context, MainActivity.class);
			intent.putExtra("flag", 1);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
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
						DataManager managerh = new DataManager(context);
						long cid = getIntent().getLongExtra("id", 0);
						//boolean result = managerh.sendJingPinHuoDong(manager.getList(), cid);
						
						//发送消息
						//Message msg = new Message();
						//if (result) {
						//	msg.arg1=0;
						//} else {
						//	msg.arg1=1;
						//}
						//发送消息
						//handler.sendMessage(msg);
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
