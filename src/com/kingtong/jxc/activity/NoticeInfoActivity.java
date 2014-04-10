package com.kingtong.jxc.activity;

import com.kingtong.jxc.R;
import com.kingtong.jxc.common.MyApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class NoticeInfoActivity extends Activity {

	private ImageView backBtn = null;
	private TextView titleText = null;
	private TextView contentText = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_info);
        MyApplication.getInstance().addActivity(this);
        backBtn = (ImageView)findViewById(R.id.back);
        titleText = (TextView) findViewById(R.id.gonggao_title);
        contentText = (TextView) findViewById(R.id.gonggao_content);
        registeEvent();
        
        // init content
        Intent intent = getIntent();
        String titleString = intent.getStringExtra("title");
        String contentString = intent.getStringExtra("text");
        titleText.setText(titleString);
        contentText.setText(contentString);
    }
    
    public void registeEvent()
    {
    	backBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				finish();
			}});
    }
}
