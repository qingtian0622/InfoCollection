package com.anfeng.infocollection;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.anfeng.infocollection.http.RequestManager;
import com.anfeng.infocollection.http.callback.StringCallBack;
import com.anfeng.infocollection.util.DeviceUtils;
import com.anfeng.infocollection.util.FragmentController;

import java.util.HashMap;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener
{
    private static final String TAG = "MainActivity";
    RadioGroup hometabRadio;
    private FragmentController controller;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_maintab);
        hometabRadio = (RadioGroup) findViewById(R.id.radioGroup);
        hometabRadio.setOnCheckedChangeListener(this);
        tv_title = (TextView) findViewById(R.id.title);
        controller = FragmentController.getInstance(this, R.id.content);
        controller.showFragment(0);
        uploadDeviceInfo();
    }

    private void uploadDeviceInfo()
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("log_data", DeviceUtils.getDeviceInfo(this));
        RequestManager.getInstance(this).requestAsyn("api/save_log", RequestManager.TYPE_GET, map, new StringCallBack()
        {
            @Override
            public void onReqSuccess(String result)
            {
                super.onReqSuccess(result);
                Log.e(TAG, "onReqSuccess: " + result);
            }

            @Override
            public void onReqFailed(String errorMsg)
            {
                super.onReqFailed(errorMsg);
                Log.e(TAG, "onReqFailed: " + errorMsg);
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        switch (checkedId)
        {

            case R.id.rb_menu_pic:
                tv_title.setText("消息");
                controller.showFragment(0);
                break;
            case R.id.rb_menu_video:
                tv_title.setText("联系人");
                controller.showFragment(1);
                break;
            case R.id.rb_menu_me:
                tv_title.setText("我的");
                controller.showFragment(2);
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        FragmentController.onDestoryController();
    }
}
