package com.kingtong.jxc.activity;

import java.util.List;
import com.kingtong.jxc.R;
import com.kingtong.jxc.common.DeviceUtil;
import com.kingtong.jxc.common.MyApplication;
import com.kingtong.jxc.conn.DataManager;
import com.kingtong.jxc.domain.Chanpin;
import com.kingtong.jxc.domain.JingPin;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends Activity {
	private Context context = LoginActivity.this;
	private EditText username;
	private EditText userid;
	private CheckBox savePasswd;
    private Button loginBtn = null;
    private ProgressDialog loginProgressDialog;
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        MyApplication.getInstance().addActivity(this);
        findView();
        registeEvent();
        
        // 设置用户名和密码
        SharedPreferences _data = context.getSharedPreferences("_data", Context.MODE_WORLD_WRITEABLE);
        String userString = _data.getString("user_name", null);
        if(null != userString) {
        	username.setText(userString);
        }
        String passString = _data.getString("user_passwd", null);
        if(null != passString) {
        	userid.setText(passString);
        	savePasswd.setChecked(true);
        }
    }
    
    private void findView()
    {
    	username   = (EditText) findViewById(R.id.username_text);
    	userid     = (EditText) findViewById(R.id.userid_text);
    	loginBtn   = (Button)findViewById(R.id.login_btn);
    	savePasswd = (CheckBox) findViewById(R.id.save_password);
    }
    
    
    private void registeEvent()
    {
    	loginBtn.setOnClickListener(new View.OnClickListener(){

			public void onClick(View v) {
				
				if(verify()) {
					//检测是否有可用网络，无网络引导用户设置
					if(!DeviceUtil.isNetworkAvailable(context))
					{
						DeviceUtil.showNetSetCancelDialog(context);
						return;
					}
					//弹出旋转框登陆
					loginProgressDialog = ProgressDialog.show(context, "", getString(R.string.login_loading),true,true);
					
					new Thread(new Runnable() {
						public void run() {
							String usernameString = username.getText().toString().trim();
							String useridString   = userid.getText().toString().trim();
							boolean savePasswdBool = savePasswd.isChecked();
							boolean result = new DataManager(context).login(usernameString, useridString, savePasswdBool);
							//发送消息
							Message msg = new Message();
							if(result) {
								msg.arg1 = 0;
							} else {
								msg.arg1 = 1;
							}
							handler.sendMessage(msg);
						}

					}).start();
					
				}
			}
    	});
    	
    }
    
	private void logd(String string) {
		Log.d(LoginActivity.class.getSimpleName(), string);
	}
    
    protected boolean verify() {
    	String usernameString = username.getText().toString().trim();
		String useridString   = userid.getText().toString().trim();
		if(useridString.equals("") || usernameString.equals("")) {
			DeviceUtil.alert(R.string.login_emputy_input, context);
			return false;
		}
		return true;
	}

	//在主线程中更新界面
    Handler handler = new Handler()
    {
    	public void handleMessage(Message msg)
    	{
			loginProgressDialog.dismiss();
			if(msg.arg1 == 0) {
				Intent intent = new Intent(LoginActivity.this, WelcomeTableActivity.class);
				startActivity(intent);
				// 启动后台更新产品列表线程
				new Thread() {
					public void run() {
						logd("update chanpin list");
						DataManager manage = new DataManager(context);
						List<Chanpin> list = manage.getAllChanpin();
						if(null != list && list.size() > 0) {
							// 删除旧的sp
							SharedPreferences _chanpin = context.getSharedPreferences("_chanpin", Context.MODE_WORLD_WRITEABLE);
							Editor editor = _chanpin.edit();
							editor.clear();
							editor.commit();
							// 写入新的sp
							for (int i=0,len=list.size(); i<len; i++) {
								int key = i;
								editor.putString("" + key, "" + list.get(i).getId() + ":" + list.get(i).getTitle());
							}
							editor.commit();
						}
						logd("update jingpin list");
						List<JingPin> jList = manage.getJingpinsAll();
						if(null != jList && jList.size() > 0) {
							// 删除旧的sp
							SharedPreferences _jingpin = context.getSharedPreferences("_jingpin", Context.MODE_WORLD_WRITEABLE);
							Editor editor = _jingpin.edit();
							editor.clear();
							editor.commit();
							// 写入新的sp
							for (int i=0,len=jList.size(); i<len; i++) {
								int key = i;
								editor.putString("" + key, "" + jList.get(i).getId() + ":" + jList.get(i).getTitle());
							}
							editor.commit();
						}
					}
				}.start();
			} else {
				DeviceUtil.alert(R.string.login_failed, context);
			}
    	}
    };
    
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
				finish();
				System.exit(0);
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