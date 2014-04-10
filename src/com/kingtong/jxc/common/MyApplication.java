/****************************************************************************************
 * Copyright (c) 2010~2012 All Rights Reserved by
 *  G-Net Integrated Service Co.,  Ltd. 
 ****************************************************************************************/
/**
 * @file MyApplication.java
 * @author wenhui.li
 * @date 2013-5-22 下午1:55:01 
 * Revision History 
 *     - 2013-5-22: change content for what reason
 ****************************************************************************************/

package com.kingtong.jxc.common;

import java.util.LinkedList;
import java.util.List;
import com.kingtong.jxc.activity.WelcomeTableActivity;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

public class MyApplication extends Application 
{
    private List<Activity> activityList = new LinkedList<Activity>();
    private static MyApplication instance;
 
    private MyApplication() 
    {
    }
 
    // 单例模式中获取唯一的MyApplication实例
    public static MyApplication getInstance() 
    {
        if (null == instance) 
        {
            instance = new MyApplication();
        }
        return instance;
    }
 
    // 添加Activity到容器中
    public void addActivity(Activity activity) 
    {
        activityList.add(activity);
    }
 
    // 遍历所有Activity并finish
 
    public void exit(Context context) 
    {
        for (Activity activity : activityList) 
        {
            activity.finish();
        }
        Intent intent = new Intent(context, WelcomeTableActivity.class);
    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	intent.putExtra("exit", "true");
    	context.startActivity(intent);
    }
}

