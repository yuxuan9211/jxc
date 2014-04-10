package com.kingtong.jxc.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kingtong.jxc.R;
import com.kingtong.jxc.adapter.DeliveryFormInfoAdapter;
import com.kingtong.jxc.common.DeviceUtil;
import com.kingtong.jxc.common.MyApplication;
import com.kingtong.jxc.conn.DataManager;
import com.kingtong.jxc.domain.SongHuoConfirm;
import com.kingtong.jxc.domain.SongHuoDetail;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 送货列表详情页面
 * @author Liu Leilei
 *
 */
public class DeliveryFormInfoActivity extends Activity {
	
	private ImageView backBtn = null;
	private Button confirm = null;
	private TextView deliverTitle = null;
	private TextView deliverAddress = null;
	private TextView deliverPhone = null;
	private TextView deliverMobile = null;
	private TextView deliverContactor = null;
	private ProgressDialog progressDialog = null;
	private Context context = DeliveryFormInfoActivity.this;///<页面当前上下文
	private int id = 0;
	private ListView list;
	private static final int PROGRESS_DIALOG = 1; ///<进度条对话框
	private SongHuoDetail detail = null;
	private static final int GET_SONG_HUO_DETAIL = 1;
	private static final int EDIT_SONG_HUO_COUNT = 2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_form_info);
        MyApplication.getInstance().addActivity(this);
        deliverTitle = (TextView)findViewById(R.id.deliver_title);
        deliverAddress = (TextView)findViewById(R.id.deliver_address);
        deliverPhone = (TextView)findViewById(R.id.deliver_phone);
        deliverMobile = (TextView)findViewById(R.id.deliver_mobile);
        deliverContactor = (TextView)findViewById(R.id.deliver_contactor);
        id = getIntent().getIntExtra("id", 0);
        confirm = (Button) findViewById(R.id.delivery_confirm_btn);
        list = (ListView) findViewById(R.id.delivery_form_info_list);
        backBtn = (ImageView)findViewById(R.id.back);
        initData();
        registeEvent();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
				SongHuoDetail detail = new DataManager(context).getSongHuoDetail(id);
		        if(null == detail) {
		        	if(progressDialog != null && progressDialog.isShowing())
					{
						dismissDialog(PROGRESS_DIALOG);
					}
		        	Log.e(UserVisitActivity.class.getSimpleName(), "get SongHuo detail is null");
		        	return;
		        }
		        // 发送消息
				Message msg = new Message();
				msg.what = GET_SONG_HUO_DETAIL;
				msg.getData().putSerializable("detail",(Serializable) detail);
				handler.sendMessage(msg);
			}
		}).start();
	}
    
    //在主线程中更新界面
   	Handler handler = new Handler()
     {
	       	public void handleMessage(Message msg)
	       	{
	       		if(progressDialog.isShowing())
	       		{
	       			dismissDialog(PROGRESS_DIALOG);
	       		}
	       		switch(msg.what)
	       		{
		       		case  GET_SONG_HUO_DETAIL: //获取送货详情
		       		{
		       			detail = (SongHuoDetail)msg.getData().getSerializable("detail");
		           		if(detail != null)
		           		{
		           			deliverTitle.setText(detail.getTitle());
		           			deliverAddress.setText(detail.getAddress());
		           			deliverPhone.setText(detail.getPhone());
		           			deliverMobile.setText(detail.getMobile());
		           			deliverContactor.setText(detail.getContactor());
		           		    //生成动态数组，加入数据  
		           	        List<Map<String,String>> listItem = new ArrayList<Map<String,String>>(); 
		           	        List<SongHuoConfirm> subList = detail.getOrderInfo();
		           	        if(subList == null) return;
		           	        for(int i = 0; i< subList.size(); i++)  
		           	        {
		           	            Map<String,String> item = new HashMap<String,String>();
		           	            SongHuoConfirm songHuoConfirm = subList.get(i);
		           	            item.put("name", songHuoConfirm.getName());
		           	            item.put("price",  ""+songHuoConfirm.getPrice());
		           	            item.put("count",  ""+songHuoConfirm.getCount());
		           	            item.put("count_confirm",  ""+songHuoConfirm.getConfirmCount());
		           	            listItem.add(item);
		           	        }   
		           	        //添加并且显示  
		           	        list.setAdapter(new DeliveryFormInfoAdapter(context, listItem, detail));
		           		}
		       			break;
		       		}
		       		case  EDIT_SONG_HUO_COUNT:
		       		{
		       			boolean result = msg.getData().getBoolean("submitResult");
		       			if(result)
		       			{
		       				Toast.makeText(context, "送货单数量确认成功", Toast.LENGTH_SHORT).show();
		       				finish();
		       			}else
		       			{
		       				Toast.makeText(context, "送货单数量确认失败！", Toast.LENGTH_SHORT).show();
		       			}
		       			break;
		       		}
	       		
	       		}
	       	}
       };
    
    /**
	 * 创建对话框
	 */
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
	
    public void registeEvent()
    {
    	backBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				finish();
			}});
    	
    	// 提交按钮
    	confirm.setOnClickListener(new OnClickListener() {
			
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			public void onClick(View v) {
				
				//检测是否有可用网络，无网络引导用户设置
				if(!DeviceUtil.isNetworkAvailable(context))
				{
					DeviceUtil.showNetSetCancelDialog(context);
					return;
				}
				showDialog(PROGRESS_DIALOG);
		    	//开启一条线程 提供新数据
				new Thread(new Runnable() {
					public void run() 
					{
						List<Integer> idList = new ArrayList<Integer>();
						List<Integer> confirmCountList = new ArrayList<Integer>();
						List<SongHuoConfirm> confirmList = detail.getOrderInfo();
						for(int i = 0; i <confirmList.size(); i ++)
						{
							SongHuoConfirm confirm = confirmList.get(i);
							int confirmCount = confirm.getConfirmCount();
							if(confirmCount <=0) continue;
							idList.add(confirm.getId());
							confirmCountList.add(confirmCount);
					     }
						if(confirmCountList.size() == 0)
						{
							Toast.makeText(context, "请填写确认数量！", Toast.LENGTH_LONG);
							return;
						}
						// 动态取得数据
						boolean submitResult = new DataManager(context).EditSongHuoDan(idList, confirmCountList);
						// 发送消息
						Message msg = new Message();
						msg.what = EDIT_SONG_HUO_COUNT;
						msg.getData().putBoolean("submitResult", submitResult);
						handler.sendMessage(msg);
				     }
			    }).start();
			}
		});
    }
}
