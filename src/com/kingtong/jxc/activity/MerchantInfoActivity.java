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

public class MerchantInfoActivity extends Activity {
	
	private ImageView backBtn = null;
	private TextView titleText = null;
	private TextView addText = null;
	private TextView telText = null;
	private TextView faxText = null;
	private TextView lxrText = null;
	private TextView mpText = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_info);
        MyApplication.getInstance().addActivity(this);
        backBtn = (ImageView)findViewById(R.id.back);
        titleText = (TextView) findViewById(R.id.shanghu_title);
        addText = (TextView) findViewById(R.id.shanghu_add_text);
        telText = (TextView) findViewById(R.id.shanghu_tel_text);
        faxText = (TextView) findViewById(R.id.shanghu_fax_text);
        lxrText = (TextView) findViewById(R.id.shanghu_lxr_text);
        mpText = (TextView) findViewById(R.id.shanghu_mp_text);
        
        registeEvent();
        
        // init content
        Intent intent = getIntent();
        String titleString = intent.getStringExtra("title");
        String addString = intent.getStringExtra("add");
        String telString = intent.getStringExtra("tel");
        String faxString = intent.getStringExtra("fax");
        String lxrString = intent.getStringExtra("lxr");
        String mpString = intent.getStringExtra("mp");
        titleText.setText(titleString);
        addText.setText(addString);
        telText.setText(telString);
        faxText.setText(faxString);
        lxrText.setText(lxrString);
        mpText.setText(mpString);
    }
    
    public void registeEvent()
    {
    	backBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				finish();
			}});
    }
}
