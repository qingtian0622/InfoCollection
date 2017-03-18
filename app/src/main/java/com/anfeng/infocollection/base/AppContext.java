package com.anfeng.infocollection.base;

import android.app.Application;

import com.anfeng.infocollection.util.CrashUtil;

/**
 * Created by Administrator on 2017/3/18.
 */

public class AppContext extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        CrashUtil.getInstance().init(this);
    }

    private static AppContext instance;
    public static AppContext getInstance(){
        return instance;
    }

    private String userphone="";

    public String getUserphone()
    {
        return userphone;
    }

    public void setUserphone(String userphone)
    {
        this.userphone = userphone;
    }
}
