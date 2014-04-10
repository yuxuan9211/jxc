package com.kingtong.jxc.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;
import com.kingtong.jxc.domain.TheDate;
import com.kingtong.jxc.domain.TheTime;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimeUtil {
	
	/**
	 * 计算时间差值
	 * 此功能为别出心裁功能，若数据不合法，则不提示用户，直接跳过。
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getHourSpacing(final TextView start, final TextView end, int haveLunch)
	{
		
		try {
			/** 校验数据合法性 */
			TheTime timeS = TimeUtil.parseTime(start.getText().toString().trim());
			TheTime timeE = TimeUtil.parseTime(end.getText().toString().trim());
			
			if(timeE.getHour() - timeS.getHour() < 0) {
				return -1;
			}
			
			return (timeE.getHour() - timeS.getHour() - haveLunch);
			
		} catch (DataFormatException e) {
			// Ignore
			return -1;
		}
		
	}
	
	/**
	 * 获取格式为hh:mm的sql时间 
	 * @param formatTime
	 * @return
	 */
	public static java.sql.Time getSQLTime(String formatTime)
	{
		SimpleDateFormat  bartDateFormat =   new SimpleDateFormat("hh:mm");
		try{          
			java.util.Date  date  =  bartDateFormat.parse(formatTime);
			return new java.sql.Time(date.getTime());
		}   catch (Exception ex) {        
			System.out.println("Exception:" + ex.getMessage());   
		}
		return null;
	}
	
	/**
	 * 获取格式为yyyy-MM-dd的sql日期
	 * @param formatDate
	 * @return
	 */
	public static java.sql.Date getSQLDate(String formatDate)
	{

		SimpleDateFormat  bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try{          
			java.util.Date  date  =  bartDateFormat.parse(formatDate);
			return new java.sql.Date(date.getTime());
		}   catch (Exception ex) {        
			System.out.println("Exception:" + ex.getMessage());   
		}
		return null;
	}
	
	/**
	 * 选择日期框
	 * @param text
	 */
	public static void showDateDialog(final TextView text, final Context context)
	{
    	
    	try {
    		String str = text.getText().toString();
    		if(str.contains("("))
    		{
    			str = str.substring(0,str.indexOf("("));
    		}
			TheDate theDate = TimeUtil.parseDate(str);
			log("Parse " + str + " result:" + theDate.getYear() + ";" + theDate.getMonth() + ";" + theDate.getDay());
	    	DatePickerDialog date = new DatePickerDialog(context, new OnDateSetListener() {
	    		boolean fired = false;
	    		
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) 
				{
					if(fired) {
						log("Fix Android4.1 bug[34860]");
					} else {
						log("Time:" + year + ";" + monthOfYear + ";" + dayOfMonth);
						try {
							String time = TimeUtil.formatDate(year, monthOfYear+1, dayOfMonth);
							text.setText(Html.fromHtml("<u>" + time + "</u>"));
						} catch (DataFormatException e) {
							log("DataFormatException e=" + e);
						}
						
						fired = true;
					}
					
				}
			}, theDate.getYear(), theDate.getMonth()-1, theDate.getDay());
	    	date.show();
		} catch (DataFormatException e1) {
			log("Parse date error:" + e1);
		}
    	
	}
	
	/**
	 * 显示选择时间框
	 * @param text
	 */
	public static void showTimeDialog(final TextView text, final Context context)
	{
    	try {
	    	String str = text.getText().toString();
	    	TheTime theTime = TimeUtil.parseTime(str);
	    	log("Parse " + str + " result:" + theTime.getHour() + ";" + theTime.getMinute());
	    	TimePickerDialog time = new TimePickerDialog(context, new OnTimeSetListener() {
	    		boolean fired = false;
				
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					try {
						if(fired) {
							log("Fix Android4.1 bug[34860]");
						} else {
							text.setText(TimeUtil.formatTime(hourOfDay, minute));
							fired = true;
						}
					} catch (DataFormatException e) {
						log("DataFormatException e=" + e);
					}
				}
			}, theTime.getHour(), theTime.getMinute(), true);
	    	time.show();
    	} catch (DataFormatException e) {
    		log("Parse time error:" + e);
    	}
	}
	
	/**
	 * 显示选择时间框
	 * 
	 * 用户选择完后计算时间间隔。
	 * @param text
	 */
	public static void showTimeDialog(final TextView text, final Handler change, final Context context)
	{
    	try {
	    	String str = text.getText().toString();
	    	TheTime theTime = TimeUtil.parseTime(str);
	    	log("Parse " + str + " result:" + theTime.getHour() + ";" + theTime.getMinute());
	    	TimePickerDialog time = new TimePickerDialog(context, new OnTimeSetListener() {
	    		boolean fired = false;
				
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					try {
						if(fired) {
							log("Fix Android4.1 bug[34860]");
						} else {
							text.setText(TimeUtil.formatTime(hourOfDay, minute));
							change.sendEmptyMessage(0);
							fired = true;
						}
					} catch (DataFormatException e) {
						log("DataFormatException e=" + e);
					}
				}
			}, theTime.getHour(), theTime.getMinute(), true);
	    	time.show();
    	} catch (DataFormatException e) {
    		log("Parse time error:" + e);
    	}
	}
	
	/**
	 * 格式化时间（hh:mm）
	 * @param hourOfDay
	 * @param minute
	 * @return
	 * @throws DataFormatException
	 */
	public static String formatTime(int hourOfDay, int minute) throws DataFormatException
	{
		String h;
		String m;
		
		///处理异常
		if(hourOfDay < 0 || hourOfDay > 24) {
			throw new DataFormatException("Hour error");
		}
		if(minute < 0 || minute > 60) {
			throw new DataFormatException("Minute error");
		}
		
		if(hourOfDay < 10) {
			h = "0" + hourOfDay;
		} else {
			h = "" + hourOfDay;
		}
		
		if(minute < 10) {
			m = "0" + minute;
		} else {
			m = "" + minute;
		}
		
		return h + ":" + m;
		
	}
	
	/**
	 * 解析固定格式时间(hh:mm)
	 * @param formatTime
	 * @return
	 * @throws DataFormatException 
	 */
	public static TheTime parseTime(String formatTime) throws DataFormatException
	{
		TheTime time = new TheTime();
		if(formatTime.length() != 5) {
			throw new DataFormatException("Not hh:mm[" + formatTime.length() + "]");
		}
		time.setHour(Integer.parseInt(formatTime.substring(0, 2)));
		time.setMinute(Integer.parseInt(formatTime.substring(3, 5)));
		return time;
	}
	
	/**
	 * 获取格式化日期（yyyy-MM-dd）
	 * 
	 * 根据所给的毫秒数。
	 * @param ms
	 * @return
	 */
	public static String getFormatDate(long ms)
	{
		Date date = new Date(ms);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String out = format.format(date);
		return out;
	}
	
	/**
	 * 解析date串(yyyy-MM-dd)
	 * @param formatDate
	 * @return
	 * @throws DataFormatException 
	 */
	public static TheDate parseDate(String formatDate) throws DataFormatException
	{
		TheDate theDate = new TheDate();
		if(formatDate.length() != 10) {
			throw new DataFormatException("Not yyyy-MM-dd[" + formatDate.length() + "]");
		}
		theDate.setYear(Integer.parseInt(formatDate.substring(0, 4)));
		theDate.setMonth(Integer.parseInt(formatDate.substring(5, 7)));
		theDate.setDay(Integer.parseInt(formatDate.substring(8, 10)));
		return theDate;
	}
	
	/**
	 * 格式化日期为（yyyy-MM-dd）
	 * 
	 * 根据分别给定的年月日。
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @return
	 * @throws DataFormatException 
	 */
	public static String formatDate(int year, int monthOfYear, int dayOfMonth) throws DataFormatException
	{
		String y;
		String m;
		String d;
		
		///处理异常
		if(year < 0) {
			throw new DataFormatException("Year error");
		}
		if(monthOfYear < 0 || monthOfYear > 12) {
			throw new DataFormatException("Month error");
		}
		if(dayOfMonth < 0 || dayOfMonth > 31) {
			throw new DataFormatException("Day error");
		}
		
		y = Integer.toString(year);
		
		if(monthOfYear < 10) {
			m = "0" + monthOfYear;
		} else {
			m = "" + monthOfYear;
		}
		
		if(dayOfMonth < 10) {
			d = "0" + dayOfMonth;
		} else {
			d = "" + dayOfMonth;
		}
		
		return y + "-" + m + "-" + d;
	}
	
	/**
	 * 页面第一个选择日期框
	 * 
	 * 选择完成后需要设置其余日期不小于第一个日期。
	 * @param text
	 */
	public static void showDateDialog(final List<TextView> texts, final Context context)
	{
    	
    	try {
    		String str = (String) texts.get(0).getText();
			TheDate theDate = TimeUtil.parseDate(str);
			log("Parse " + str + " result:" + theDate.getYear() + ";" + theDate.getMonth() + ";" + theDate.getDay());
	    	DatePickerDialog date = new DatePickerDialog(context, new OnDateSetListener() {
	    		boolean fired = false;
				
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) 
				{
					if(fired) {
						log("Fix Android4.1 bug[34860]");
					} else {
						log("Time:" + year + ";" + monthOfYear + ";" + dayOfMonth);
						try {
							//texts.get(0).setText(TimeUtil.formateDate(year, monthOfYear+1, dayOfMonth));
							
							for(TextView tv : texts) {
								tv.setText(TimeUtil.formatDate(year, monthOfYear+1, dayOfMonth));
							}
							
						} catch (DataFormatException e) {
							log("DataFormatException e=" + e);
						}
						
						fired = true;
					}
					
				}
			}, theDate.getYear(), theDate.getMonth()-1, theDate.getDay());
	    	date.show();
		} catch (DataFormatException e1) {
			log("Parse date error:" + e1);
		}
    	
	}
	
	public static long paserDateToString(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date0 = format.parse(date);
			return date0.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 将毫秒转化成时间字符串
	 * @brief 将毫秒转化成时间字符串
	 * @param timemillis    毫秒值
	 * @return              时间字符串
	 */
	public static String pareTime2LongString(long timemillis){
		String result=null;
		//输出格式转换对象
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date=new Date(timemillis);
		result = outputFormat.format(date);
		return result;
	}

	private static void log(String string) {
		Log.d(TimeUtil.class.getSimpleName(), string);
	}
	
	/**
	 * @brief 将毫秒转化成日期字符串
	 * @param timemillis
	 * @return
	 */
	public static String pareTime2ShortString(long timemillis){
		String result=null;
		//输出格式转换对象
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy年MM月dd日");
		Date date=new Date(timemillis);
		result = outputFormat.format(date);
		return result;
	}
	
}
