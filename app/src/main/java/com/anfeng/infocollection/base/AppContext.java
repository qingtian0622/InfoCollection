package com.anfeng.infocollection.base;

import android.app.Activity;
import android.app.Application;

import com.anfeng.infocollection.util.CrashUtil;
import com.anfeng.infocollection.util.Utils;

import java.util.Stack;

/**
 * Created by Administrator on 2017/3/18.
 */

public class AppContext extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Utils.init(this);
        CrashUtil.getInstance().init();
    }

    private static AppContext instance;

    public static AppContext getInstance()
    {
        return instance;
    }

    public static Stack<Activity> activityStack;

    public static void addActivity(Activity activity)
    {
        if (activityStack == null)
        {
            activityStack = new Stack<Activity>();
        }
        if (!HasInStack(activity.getClass()))
        {
            activityStack.add(activity);
        }

    }

    public static boolean HasInStack(Class activity)
    {
        boolean result = false;
        int size = activityStack.size();
        for (int i = 0; i < size; i++)
        {
            if (activityStack.get(i).getClass().getName().toLowerCase().equals(activity.getName().toLowerCase()))
            {
                result = true;
                break;
            }
        }
        return result;
    }

    public static void finishAllActivity()
    {
        if (activityStack != null)
        {
            int size = activityStack.size();
            if (size > 0)
            {
                for (int i = 0; i < size; i++)
                {
                    if (null != activityStack.get(i))
                    {
                        //if (!activityStack.get(i).getComponentName().toString().equals("ComponentInfo{com.childrenwith.tom/com.childrenwith.tom.MainActivity}"))
                        activityStack.get(i).finish();
                    }
                }
                activityStack.clear();
            }
        }
    }

}
