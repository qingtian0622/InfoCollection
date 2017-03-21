package com.anfeng.infocollection;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.anfeng.infocollection.adapter.AppsAdapter;
import com.anfeng.infocollection.base.BaseActivity;
import com.anfeng.infocollection.model.AppsModel;
import com.anfeng.infocollection.util.DeviceUtils;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/3/20.
 */

public class AppsActivity extends BaseActivity
{
    private static final String TAG = "AppsActivity";
    private GridView gridview;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
        initView();
        Log.e(TAG, "onCreate: " + DeviceUtils.getInstallApps(this));
        Gson gson = new Gson();
        AppsModel model = gson.fromJson(DeviceUtils.getInstallApps(this), AppsModel.class);
        if (model != null)
        {
            AppsAdapter adapter = new AppsAdapter(this, model.getAppsInfo());
            gridview.setAdapter(adapter);
        }
    }

    private void initView()
    {
        gridview = (GridView) findViewById(R.id.gview);
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
