package com.anfeng.infocollection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anfeng.infocollection.base.AppContext;
import com.anfeng.infocollection.base.BaseActivity;
import com.anfeng.infocollection.util.SpUtil;

/**
 * Created by Administrator on 2017/3/20.
 */

public class AboutActivity extends BaseActivity
{
    private TextView tv_logout;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_us);
        tv_logout = (TextView) findViewById(R.id.logout);
        tv_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SpUtil.setParam(AboutActivity.this, "userphone", "");
                AppContext.finishAllActivity();
                Intent intent = new Intent(AboutActivity.this, LoginActivity.class);
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
