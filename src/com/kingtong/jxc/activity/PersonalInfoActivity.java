package com.kingtong.jxc.activity;

import com.kingtong.jxc.R;
import com.kingtong.jxc.common.MyApplication;
import com.kingtong.jxc.conn.DataManager;
import com.kingtong.jxc.conn.MyHttpClient;
import com.kingtong.jxc.domain.GeRen;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonalInfoActivity extends Activity {
	private Context context = PersonalInfoActivity.this;
	private ImageView backBtn = null;
	private ProgressDialog progressDialog = null; ///<网络操作提示框
    private static final int PROGRESS_DIALOG = 1; ///<进度条对话框
	private Bitmap picBitmap = null;
	private GeRen geRen = null;
	private TextView name;
	private TextView tel;
	private ImageView photo;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);
        MyApplication.getInstance().addActivity(this);
        backBtn = (ImageView)findViewById(R.id.back);
        name = (TextView) findViewById(R.id.person_name);
        tel = (TextView) findViewById(R.id.person_tel);
        photo = (ImageView) findViewById(R.id.person_photo);
        registeEvent();
        getDataThread();
        
    }
    
    private void getDataThread() {
    	showDialog(PROGRESS_DIALOG);
    	//开启一条线程 提供新数据
		new Thread(new Runnable() {
			public void run() {
				SharedPreferences _data = context.getSharedPreferences("_data", Context.MODE_WORLD_WRITEABLE);
		        String useridString = _data.getString("user_id", null);
		        if(null == useridString) {
		        	log("get userid error");
		        	return;
		        }
		        
				// 动态取得数据
				geRen = new DataManager(context).getGeren(useridString);
				picBitmap = MyHttpClient.getBitmap(geRen.getPhoto());
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
    		log("dismiss");
    		if(null == geRen) {
    			return;
    		}
    		name.setText(geRen.getName());
    		tel.setText(geRen.getEmail());
    		if(null == picBitmap) {
    			return;
    		}
    		photo.setImageBitmap(picBitmap);
    	}
    };

	protected void log(String string) {
		Log.d(PersonalInfoActivity.class.getSimpleName(), string);
	}

	public void registeEvent()
    {
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
		Intent intent = new Intent(PersonalInfoActivity.this, WelcomeTableActivity.class);
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
