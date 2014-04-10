package com.kingtong.jxc.activity;

import com.kingtong.jxc.R;
import com.kingtong.jxc.common.MyApplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeTableActivity extends Activity {
	
	private TextView username;
	ImageView usersVist = null;
	ImageView orderForm = null;
	ImageView deliveryForm = null;
	ImageView comProducts = null;
	ImageView merchantInfo = null;
	ImageView productInfo = null;
	ImageView announcementInfo = null;
	ImageView personalInfo = null;
	private Context context = WelcomeTableActivity.this;///<页面当前上下文
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_table);
        Intent intent = getIntent();
		if (intent.hasExtra("exit")) {
		    // User wants to exit
		    System.exit(0);
		}
        MyApplication.getInstance().addActivity(this);
        findView();
        registeEvent();
        
        // 设置用户名
        SharedPreferences _data = context.getSharedPreferences("_data", Context.MODE_WORLD_WRITEABLE);
        String userString = _data.getString("user_name", null);
        if(null != userString) {
        	username.setText(userString);
        }
    }
    
    private void findView()
    {
    	username  = (TextView) findViewById(R.id.wel_username);
    	usersVist = (ImageView)findViewById(R.id.users_visit);
    	orderForm = (ImageView)findViewById(R.id.order_form);
    	deliveryForm = (ImageView)findViewById(R.id.delivery_form);
    	comProducts = (ImageView)findViewById(R.id.com_products);
    	merchantInfo = (ImageView)findViewById(R.id.merchant_info);
    	productInfo = (ImageView)findViewById(R.id.product_info);
    	announcementInfo = (ImageView)findViewById(R.id.announcement_info);
    	personalInfo = (ImageView)findViewById(R.id.personal_info);
    	 
    }
    
    private void registeEvent()
    {
    	usersVist.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(context, MainActivity.class);
				intent.putExtra("flag", 1);
				startActivity(intent);
			}});
    	
    	orderForm.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(context, MainActivity.class);
				intent.putExtra("flag", 2);
				startActivity(intent);
			}});
    	
    	
    	deliveryForm.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(context, MainActivity.class);
				intent.putExtra("flag", 3);
				startActivity(intent);
			}});
    	
    	comProducts.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(context, MainActivity.class);
				intent.putExtra("flag", 4);
				startActivity(intent);
			}});
    	
    	
    	merchantInfo.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(context, MainActivity.class);
				intent.putExtra("flag", 5);
				startActivity(intent);
			}});
    	
    	productInfo.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(context, MainActivity.class);
				intent.putExtra("flag", 6);
				startActivity(intent);
			}});
    	
    	announcementInfo.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(context, MainActivity.class);
				intent.putExtra("flag", 7);
				startActivity(intent);
			}});
    	
    	personalInfo.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(context, MainActivity.class);
				intent.putExtra("flag", 8);
				startActivity(intent);
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
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("提醒");
		builder.setMessage("确定退出？");
		
		builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				MyApplication.getInstance().exit(context);
			}
		});
		
		builder.setNegativeButton("取消", new AlertDialog.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		builder.create().show();
	}
    
}
