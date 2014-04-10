package com.kingtong.jxc.activity;

import com.kingtong.jxc.R;
import com.kingtong.jxc.common.MyApplication;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends  TabActivity {
	
	private Context context = MainActivity.this;///<页面当前上下文
	private TabHost tabHost;                                   
	private TabHost.TabSpec spec;                              
	private RadioGroup radioGroup;
	//private RadioButton homeRB;
	private RadioButton merchantRB; 
	private RadioButton productRB; 
	private RadioButton noticeRB; 
	private RadioButton personRB; 
	private Intent _intent; 
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MyApplication.getInstance().addActivity(this);
        findView();
        registerEvents();
        
    }
    
    private void findView()
    {
    	radioGroup = (RadioGroup)this.findViewById(R.id.mainRG);
    	//homeRB = (RadioButton)this.findViewById(R.id.radio_home);
    	merchantRB = (RadioButton)this.findViewById(R.id.radio_merchant);
    	productRB = (RadioButton)this.findViewById(R.id.radio_product);
    	noticeRB = (RadioButton)this.findViewById(R.id.radio_notice);
    	personRB = (RadioButton)this.findViewById(R.id.radio_person);
    	tabHost = getTabHost();
    	
    	Intent intent = getIntent();
 	    int flag = intent.getIntExtra("flag", 0);
    	
	    _intent = new Intent().setClass(this, UserVisitActivity.class);
	    _intent.setFlags(flag);
    	spec = tabHost.newTabSpec("uservist").setIndicator("uservist").setContent(_intent);
 	    tabHost.addTab(spec);
 	    
 	    _intent = new Intent().setClass(this, OrderFormActivity.class);
 	   	spec = tabHost.newTabSpec("orderform").setIndicator("orderform").setContent(_intent);
	    tabHost.addTab(spec);
	    
	    _intent = new Intent().setClass(this, DeliveryFormListActivity.class);
    	spec = tabHost.newTabSpec("deliveryform").setIndicator("deliveryform").setContent(_intent);
 	    tabHost.addTab(spec);
 	    
 	    _intent = new Intent().setClass(this, ComProductsActivity.class);
 	   	spec = tabHost.newTabSpec("comproducts").setIndicator("comproducts").setContent(_intent);
	    tabHost.addTab(spec);
 	    
	    _intent = new Intent().setClass(this, WelcomeTableActivity.class);
    	spec = tabHost.newTabSpec("home").setIndicator("home").setContent(_intent);
 	    tabHost.addTab(spec);
 	    
 	    _intent = new Intent().setClass(this, MerchantActivity.class);
 	    spec = tabHost.newTabSpec("merchant").setIndicator("merchant").setContent(_intent);
	    tabHost.addTab(spec);
	    
	    _intent = new Intent().setClass(this, ProductActivity.class);
    	spec = tabHost.newTabSpec("product").setIndicator("product").setContent(_intent);
 	    tabHost.addTab(spec);
 	    
 	    _intent = new Intent().setClass(this, NoticeActivity.class);
 	    spec = tabHost.newTabSpec("notice").setIndicator("notice").setContent(_intent);
	    tabHost.addTab(spec);
	    
	    _intent = new Intent().setClass(this, PersonalInfoActivity.class);
 	    spec = tabHost.newTabSpec("person").setIndicator("person").setContent(_intent);
	    tabHost.addTab(spec);
	    
	    switch(flag){
      	 case 1:
      		 tabHost.setCurrentTabByTag("uservist");
      		 break;
      	 case 2:
      		 tabHost.setCurrentTabByTag("orderform");
      		 break;
      	 case 3:
      		 tabHost.setCurrentTabByTag("deliveryform");
      		 break;
      	 case 4:
      		 tabHost.setCurrentTabByTag("comproducts");
      		 break;
      	 case 5:
      		 tabHost.setCurrentTabByTag("merchant");
      		 merchantRB.setChecked(true);
      		 break;
      	 case 6 :
      		 tabHost.setCurrentTabByTag("product");
      		 productRB.setChecked(true);
      		 break;
      	 case 7:
      		tabHost.setCurrentTabByTag("notice");
      		noticeRB.setChecked(true);
      		 break;
      	 case 8:
      		tabHost.setCurrentTabByTag("person");
      		personRB.setChecked(true);
      		 break;
  	 	}
	    
    }
    
    private void registerEvents() {
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(RadioGroup group, int checkedId) {
           	 switch(checkedId){
	           	 case R.id.radio_home:
	           		 /*tabHost.setCurrentTabByTag("home");*/
	           		 Intent intent = new Intent(context, WelcomeTableActivity.class);
	           		 startActivity(intent);
	           		 break;
	           	 case R.id.radio_merchant:
	           		 tabHost.setCurrentTabByTag("merchant");
	           		 break;
	           	 case R.id.radio_product :
	           		 tabHost.setCurrentTabByTag("product");
	           		 break;
	           	 case R.id.radio_notice:
	           		tabHost.setCurrentTabByTag("notice");
	           		 break;
	           	 case R.id.radio_person:
	           		tabHost.setCurrentTabByTag("person");
	           		 break;
           	 	}
        	}
        });
	}
   
}