package com.anfeng.infocollection.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.anfeng.infocollection.LoginActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/9/27
 *     desc  : 崩溃相关工具类
 * </pre>
 */
public class CrashUtil implements Thread.UncaughtExceptionHandler
{

    private static final String TAG = "CrashUtil";

    private volatile static CrashUtil mInstance;

    private UncaughtExceptionHandler mHandler;

    private boolean mInitialized;
    private String crashDir;
    private String versionName;
    private int versionCode;

    private CrashUtil()
    {
    }

    /**
     * 获取单例
     * <p>在Application中初始化{@code CrashUtils.getInstance().init(this);}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}</p>
     *
     * @return 单例
     */
    public static CrashUtil getInstance()
    {
        if (mInstance == null)
        {
            synchronized (CrashUtil.class)
            {
                if (mInstance == null)
                {
                    mInstance = new CrashUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     *
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public boolean init()
    {
        if (mInitialized)
            return true;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        {
            File baseCache = Utils.getContext().getExternalCacheDir();
            if (baseCache == null)
                return false;
            crashDir = baseCache.getPath() + File.separator + "crash" + File.separator;
        }
        else
        {
            File baseCache = Utils.getContext().getCacheDir();
            if (baseCache == null)
                return false;
            crashDir = baseCache.getPath() + File.separator + "crash" + File.separator;
        }
        try
        {
            PackageInfo pi = Utils.getContext().getPackageManager().getPackageInfo(Utils.getContext().getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        mHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        return mInitialized = true;
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable throwable)
    {
        SpUtil.setParam(Utils.getContext(), "error_log", "");
        Throwable ex = throwable.getCause() == null ? throwable : throwable.getCause();
        StackTraceElement[] stacks = ex.getStackTrace();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        StringBuffer sb = new StringBuffer("{");
        sb.append("\"deviceinfo\":" + getCrashHead() + ",");
        sb.append("\"error_summer\":\"" + ex.toString() + "\",");
        sb.append("\"error_msg\":" + getMessage(stacks[0]) + ",");
        sb.append("\"error_time\":\"" + now + "\"}");


        Log.e(TAG, "uncaughtException: " + sb.toString());

        SpUtil.setParam(Utils.getContext(), "error_log", sb.toString());

        if (mHandler != null)
        {
            mHandler.uncaughtException(thread, throwable);
        }
    }

    private String getMessage(StackTraceElement ste)
    {
        StringBuffer sb = new StringBuffer("{");
        sb.append("\"error_path\":\"" + ste.getClassName() + "\",");
        sb.append("\"error_classname\":\"" + ste.getFileName() + "\",");
        sb.append("\"error_method\":\"" + ste.getMethodName() + "\",");
        sb.append("\"error_location\":\"" + "第" + ste.getLineNumber() + "行\"}");
        Log.e(TAG, "getMessage: " + ste.toString());
        return sb.toString();
    }

    /**
     * 获取崩溃头
     *
     * @return 崩溃头
     */
    private String getCrashHead()
    {
        StringBuffer sb = new StringBuffer("{");
        sb.append("\"phone_brand\":\"" + Build.BRAND + "\",");
        sb.append("\"phone_model\":\"" + Build.MODEL + "\",");
        sb.append("\"system_version\":\"" + Build.VERSION.RELEASE + "\",");
        sb.append("\"sdk_version\":\"" + Build.VERSION.SDK_INT + "\",");
        sb.append("\"versionName\":\"" + versionName + "\",");
        sb.append("\"versionCode\":\"" + versionCode + "\"}");

        return sb.toString();
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    private static boolean createOrExistsFile(String filePath)
    {
        File file = new File(filePath);
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists())
            return file.isFile();
        if (!createOrExistsDir(file.getParentFile()))
            return false;
        try
        {
            return file.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    private static boolean createOrExistsDir(File file)
    {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }
}
