package com.anfeng.infocollection;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anfeng.infocollection.base.AppContext;
import com.anfeng.infocollection.base.BaseActivity;
import com.anfeng.infocollection.http.RequestManager;
import com.anfeng.infocollection.http.callback.StringCallBack;
import com.anfeng.infocollection.model.LoginModel;
import com.anfeng.infocollection.util.DeviceUtils;
import com.anfeng.infocollection.util.SpUtil;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/18.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener
{
    private static final String TAG = "LoginActivity";
    private EditText et_username, et_code;

    private TextView tv_code, tv_login;

    private String strcode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();


    }

    private void initView()
    {
        et_username = (EditText) findViewById(R.id.sign_phone_tv);
        et_code = (EditText) findViewById(R.id.sign_code_tv);

        tv_code = (TextView) findViewById(R.id.sign_coed);
        tv_login = (TextView) findViewById(R.id.sign_bt);
        tv_code.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    private String getRandNum(int num)
    {
        String codeStr = "";
        for (int i = 0; i < num; i++)
        {
            codeStr += (int) (Math.random() * 10);
        }
        return codeStr;
    }

    public static boolean isMobileNO(String mobiles)
    {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    @Override
    public void onClick(View view)
    {
        final String phone = et_username.getText().toString();
        String code = et_code.getText().toString();
        switch (view.getId())
        {
            case R.id.sign_coed:
                if (TextUtils.isEmpty(phone))
                {
                    Toast.makeText(LoginActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isMobileNO(phone))
                {
                    Toast.makeText(LoginActivity.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone != null && isMobileNO(phone))
                {
                    strcode = getRandNum(4);
                    tv_code.setText(strcode);
                }
                break;
            case R.id.sign_bt:
                if (TextUtils.isEmpty(phone))
                {
                    Toast.makeText(LoginActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isMobileNO(phone))
                {
                    Toast.makeText(LoginActivity.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(code))
                {
                    Toast.makeText(LoginActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!strcode.equals(code))
                {
                    Toast.makeText(LoginActivity.this, "验证码不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code))
                {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("mobile", phone);
                    RequestManager.getInstance(this).requestAsyn("api/save_user", RequestManager.TYPE_GET, map, new StringCallBack()
                    {
                        @Override
                        public void onReqSuccess(String result)
                        {
                            super.onReqSuccess(result);
                            Log.e(TAG, "onReqSuccess: " + result);
                            Gson gson = new Gson();
                            LoginModel model = gson.fromJson(result, LoginModel.class);
                            if (model != null)
                            {
                                SpUtil.setParam(LoginActivity.this, "userphone", model.getMobile());
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onReqFailed(String errorMsg)
                        {
                            super.onReqFailed(errorMsg);
                            Log.e(TAG, "onReqFailed: " + errorMsg);
                            Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }
}
