package com.kingtong.jxc.activity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import com.kingtong.jxc.R;
import com.kingtong.jxc.common.DeviceUtil;
import com.kingtong.jxc.common.JsonUtil;
import com.kingtong.jxc.common.MyApplication;
import com.kingtong.jxc.conn.Api;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.baidu.mapapi.BMapManager;  
import com.baidu.mapapi.LocationListener;  
import com.baidu.mapapi.MKLocationManager;  

public class UserVisitInfoActivity extends Activity implements LocationListener{
	
	private ImageView backBtn = null;
	private Button addOrder = null;
	private Button addDoings = null;
	private Button displayBtn = null;
	private TextView title   = null;
	private TextView address = null;
	private TextView tel   = null;
	private TextView fax     = null;
	private TextView contact = null;
	private TextView mPhoto  = null;
	private long merchantId =0;
	private Context context = UserVisitInfoActivity.this;///<页面当前上下文
	private double latitude = 0.0;
	private double longitude = 0.0;
	private BMapManager mapManager;  
    private MKLocationManager mLocationManager = null; 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_visit_info);
        MyApplication.getInstance().addActivity(this);
        
        //初始化baiduMap 接口
        mapManager = new BMapManager(context);  
        // init方法的第一个参数需填入申请的API Key  
        mapManager.init("29F896DF968C8B7CF38C9DC539E6349DF65B65C5", null);  
        
        mLocationManager = mapManager.getLocationManager();  
        // 注册位置更新事件  
        mLocationManager.requestLocationUpdates(this);  
        // 使用GPS定位  
        mLocationManager.enableProvider((int) MKLocationManager.MK_GPS_PROVIDER); 
        
        backBtn = (ImageView)findViewById(R.id.back);
        addOrder = (Button) findViewById(R.id.add_order_button);
        addDoings = (Button) findViewById(R.id.add_doings_button);
        displayBtn = (Button)findViewById(R.id.products_display_button);
        title    = (TextView) findViewById(R.id.vi_sahnghu_title_text);
        address  = (TextView) findViewById(R.id.vi_shanghu_add_text);
        tel      = (TextView) findViewById(R.id.vi_shanghu_tel_text);
        fax      = (TextView) findViewById(R.id.vi_shanghu_fax_text);
        contact  = (TextView) findViewById(R.id.vi_shanghu_contact_text);
        mPhoto   = (TextView) findViewById(R.id.vi_shanghu_m_photo_text);
        
        registeEvent();
        
        // init content
        Intent intent = getIntent();
        String titleString = intent.getStringExtra("title");
        String addString = intent.getStringExtra("add");
        String telString = intent.getStringExtra("tel");
        String faxString = intent.getStringExtra("fax");
        String lxrString = intent.getStringExtra("lxr");
        String mpString = intent.getStringExtra("mp");
        merchantId = intent.getLongExtra("merchantId", 0);
        if(null != titleString) {
        	title.setText(titleString);
        }
        if(null != addString) {
        	address.setText(addString);
        }
        if(null != telString) {
        	tel.setText(telString);
        }
        if(null != faxString) {
        	fax.setText(faxString);
        }
        if(null != lxrString) {
        	contact.setText(lxrString);
        }
        if(null != mpString) {
        	 mPhoto.setText(mpString);
        }
       
    }
    
    public void registeEvent()
    {
    	backBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				finish();
			}});
    	
    	displayBtn.setOnClickListener(new Button.OnClickListener(){  
            public void onClick(View v) {  
            	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }  
            });  
    	addOrder.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
			    Intent intent = new Intent(UserVisitInfoActivity.this, AddOrderActivity.class);
			    intent.putExtra("title", title.getText());
			    intent.putExtra("flag", "new");
			    intent.putExtra("id", getIntent().getLongExtra("id", 0));
			    intent.putExtra("address", address.getText().toString());
				startActivity(intent);
			}
		});
    	
    	addDoings.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
			    Intent intent = new Intent(UserVisitInfoActivity.this, AddDoingsActivity.class);
			    intent.putExtra("title", title.getText());
			    intent.putExtra("flag", "new");
			    intent.putExtra("id", getIntent().getLongExtra("id", 0));
			    intent.putExtra("address", address.getText().toString());
				startActivity(intent);
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

        	Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
            if(bitmap != null)
            {
            	 String fileName = null;
                 File file = null;
                 if(bitmap != null)
                 {
                 	fileName = "" + System.currentTimeMillis() + ".png";
                 }
             	String sdStatus = Environment.getExternalStorageState();
                 if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) // 检测sd是否可用
                 { 
                     Log.v("TestFile", "SD card is not avaiable/writeable right now.");
                     file = DeviceUtil.saveFileToDevice(this, bitmap, fileName);
                 }else
                 {
                 	file = DeviceUtil.saveFileToSDCard(bitmap, fileName);
                 }
                 //检测是否有可用网络，无网络引导用户设置
         		 if(!DeviceUtil.isNetworkAvailable(context))
         		 {
         			DeviceUtil.showNetSetCancelDialog(context);
         			return;
         		 }
                 //上传图片文件
                 if(uploadFile(fileName, file))
                 {
                	 ((ImageView) findViewById(R.id.products_image)).setImageBitmap(bitmap);// 将图片显示在ImageView里
                	 //Toast.makeText(context, "上传图片图片成功!", Toast.LENGTH_SHORT);
                 	
                 }else
                 {
                	 ((ImageView) findViewById(R.id.products_image)).setImageBitmap(null);// 将图片显示在ImageView里
                	 //Toast.makeText(context, "上传图片图片失败!", Toast.LENGTH_SHORT);
                 }
                 //删除本地缓存
                 DeviceUtil.delImageFile(file);
            }
        }
    }
    
    private boolean uploadFile(String fileName, File file)
    {
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		boolean uploadResult = false;
		SharedPreferences _data = context.getSharedPreferences("_data", Context.MODE_WORLD_WRITEABLE);
		int userId = Integer.parseInt(_data.getString("user_id", "0"));
		String json =JsonUtil.makeUploadProductJsonString(""+userId, ""+merchantId, ""+latitude, ""+longitude);
		try
	    { 
			URL url =new URL(Api.API_UPLOAD_IMAGE);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();       
	       
	        conn.setReadTimeout(5 * 1000);
	        conn.setDoInput(true);// 允许输入
	        conn.setDoOutput(true);// 允许输出
	        conn.setUseCaches(false);
	        conn.setRequestMethod("POST");  //Post方式
	        conn.setRequestProperty("connection", "keep-alive");
	        conn.setRequestProperty("Charsert", "UTF-8");
	        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
	       
	          // 首先组拼文本类型的参数
	        StringBuilder sb = new StringBuilder();
	        sb.append(PREFIX);
	        sb.append(BOUNDARY);
	        sb.append(LINEND);
	        sb.append("Content-Disposition: form-data; name=\""
	               + "req" + "\"" + LINEND);
	        sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
	        sb.append(LINEND);
	        sb.append(json);
	        sb.append(LINEND); 
	       
	        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
	        outStream.write(sb.toString().getBytes());
	
			// 发送文件数据
			if (file != null) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"" + LINEND);
				sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());
				outStream.flush();
	
				InputStream is = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
	
				is.close();
				outStream.write(LINEND.getBytes());
			}
	
			// 请求结束标志
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(end_data);
			outStream.flush();
			outStream.close();
			// 得到响应码
			int res = conn.getResponseCode();
			if (res == 200) 
			{
				   //当正确响应时处理数据  
		           StringBuffer sbb = new StringBuffer();  
		           String readLine;  
		           BufferedReader responseReader;  
		           //处理响应流，必须与服务器响应流输出的编码一致  
		           responseReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));  
		           while ((readLine = responseReader.readLine()) != null) 
		           {  
		        	   sbb.append(readLine).append("\n");  
		           }  
		          responseReader.close();  
		          String respJson = sbb.toString();
		          JSONObject jsObject = new JSONObject(respJson.toString());
		    	  if(jsObject.getBoolean("s"))
		    	  {
		    		  Log.d("UserVisitInfoActivity", "上传图片成功");
		    		  uploadResult = true;
		    	  } 
			}
		}catch(Exception e)
      	{
            Log.e("UserVisitInfoActivity", "上传图片失败 " +e.getMessage());
      	}
      return uploadResult;
	}
  
    @Override  
    protected void onDestroy() {  
        if (mapManager != null) {  
            mapManager.destroy();  
            mapManager = null;  
        }  
        mLocationManager = null;  
        super.onDestroy();  
    }  
  
    @Override  
    protected void onPause() {  
        if (mapManager != null) {  
            mapManager.stop();  
        }  
        super.onPause();  
    }  
  
    @Override  
    protected void onResume() {  
        if (mapManager != null) {  
            mapManager.start();  
        }  
        super.onResume();  
    }  
  
    /** 
     * 当位置发生变化时触发此方法 
     *  
     * @param location 当前位置 
     */  
    public void onLocationChanged(Location location) {  
        if (location != null) {  
            // 显示定位结果  
        	longitude = location.getLongitude();  
            latitude = location.getLatitude();  
        }  
    }  
}
