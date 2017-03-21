package com.anfeng.infocollection.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import com.anfeng.infocollection.base.AppContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangtao on 2017/3/17.
 */

public class DeviceUtils
{

    public static HashMap<String, String> getDeviceInfo2Map(Context context)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("PhoneBrand", Build.BRAND);
        map.put("PhoneModel", Build.VERSION.RELEASE);
        map.put("PhoneVersionCode", Build.VERSION.SDK_INT + "");
        map.put("PhoneIMEI", getIMEI(context));
        map.put("PhoneIMSI", getIMSI(context));
        map.put("PhoneNumber", getNum(context));
        map.put("PhoneScreen", getScreen((Activity) context));
        return map;
    }

    /**
     * 获取手机设备信息，返回json格式字符串
     *
     * @param context
     * @return
     */
    public static String getDeviceInfo(Context context)
    {
        String userphone = (String) SpUtil.getParam(context, "userphone", "");
        String str = "";
        if (!TextUtils.isEmpty(userphone))
        {
            str = userphone;
        }
        StringBuffer sb = new StringBuffer("{");
        sb.append("\"PhoneBrand\":\"" + Build.BRAND + "\",");
        sb.append("\"PhoneModel\":\"" + Build.MODEL + "\",");
        sb.append("\"PhoneVersionName\":\"" + Build.VERSION.RELEASE + "\",");
        sb.append("\"PhoneVersionCode\":\"" + Build.VERSION.SDK_INT + "\",");
        sb.append("\"PhoneIMEI\":\"" + getIMEI(context) + "\",");
        sb.append("\"PhoneIMSI\":\"" + getIMSI(context) + "\",");
        sb.append("\"PhoneNumber\":\"" + (TextUtils.isEmpty(getNum(context)) ? str : getNum(context)) + "\",");
        sb.append("\"PhoneScreen\":\"" + getScreen((Activity) context) + "\"");
        sb.append("}");
        return sb.toString();
    }

    /**
     * 所有信息的集合，包括手机设备信息，所有安装app信息，返回json字符串类型
     *
     * @param context
     * @return
     */
    public static String getAllInfo(Context context)
    {
        StringBuffer sb = new StringBuffer("{");
        sb.append("\"DeviceInfo\":" + getDeviceInfo(context) + ",");
        sb.append("\"AppsInfo\":" + getAppsInfo(context));
        sb.append("}");
        return sb.toString();
    }

    /**
     * 得到所有安装app的应用信息，返回json字符串类型
     *
     * @param context
     * @return
     */
    public static String getInstallApps(Context context)
    {
        StringBuffer sb = new StringBuffer("{");
        sb.append("\"AppsInfo\":" + getAppsInfo(context));
        sb.append("}");
        return sb.toString();
    }

    /**
     * 根据应用包名，拿应用logo图标
     *
     * @param context
     * @param packname
     * @return
     */
    public static Drawable getAppIcon(Context context, String packname)
    {
        try
        {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            return info.loadIcon(pm);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();

        }
        return null;
    }

    private static String getScreen(Activity context)
    {
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        return display.getWidth() + "*" + display.getHeight();
    }

    private static String getAppsInfo(Context context)
    {
        StringBuffer sb = new StringBuffer("[");
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo i : packages)
        {
            ApplicationInfo applicationInfo = i.applicationInfo;
            if (!isSystemApp(applicationInfo))
            //if (i.packageName.equals("com.tencent.mm") || i.packageName.equals("com.ht.sophixdemo"))
            {
                sb.append("{");
                sb.append("\"AppName\":\"" + i.applicationInfo.loadLabel(pm).toString() + "\",");
                sb.append("\"PackageName\":\"" + i.packageName + "\",");
                sb.append("\"InstallTime\":\"" + i.firstInstallTime + "\",");
                sb.append("\"UpdateTime\":\"" + i.lastUpdateTime + "\",");
                sb.append("\"VersionName\":\"" + i.versionName + "\",");
                sb.append("\"VersionCode\":\"" + i.versionCode + "\"},");
            }
        }
        return sb.toString().substring(0, sb.toString().length() - 1).concat("]");
    }

    private static boolean isSystemApp(ApplicationInfo appInfo)
    {
        /**
         * uid是应用程序安装时由系统分配(1000 ～ 9999为系统应用程序保留)
         */
        return (appInfo.flags & appInfo.FLAG_SYSTEM) > 0;
    }

    /**
     * 获取手机号码
     *
     * @param context
     * @return
     */
    private static String getNum(Context context)
    {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }

    /**
     * 获取手机IMEI号
     */
    private static String getIMEI(Context context)
    {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();

        return imei;
    }

    /**
     * 获取手机IMSI号
     */
    private static String getIMSI(Context context)
    {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();

        return imsi;
    }


}
