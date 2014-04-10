package com.kingtong.jxc.common;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import com.kingtong.jxc.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class DeviceUtil {
	
	  private static String TAG = DeviceUtil.class.getSimpleName();///<消息操作和管理类名称
	/**
	 * 浮层提示
	 */
	public static void alert(int messageid, Context context)
	{
		Toast.makeText(context, messageid, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 浮层提示
	 */
	public static void alert(String message, Context context)
	{
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * @brief 将下载的图片缓存到手机sd卡中
	 * @param bm 要保存的图片对象
	 * @param fileName 要保存的文件名称
	 */
	public static File saveFileToSDCard(Bitmap bm, String fileName)
	{
	    String  filePath = Environment.getExternalStorageDirectory().getPath() + "/"  + "jxc";
	    File dirFile = new File(filePath);  
        if(!dirFile.exists())
        {  
                dirFile.mkdir();  
        }  
        File myCaptureFile = new File(filePath + "/" + fileName);  
        BufferedOutputStream bos = null;
        try 
        {
             bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
             bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
             bos.flush();  
             bos.close(); 
        }catch (Exception e) 
        {
             e.printStackTrace();
        } 
        return myCaptureFile;
	}
	
	/**
	 * @brief 将下载的图片缓存到手机内存中
	 * @param 上下文
	 * @param bm 要保存的图片对象
	 * @param fileName 要保存的文件名称
	 */
	public static File saveFileToDevice(Context context, Bitmap bm, String fileName)
	{ 
		String filePath = "/data/data/" + context.getPackageName()+ "/files";
		InputStream input = bitmap2InputStream(bm);  
	    FileOutputStream outStream = null; 
		try {
			outStream = context.openFileOutput(fileName,
					Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
			int temp = 0;
			byte[] data = new byte[1024];
			while ((temp = input.read(data)) != -1) {
				outStream.write(data, 0, temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.e(TAG,"Image file not found, error message: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "Save image file to device failure, error message: "
					+ e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Save image file to device failure, error message: "+ e.getMessage());
		}
		finally {
			try {
				outStream.flush();
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(TAG,"Close the outputstream filure while save the image file to device, error message: "+ e.getMessage());
			}
		}
		 return new File(filePath);
	}
	
	/**
	 * @brief 删除图片文件
	 * @param file 要删除的图片文件
	 * @return 删除成功返回true 否则返回false
	 */
	public static boolean delImageFile(File file) 
    {
	  	try
	  	{
		  	if (file != null && file.exists()) 
		  	{
		  		file.delete();
		  	}
	  	}catch(Exception e)
	  	{
	  		e.printStackTrace();
	  		Log.e(TAG, "Delete ics file exception = " + e.getMessage());
	  	}
	  	return true;
    }
	
	/**
	 * @brief 将Bitmap转换成InputStream
	 * @param bm Bitmap格式的图片
	 * @return 返回图片流
	 */
	 private static InputStream bitmap2InputStream(Bitmap bm) 
	 {
	     ByteArrayOutputStream baos = new ByteArrayOutputStream();
	     bm.compress(Bitmap.CompressFormat.PNG, 80, baos);
	     InputStream is = new ByteArrayInputStream(baos.toByteArray());
	     return is;
	 }
	 
	 /**
		 * @brief 检测数据网络是否可用
		 * @param context 上下文文本对象
		 * @req MeetMeMobile1.0-ANDROIDSRS-003
		 * @req MeetMeMobile1.0-ANDROIDSRS-008
		 * @return   true表示网络可用，false表示网络不可用
		 */
		public static boolean isNetworkAvailable(Context context)
		{
			try {
				ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo info = cm.getActiveNetworkInfo();
				if (info != null && info.isConnected()){
					return true;
				}else{
					return false;
				}
			}
			catch (Exception e) {
				Log.e("NetworkUtil", "取得网络连接信息失败");
				return false;
			}
		}
		
		/**
		 * @brief 弹出引导用户设置网络对话框
		 * @param context 上下文Context对象
		 */
		public static void showNetSetCancelDialog(Context context){
			final Context mContext=context;
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setTitle(mContext.getResources().getString(R.string.netsettitle))
			.setMessage(mContext.getResources().getString(R.string.netsetmessage))
			.setNegativeButton(mContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//当点击不同意时，退出客户端
					dialog.cancel();
				}
			})
			.setPositiveButton(mContext.getResources().getString(R.string.setting_conference_set), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//当点击设置时，跳转至网络设置页面
					dialog.cancel();
					if(android.os.Build.VERSION.SDK_INT > 10 ){
						mContext.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
					}else {
						mContext.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
					}
				}
			}).show();
		}
	
		public static boolean isNumeric(String str){ 
		    Pattern pattern = Pattern.compile("[0-9]*"); 
		    return pattern.matcher(str).matches();    
		 } 
		
		 /**
	     * @brief 根据图片Id获取Bitmap根式的图片文件
	     * @param res 资料
	     * @param resId 图片Id
	     * @return Bitmap 格式的图片
	     */
	    public static Bitmap getResIcon(Resources res,int resId)
	    {  
	        Drawable icon=res.getDrawable(resId);  
	        if(icon instanceof BitmapDrawable)
	        {  
	            BitmapDrawable bd=(BitmapDrawable)icon;  
	            return bd.getBitmap();  
	        }else
	        {  
	            return null;  
	        }  
	    }
}
