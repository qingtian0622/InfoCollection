package com.anfeng.infocollection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anfeng.infocollection.base.AppContext;
import com.anfeng.infocollection.http.RequestManager;
import com.anfeng.infocollection.http.callback.StringCallBack;
import com.anfeng.infocollection.util.DeviceUtils;
import com.anfeng.infocollection.util.FragmentController;
import com.anfeng.infocollection.util.SpUtil;

import java.util.HashMap;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener
{
    private static final String TAG = "MainActivity";
    RadioGroup hometabRadio;
    private FragmentController controller;
    private TextView tv_title;

    private int index = -1;
    private ImageView iv_search;
    private String userphone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_maintab);
        AppContext.addActivity(this);
        userphone = (String) SpUtil.getParam(MainActivity.this, "userphone", "17688530622");
        hometabRadio = (RadioGroup) findViewById(R.id.radioGroup);
        hometabRadio.setOnCheckedChangeListener(this);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.title);
        controller = FragmentController.getInstance(this, R.id.content);
        controller.showFragment(0);
        uploadErrorInfo();
        uploadDeviceInfo();
    }

    private void uploadErrorInfo()
    {
        String str = (String) SpUtil.getParam(this, "error_log", "");
        if (!TextUtils.isEmpty(str))
        {
            //TODO 将错误日志上传到服务器，上传成功之后，把error_log清空
            //SpUtil.setParam(this,"error_log",null);
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();


            HashMap<String, String> map = new HashMap<String, String>();
            map.put("error_msg", str);
            map.put("mobile", userphone);
            RequestManager.getInstance(this).requestAsyn("api/save_error", RequestManager.TYPE_GET, map, new StringCallBack()
            {
                @Override
                public void onReqSuccess(String result)
                {
                    super.onReqSuccess(result);
                    Log.e(TAG, "onReqSuccess: " + result);
                    SpUtil.setParam(MainActivity.this, "error_log", "");
                }

                @Override
                public void onReqFailed(String errorMsg)
                {
                    super.onReqFailed(errorMsg);
                }
            });
        }
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
                if (result.contains("ok"))
                {
                    SpUtil.setParam(MainActivity.this, "first", true);
                }
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
                index = 1;
                iv_search.setVisibility(View.VISIBLE);
                tv_title.setText("通讯录");
                controller.showFragment(0);
                break;
            case R.id.rb_menu_video:
                index = 2;
                iv_search.setVisibility(View.VISIBLE);
                tv_title.setText("日志");
                controller.showFragment(1);
                break;
            case R.id.rb_menu_me:
                index = -1;
                iv_search.setVisibility(View.GONE);
                tv_title.setText("我的");
                controller.showFragment(2);
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.e("controller", "onDestroy: ccccccc");
        //controller.onDestoryController();
        FragmentController.onDestroy();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("index", index);
                intent.putExtra("userphone", userphone);
                startActivity(intent);
                break;
        }
    }
}
