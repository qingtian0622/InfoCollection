package com.anfeng.infocollection.fragments;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.anfeng.infocollection.R;
import com.anfeng.infocollection.base.BaseFragment;
import com.anfeng.infocollection.model.DeviceInfoModel;
import com.anfeng.infocollection.util.DeviceUtils;
import com.google.gson.Gson;


/**
 * Created by Administrator on 2017/2/21.
 */

public class MineFragment extends BaseFragment
{
    TextView tv_brand, tv_model, tv_vc, tv_vn, tv_imei, tv_imsi, tv_num, tv_screen;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_mine;
    }

    @Override
    public void finishCreateView(View view)
    {

        tv_brand = (TextView) view.findViewById(R.id.brand);
        tv_imei = (TextView) view.findViewById(R.id.ime);

        tv_model = (TextView) view.findViewById(R.id.model);
        tv_vc = (TextView) view.findViewById(R.id.vc);
        tv_vn = (TextView) view.findViewById(R.id.vn);
        tv_imsi = (TextView) view.findViewById(R.id.imsi);
        tv_num = (TextView) view.findViewById(R.id.num);
        tv_screen = (TextView) view.findViewById(R.id.screen);
        Gson gson = new Gson();
        DeviceInfoModel model = gson.fromJson(DeviceUtils.getDeviceInfo(getActivity()), DeviceInfoModel.class);

//        tv_brand.setText("手机品牌：" + model.getPhoneBrand());
        tv_brand.setText(getDiffColorStr("手机品牌：", "手机品牌：" + model.getPhoneBrand()));
        tv_imei.setText("手机IMEI：" + model.getPhoneIMEI());

        tv_model.setText("手机型号：" + model.getPhoneModel());
        tv_vc.setText("版本号：" + model.getPhoneVersionCode());
        tv_vn.setText("版本名称：" + model.getPhoneVersionName());
        tv_imsi.setText("手机IMSI：" + model.getPhoneIMSI());
        tv_num.setText(TextUtils.isEmpty(model.getPhoneNumber()) ? "无SIM卡" : "本机号码：" + model.getPhoneNumber());
        tv_screen.setText("分辨率：" + model.getPhoneScreen());

    }


    private SpannableString getDiffColorStr(String colorStr, String totalStr)
    {
        SpannableString spanString = new SpannableString(totalStr);
        spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0000")), 0, colorStr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spanString;
    }
}
