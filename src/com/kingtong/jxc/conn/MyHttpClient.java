package com.kingtong.jxc.conn;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class MyHttpClient {
	
	private static void log(String message,Throwable t) {
		Log.w(MyHttpClient.class.getSimpleName(), message, t);
	}
	
	public static String post(String url, List<NameValuePair> data)
	{
		log("request url=" + url, null);
		String result = "#NULL";
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			httppost.setEntity(new UrlEncodedFormEntity(data,"UTF-8"));
			HttpResponse response;
			response = httpclient.execute(httppost);
			
			String reDataString = "";
			InputStream inputStream = response.getEntity().getContent();
			if (inputStream != null) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int len = 0;
				byte[] mydata = new byte[4096];
				while ((len = inputStream.read(mydata)) != -1) {
					baos.write(mydata, 0, len);
				}
				byte[] datas = baos.toByteArray();
				
				reDataString = new String(datas, "UTF-8");
			}
			
			int statu = response.getStatusLine().getStatusCode();
			if(statu == HttpStatus.SC_OK) {//< 请求成功
				result = reDataString;
			} else {
				result = "#[" + statu + "]" + reDataString;
			}
		} catch (ClientProtocolException e) {
			log("post", e);
		} catch (IOException e) {
			log("post", e);
		}
		return result;
	}
	
	public static Bitmap getBitmap(String url) {
		log("Get image:" + url, null);
		URL fileURL = null;
	    Bitmap bitmap = null;
        try 
        {
            fileURL = new URL(url);
        } catch (MalformedURLException err) 
        {
            err.printStackTrace();
        }
        try 
        {
            if(fileURL == null) return null;
            HttpURLConnection conn = (HttpURLConnection) fileURL.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            int length = (int) conn.getContentLength();
            if (length != -1) 
            {
                byte[] imgData = new byte[length];
                byte[] buffer = new byte[512];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(buffer)) > 0) 
                {
                    System.arraycopy(buffer, 0, imgData, destPos, readLen);
                    destPos += readLen;
                }
                bitmap = BitmapFactory.decodeByteArray(imgData, 0,
                        imgData.length);
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        return bitmap;
	}
	
}
