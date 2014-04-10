package com.kingtong.jxc.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.kingtong.jxc.R;
import com.kingtong.jxc.adapter.OrderFormAdapter;
import com.kingtong.jxc.common.DeviceUtil;
import com.kingtong.jxc.common.MyApplication;
import com.kingtong.jxc.conn.DataManager;
import com.kingtong.jxc.domain.DingDan;
import com.kingtong.jxc.domain.DingDanDetail;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 订单列表页面
 * @author Liu Leilei
 *
 */
public class OrderFormInfoActivity extends Activity {
	
	private ImageView backBtn = null;
	private TextView orderName = null;
	private TextView orderAddress = null;
	private TextView orderPhone = null;
	private TextView orderMobile = null;
	private TextView orderContactor = null;
	private TextView orderSendTime = null;
	private ProgressDialog progressDialog = null;
	private Context context = OrderFormInfoActivity.this;///<页面当前上下文
	private int oId = 0;
	private ListView list;
	private static final int PROGRESS_DIALOG = 1; ///<进度条对话框
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_form_info);
        MyApplication.getInstance().addActivity(this);
        orderName = (TextView)findViewById(R.id.order_name);
        orderAddress = (TextView)findViewById(R.id.order_address);
        orderPhone = (TextView)findViewById(R.id.order_phone);
        orderMobile = (TextView)findViewById(R.id.order_mobile);
        orderContactor = (TextView)findViewById(R.id.order_contactor);
        orderSendTime = (TextView)findViewById(R.id.order_send_time);
        oId = getIntent().getIntExtra("oid", 0);
        list = (ListView) findViewById(R.id.order_form_list);
        backBtn = (ImageView)findViewById(R.id.back);
        initData();
        registeEvent();
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
				DingDanDetail detail = new DataManager(context).getDingDanDetail(oId);
		        if(null == detail) 
		        {
		        	if(progressDialog !=null && progressDialog.isShowing())
		        	{
		        		dismissDialog(PROGRESS_DIALOG);
		        		Log.e(UserVisitActivity.class.getSimpleName(), "get DingDan detail is null");
		        		return;
		        	}
		        }
		        // 发送消息
				Message msg = new Message();
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
       		DingDanDetail detail = (DingDanDetail)msg.getData().getSerializable("detail");
       		if(detail != null)
       		{
       			orderName.setText(detail.getName());
       			orderAddress.setText(detail.getAddress());
       			orderPhone.setText(detail.getPhone());
       			orderMobile.setText(detail.getMobile());
       			orderContactor.setText(detail.getContactor());
       			orderSendTime.setText(detail.getSendTime());
       		    //生成动态数组，加入数据  
       	        List<Map<String,String>> listItem = new ArrayList<Map<String,String>>(); 
       	        List<DingDan> subList = detail.getDingDanList();
       	        if(subList == null) return;
       	        for(int i = 0; i< subList.size(); i++)  
       	        {
       	            Map<String,String> item = new HashMap<String,String>();
       	            DingDan dingDan = subList.get(i);
       	            item.put("name", dingDan.getName());
       	            item.put("price",  ""+dingDan.getPrice());
       	            item.put("count",  ""+dingDan.getCount());
       	            listItem.add(item);
       	        }   
       	        //添加并且显示  
       	        list.setAdapter(new OrderFormAdapter(context, listItem));
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
	
}
