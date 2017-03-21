package com.anfeng.infocollection.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anfeng.infocollection.AboutActivity;
import com.anfeng.infocollection.DeviceInfoActivity;
import com.anfeng.infocollection.ErrorLogActivity;
import com.anfeng.infocollection.R;
import com.anfeng.infocollection.base.BaseFragment;
import com.anfeng.infocollection.model.DeviceInfoModel;
import com.anfeng.infocollection.util.DeviceUtils;
import com.google.gson.Gson;


/**
 * Created by Administrator on 2017/2/21.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener
{
    private Button bug1, bug2, bug3, bug4;

    private TextView tv_mydevice,tv_error_log,tv_about_us;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_mine1;
    }

    @Override
    public void finishCreateView(View view)
    {
        bug1 = (Button) view.findViewById(R.id.bug1);
        bug2 = (Button) view.findViewById(R.id.bug2);
        bug3 = (Button) view.findViewById(R.id.bug3);
        bug4 = (Button) view.findViewById(R.id.bug4);

        bug1.setOnClickListener(this);
        bug2.setOnClickListener(this);
        bug3.setOnClickListener(this);
        bug4.setOnClickListener(this);

        tv_mydevice= (TextView) view.findViewById(R.id.my_device);
        tv_error_log= (TextView) view.findViewById(R.id.my_error_log);
        tv_about_us= (TextView) view.findViewById(R.id.about_us);
        tv_mydevice.setOnClickListener(this);
        tv_error_log.setOnClickListener(this);
        tv_about_us.setOnClickListener(this);



    }


    private SpannableString getDiffColorStr(String colorStr, String totalStr)
    {
        SpannableString spanString = new SpannableString(totalStr);
        spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0000")), 0, colorStr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spanString;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.bug1:
                throw new NullPointerException("空指针");
            case R.id.bug2:
                throw new ArrayIndexOutOfBoundsException("下标异常");
            case R.id.bug3:
                throw new ArithmeticException("数学错误");
            case R.id.bug4:
                throw new NumberFormatException("类型不符");
            case R.id.my_device:
                Intent device_intent=new Intent(getActivity(),DeviceInfoActivity.class);
                startActivity(device_intent);
                break;
            case R.id.my_error_log:
                Intent error_intent=new Intent(getActivity(),ErrorLogActivity.class);
                startActivity(error_intent);
                break;
            case R.id.about_us:
                Intent about_intent=new Intent(getActivity(),AboutActivity.class);
                startActivity(about_intent);
                break;
        }
    }
}
