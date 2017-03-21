package com.anfeng.infocollection;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anfeng.infocollection.base.BaseActivity;
import com.anfeng.infocollection.http.RequestManager;
import com.anfeng.infocollection.http.callback.StringCallBack;
import com.anfeng.infocollection.model.SearchModel;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/20.
 */

public class PersonActivity extends BaseActivity
{
    private static final String TAG = "PersonActivity";
    String userphone = "";
    String username = "";
    private TextView tv_title, tv_phone, tv_dept, tv_position, tv_time, tv_tips;

    private TextView tv_brand, tv_model, tv_vc, tv_vn, tv_imei, tv_imsi, tv_num, tv_screen;

    private ImageView img,back;

    private LinearLayout device_info;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        tv_title = (TextView) findViewById(R.id.title);
        img = (ImageView) findViewById(R.id.img);
        tv_phone = (TextView) findViewById(R.id.phone);
        tv_dept = (TextView) findViewById(R.id.dept);
        tv_position = (TextView) findViewById(R.id.position);
        tv_time = (TextView) findViewById(R.id.time);
        tv_tips = (TextView) findViewById(R.id.tips);

        tv_brand = (TextView) findViewById(R.id.brand);
        tv_imei = (TextView) findViewById(R.id.ime);

        tv_model = (TextView) findViewById(R.id.model);
        tv_vc = (TextView) findViewById(R.id.vc);
        tv_vn = (TextView) findViewById(R.id.vn);
        tv_imsi = (TextView) findViewById(R.id.imsi);
        tv_num = (TextView) findViewById(R.id.num);
        tv_screen = (TextView) findViewById(R.id.screen);

        device_info = (LinearLayout) findViewById(R.id.device_info);

        userphone = this.getIntent().getExtras().getString("userphone");
        username = this.getIntent().getExtras().getString("username");
        tv_title.setText(username);
        Toast.makeText(this, userphone, Toast.LENGTH_SHORT).show();
        HashMap<String, String> map = new HashMap<>();
        RequestManager.getInstance(PersonActivity.this).requestAsyn("api/user_show/" + userphone, RequestManager.TYPE_GET, map, new StringCallBack()
        {
            @Override
            public void onReqSuccess(String result)
            {
                super.onReqSuccess(result);
                Gson gson = new Gson();
                SearchModel model = gson.fromJson(result, SearchModel.class);
                if (model != null)
                {
                    if (("男").equals(model.getGender()))
                    {
                        img.setImageResource(R.mipmap.head_man);
                    }
                    else if (("女").equals(model.getGender()))
                    {
                        img.setImageResource(R.mipmap.head_women);
                    }
                    else
                    {
                        img.setImageResource(R.mipmap.default_head);
                    }


                    tv_phone.setText("手机  :  "+model.getMobile());
                    tv_position.setText("职位  :  "+model.getPosition());
                    tv_dept.setText("部门  :  "+model.getDept());
                    tv_time.setText("邮箱  :  "+model.getEmail());
                    int length = model.getLog().size();
                    if (length == 0)
                    {
                        device_info.setVisibility(View.GONE);
                        tv_tips.setText("设备信息:(你可以提醒" + (model.getGender().equals("男") ? "他" : "她") + "上传设备信息)");
                    }
                    else
                    {
                        device_info.setVisibility(View.VISIBLE);
                        tv_brand.setText("手机品牌：" + model.getLog().get(0).getPhoneBrand());
                        tv_imei.setText("手机IMEI：" + model.getLog().get(0).getPhoneIMEI());

                        tv_model.setText("手机型号：" + model.getLog().get(0).getPhoneModel());
                        tv_vc.setText("版本号：" + model.getLog().get(0).getPhoneVersionCode());
                        tv_vn.setText("版本名称：" + model.getLog().get(0).getPhoneVersionName());
                        tv_imsi.setText("手机IMSI：" + model.getLog().get(0).getPhoneIMSI());
                        tv_screen.setText("分辨率：" + model.getLog().get(0).getPhoneScreen());
                    }

                }else{
                    finish();
                }
            }

            @Override
            public void onReqFailed(String errorMsg)
            {
                super.onReqFailed(errorMsg);
                finish();
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
