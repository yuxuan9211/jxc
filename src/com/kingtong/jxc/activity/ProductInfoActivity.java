package com.kingtong.jxc.activity;

import com.kingtong.jxc.R;
import com.kingtong.jxc.common.DeviceUtil;
import com.kingtong.jxc.common.MyApplication;
import com.kingtong.jxc.conn.MyHttpClient;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductInfoActivity extends Activity {
	private Context context = ProductInfoActivity.this;
	private ImageView backBtn = null;
	private TextView title = null;
	private TextView titleText = null;
	private TextView jgText = null;
	private LinearLayout picManager = null;
	private ImageView picImageView = null;
	
	private Bitmap picBitmap = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_info);
        MyApplication.getInstance().addActivity(this);
        backBtn   = (ImageView)findViewById(R.id.back);
        title     = (TextView) findViewById(R.id.chanpin_title);
        titleText = (TextView) findViewById(R.id.chanpin_title_text);
        jgText    = (TextView) findViewById(R.id.chanpin_jg_text);
        picManager = (LinearLayout) findViewById(R.id.pic_layout_manager);
//        picImageView = (ImageView) findViewById(R.id.chanpin_pic);
        
        registeEvent();
        // show content
        Intent intent = getIntent();
    	String titleString = intent.getStringExtra("title");
    	String jgString = intent.getStringExtra("jg");
    	String url = intent.getStringExtra("pic");
    	title.setText(titleString);
    	titleText.setText(titleString);
    	jgText.setText(jgString);
    	View loading = new MyCustomView(context);
    	loading.setPadding(20, 40, 20, 20);
    	picManager.addView(loading,new ViewGroup.LayoutParams(50, 50));
    	
    	//启动获取图片线程
    	startGetImage(url);
    }
    
    private void startGetImage(final String url) {
    	//开启一条线程 提供新数据
		new Thread(new Runnable() {
			public void run() {
				// 动态取得数据
				picBitmap = MyHttpClient.getBitmap(url);
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
    		if(null == picBitmap) {
    			picBitmap = DeviceUtil.getResIcon(context.getResources(),R.drawable.no);
    		}
    		picManager.removeViewAt(1);
			picImageView = new ImageView(context);
			picImageView.setImageBitmap(picBitmap);
			picImageView.setPadding(0, 0, 20, 10);
			picManager.addView(picImageView);
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
     * 播放gif
     * @author Liu Leilei
     *
     */
    class MyCustomView extends View {
    	private Movie mMovie;
    	private long mMovieStart;
        public MyCustomView(Context context) {
            super(context);
            // 以文件流的方式读取文件
            mMovie = Movie.decodeStream(getResources().openRawResource(
                    R.drawable.cycle));
        }
 
        @Override
        protected void onDraw(Canvas canvas) {
            long curTime = android.os.SystemClock.uptimeMillis();
            // 第一次播放
            if (mMovieStart == 0) {
                mMovieStart = curTime;
            }
 
            if (mMovie != null) {
                int duration = mMovie.duration();
 
                int relTime = (int) ((curTime - mMovieStart) % duration);
                mMovie.setTime(relTime);
                mMovie.draw(canvas, 0, 0);
 
                // 强制重绘
                invalidate();
            }
            super.onDraw(canvas);
        }
    }
}
