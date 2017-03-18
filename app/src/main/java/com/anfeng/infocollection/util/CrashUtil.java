package com.anfeng.infocollection.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.anfeng.infocollection.LoginActivity;
import com.anfeng.infocollection.MainActivity;
import com.anfeng.infocollection.http.RequestManager;
import com.anfeng.infocollection.http.callback.ReqCallBack;
import com.anfeng.infocollection.http.callback.StringCallBack;
import com.anfeng.infocollection.model.LoginModel;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/18.
 */

public class CrashUtil implements Thread.UncaughtExceptionHandler
{
    private static final String TAG = "CrashUtil";
    private static final boolean DEBUG = true;
    //文件路径
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "crash";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFEIX = ".trace";
    private static Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private static CrashUtil mCrashHandler = new CrashUtil();
    private Context mContext;

    private CrashUtil()
    {
    }

    public static CrashUtil getInstance()
    {
        return mCrashHandler;
    }

    public void init(Context context)
    {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable ex)
    {
        Log.e(TAG, "uncaughtException: 1111111" );
        if (!handleException(ex) && mDefaultCrashHandler != null)
        {
            Log.e(TAG, "uncaughtException: bbbb" );
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultCrashHandler.uncaughtException(thread, ex);
        }
        else
        {
            try
            {
                Thread.sleep(3000);
            }
            catch (InterruptedException e)
            {
                Log.e(TAG, "error : ", e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }


        //如果系统提供了默认异常处理就交给系统进行处理，否则自己进行处理。
        if (mDefaultCrashHandler != null)
        {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        }
        else
        {
            Process.killProcess(Process.myPid());
        }
    }

    private boolean handleException(final Throwable ex)
    {
        Log.e(TAG, "uncaughtException: " + ex.toString());

        uploadErrorMsg(ex);
        return true;
    }

    private void uploadErrorMsg(Throwable ex)
    {
//        HashMap<String, String> map = new HashMap<String, String>();
//        //map.put("error_msg", ex.toString());
//        //map.putAll(DeviceUtils.getDeviceInfo2Map(mContext));
//        RequestManager.getInstance(mContext).requestAsyn("api/save_log", RequestManager.TYPE_GET, map, new StringCallBack()
//        {
//            @Override
//            public void onReqSuccess(String result)
//            {
//                super.onReqSuccess(result);
//                Log.e(TAG, "onReqSuccess: " + result);
//            }
//
//            @Override
//            public void onReqFailed(String errorMsg)
//            {
//                super.onReqFailed(errorMsg);
//                Log.e(TAG, "onReqFailed: " + errorMsg);
//            }
//        });


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mobile", "17688530622");
        RequestManager.getInstance(mContext).requestAsyn("api/save_user", RequestManager.TYPE_GET, map, new StringCallBack()
        {
            @Override
            public void onReqSuccess(String result)
            {
                super.onReqSuccess(result);
                Log.e(TAG, "onReqSuccess: " + result);
//                Gson gson = new Gson();
//                LoginModel model = gson.fromJson(result, LoginModel.class);
//                if (model != null)
//                {
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
//                }
            }

            @Override
            public void onReqFailed(String errorMsg)
            {
                super.onReqFailed(errorMsg);
                Log.e(TAG, "onReqFailed: " + errorMsg);
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //将异常写入文件
    private void writeToSDcard(Throwable ex) throws IOException, PackageManager.NameNotFoundException
    {
        //如果没有SD卡，直接返回
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            return;
        }
        File filedir = new File(PATH);
        if (!filedir.exists())
        {
            filedir.mkdirs();
        }
        long currenttime = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currenttime));

        File exfile = new File(PATH + File.separator + FILE_NAME + time + FILE_NAME_SUFEIX);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(exfile)));
        Log.e("错误日志文件路径", "" + exfile.getAbsolutePath());
        pw.println(time);
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        //当前版本号
        pw.println("App Version:" + pi.versionName + "_" + pi.versionCode);
        //当前系统
        pw.println("OS version:" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
        //制造商
        pw.println("Vendor:" + Build.MANUFACTURER);
        //手机型号
        pw.println("Model:" + Build.MODEL);
        //CPU架构
        pw.println("CPU ABI:" + Build.CPU_ABI);

        ex.printStackTrace(pw);
        pw.close();

    }


}