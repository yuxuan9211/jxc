package com.kingtong.jxc.activity;

import java.util.ArrayList;
import java.util.List;
import com.kingtong.jxc.R;
import com.kingtong.jxc.common.DeviceUtil;
import com.kingtong.jxc.common.DoingsListController;
import com.kingtong.jxc.common.MyApplication;
import com.kingtong.jxc.common.TimeUtil;
import com.kingtong.jxc.conn.DataManager;
import com.kingtong.jxc.domain.JingPinHuoDong;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddDoingsActivity extends Activity {
	private static final int SHOW_SELECT_JINGPIN = 0x001;
	
	private Context context = AddDoingsActivity.this;
	private ImageView backBtn = null;
	private TextView name = null;
	private EditText price = null;
	private TextView stime = null;
	private TextView etime = null;
	private EditText info = null;
	private Button confirm = null;
	private int pid = 0;// 竞品id
	private TextView merchantName;
    private DoingsListController manager = null;
    private String address = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_doings);
        MyApplication.getInstance().addActivity(this);
        
        backBtn = (ImageView)findViewById(R.id.back);
        merchantName = (TextView) findViewById(R.id.doings_for_merchant_title);
        merchantName.setText(getIntent().getStringExtra("title"));
        address = getIntent().getStringExtra("address"); 
        name = (TextView) findViewById(R.id.doings_name_text);
        price = (EditText) findViewById(R.id.doings_price_text);
        stime = (TextView) findViewById(R.id.doings_stime_text);
        etime = (TextView) findViewById(R.id.doings_etime_text);
        info = (EditText) findViewById(R.id.doings_info_text);
        confirm = (Button) findViewById(R.id.doings_confirm_btn);
        
        name.setText(Html.fromHtml("<u>" + getString(R.string.null_string) + "</u>"));
        stime.setText(Html.fromHtml("<u>" + TimeUtil.getFormatDate(System.currentTimeMillis())  + "(" +getString(R.string.add_select_time) + ")" + "</u>"));
        etime.setText(Html.fromHtml("<u>" + TimeUtil.getFormatDate(System.currentTimeMillis()) + "(" +getString(R.string.add_select_time) + ")" + "</u>"));
        
        // 初始化控制器
     	manager = DoingsListController.getInstance();
     	
        registeEvent();
    }
    
    public void registeEvent()
    {
    	backBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				finish();
			}});
    	
    	// 选择商品名称
    	name.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				showDialog(SHOW_SELECT_JINGPIN);
			}
		});
    	
    	// 选择开始时间
    	stime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TimeUtil.showDateDialog(stime, context);
			}
		});
    	
    	// 选择结束时间
    	etime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TimeUtil.showDateDialog(etime, context);
			}
		});
    	
    	// 提交按钮
    	confirm.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//检测是否有可用网络，无网络引导用户设置
				if(!DeviceUtil.isNetworkAvailable(context))
				{
					DeviceUtil.showNetSetCancelDialog(context);
					return;
				}
				if(verify()) 
				{
				
					Intent intent = getIntent();
					String flag = intent.getStringExtra("flag");
					if(flag == null || flag.equals("")) {
						DeviceUtil.alert("ERROR:intent error,not found flag[" + AddDoingsActivity.class.getSimpleName() + "]",context);
						return;
					}
					String productName = name.getText().toString().trim();
					if(productName.equalsIgnoreCase("点击添加"))
					{
						DeviceUtil.alert("请选择商品", context);
						return;
					}
					// 添加数据
					JingPinHuoDong bean = new JingPinHuoDong();
					bean.setId(pid);
					bean.setName(productName);
					bean.setPrice(Double.valueOf(price.getText().toString().trim()));
					bean.setStartTiem(TimeUtil.paserDateToString(stime.getText().toString().trim()));
					bean.setEndTiem(TimeUtil.paserDateToString(etime.getText().toString().trim()));
					bean.setInfo(info.getText().toString().trim());					
					
					List<JingPinHuoDong> lista = new ArrayList<JingPinHuoDong>();
					lista.add(bean);
					
					//if(flag.equals("new")) {// 创建第一条
						DataManager managerh = new DataManager(context);
						long cid = getIntent().getLongExtra("id", 0);
						String result = managerh.sendJingPinHuoDong(lista, cid);
						bean.setId(Integer.parseInt(result));
						manager.add(bean);
						//if(result)
						//{
							DeviceUtil.alert("新增竞品活动成功", context);	
							// 跳转到列表页面
			    			Intent sIntent = new Intent(context, AddDoingsListActivity.class);
			    			sIntent.putExtra("title",merchantName.getText().toString());
							sIntent.putExtra("address", address);
							sIntent.putExtra("cid", cid);
							startActivity(sIntent);
						//}else
						//{
						//	DeviceUtil.alert("新增竞品活动失败", context);	
						//}
						
					//} else {// 直接返回上个列表页面
					//	setResult(Activity.RESULT_OK);			
					//	DeviceUtil.alert(R.string.add_ok, context);
					//	finish();
					//}	
				}
			}
		});
    }
    
    //在主线程中更新界面
    Handler handler = new Handler()
    {
    	public void handleMessage(Message msg)
    	{
    		if(msg.arg1 == 0) {
    			//DeviceUtil.alert(R.string.post_ok,context);
    			// 跳转到列表页面
    			Intent sIntent = new Intent(context, AddDoingsListActivity.class);
    			sIntent.putExtra("title",merchantName.getText().toString());
				sIntent.putExtra("address", address);
				startActivity(sIntent);
    		} else {
    			DeviceUtil.alert(R.string.post_fialed,context);
    		}
    	}
    };

	protected boolean verify() {
		if(name.getText() == null || name.getText().toString().trim().equals("")) {
			DeviceUtil.alert(R.string.data_emputy,context);
			return false;
		}
		if(price.getText() == null || price.getText().toString().trim().equals("")) {
			DeviceUtil.alert(R.string.data_emputy,context);
			return false;
		}
		if(stime.getText() == null || stime.getText().toString().trim().equals("")) {
			DeviceUtil.alert(R.string.data_emputy,context);
			return false;
		}
		if(etime.getText() == null || etime.getText().toString().trim().equals("")) {
			DeviceUtil.alert(R.string.data_emputy,context);
			return false;
		}
		if(info.getText() == null || info.getText().toString().trim().equals("")) {
			DeviceUtil.alert(R.string.data_emputy,context);
			return false;
		}
		
		return true;
	}
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case SHOW_SELECT_JINGPIN:
	        SharedPreferences _data = context.getSharedPreferences("_jingpin", Context.MODE_WORLD_WRITEABLE);
	        String userString = _data.getString("0", null);
			List<String> list = new ArrayList<String>();
			List<Integer> idsList = new ArrayList<Integer>();

			int i=0;
			while (null != userString) {
				int index = userString.indexOf(':');
				String nameS = userString.substring(index+1);
				int idS = Integer.valueOf(userString.substring(0, index));
				list.add(nameS);
				idsList.add(idS);
				i++;
				userString = _data.getString("" + i, null);
			}
			final String[] ss = new String[list.size()];
			final Integer[] dd = new Integer[idsList.size()];
			list.toArray(ss);
			idsList.toArray(dd);
			Builder b = new AlertDialog.Builder(this);
			b.setTitle("选择竞品名称");
			b.setSingleChoiceItems(ss, 0, new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					pid = dd[which];
					name.setText(Html.fromHtml("<u>" + ss[which] + "</u>"));
				}
			});
			
			if(pid == 0)
			{
				pid = dd[0];
				name.setText(Html.fromHtml("<u>" + ss[0] + "</u>"));
			}
			// 添加一个“确定”按钮，用于关闭该对话框
			b.setPositiveButton("确定", null);
			// 创建对话框
			return b.create();
		default:
			break;
		}
		return null;
	}
}
