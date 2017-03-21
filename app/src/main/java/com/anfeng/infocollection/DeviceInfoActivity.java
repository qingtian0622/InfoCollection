package com.anfeng.infocollection;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anfeng.infocollection.base.BaseActivity;
import com.anfeng.infocollection.model.DeviceInfoModel;
import com.anfeng.infocollection.util.DeviceUtils;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/3/20.
 */

public class DeviceInfoActivity extends BaseActivity
{
    private TextView tv_brand, tv_model, tv_vc, tv_vn, tv_imei, tv_imsi, tv_num, tv_screen, tv_apps;

    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        initView();
    }

    private void initView()
    {
        tv_brand = (TextView) findViewById(R.id.brand);
        tv_imei = (TextView) findViewById(R.id.ime);

        tv_model = (TextView) findViewById(R.id.model);
        tv_vc = (TextView) findViewById(R.id.vc);
        tv_vn = (TextView) findViewById(R.id.vn);
        tv_imsi = (TextView) findViewById(R.id.imsi);
        tv_num = (TextView) findViewById(R.id.num);
        tv_screen = (TextView) findViewById(R.id.screen);
        tv_apps = (TextView) findViewById(R.id.apps);
        Gson gson = new Gson();
        DeviceInfoModel model = gson.fromJson(DeviceUtils.getDeviceInfo(DeviceInfoActivity.this), DeviceInfoModel.class);
        tv_brand.setText("手机品牌：" + model.getPhoneBrand());
        tv_imei.setText("手机IMEI：" + model.getPhoneIMEI());

        tv_model.setText("手机型号：" + model.getPhoneModel());
        tv_vc.setText("版本号：" + model.getPhoneVersionCode());
        tv_vn.setText("版本名称：" + model.getPhoneVersionName());
        tv_imsi.setText("手机IMSI：" + model.getPhoneIMSI());
        tv_num.setText("本机号码：" + model.getPhoneNumber());
        tv_screen.setText("分辨率：" + model.getPhoneScreen());
        tv_apps.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(DeviceInfoActivity.this,AppsActivity.class);
                startActivity(intent);
            }
        });
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}
