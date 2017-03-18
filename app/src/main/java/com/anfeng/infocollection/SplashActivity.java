package com.anfeng.infocollection;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.anfeng.infocollection.base.BaseActivity;
import com.anfeng.infocollection.util.DeviceUtils;

public class SplashActivity extends BaseActivity
{
    private static final String TAG = "SplashActivity";

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: " + DeviceUtils.getDeviceInfo(this));
        handler.sendEmptyMessageDelayed(0, 2000);
    }
}
