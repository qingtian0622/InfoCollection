package com.anfeng.infocollection;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.anfeng.infocollection.adapter.ErrorAdapter;
import com.anfeng.infocollection.base.BaseActivity;
import com.anfeng.infocollection.http.RequestManager;
import com.anfeng.infocollection.http.callback.StringCallBack;
import com.anfeng.infocollection.model.SearchModel;
import com.anfeng.infocollection.util.SpUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ErrorLogActivity extends BaseActivity
{
    private static final String TAG = "ErrorLogActivity";

    private ListView listview;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_log);
        initView();
    }

    private void initView()
    {
        listview = (ListView) findViewById(R.id.listview);

        String userphone = (String) SpUtil.getParam(ErrorLogActivity.this, "userphone", "");
        HashMap<String, String> map = new HashMap<>();
        RequestManager.getInstance(ErrorLogActivity.this).requestAsyn("api/user_show/" + userphone, RequestManager.TYPE_GET, map, new StringCallBack()
        {
            @Override
            public void onReqSuccess(String result)
            {
                super.onReqSuccess(result);
                Gson gson = new Gson();
                SearchModel model = gson.fromJson(result, SearchModel.class);
                if (model != null && model.getLog().size() > 0)
                {
                    int length = model.getLog().size();
                    List<String> strlist = new ArrayList<String>();
                    for (int i = 0; i < length; i++)
                    {
                        if (!TextUtils.isEmpty(model.getLog().get(i).getError_msg()))
                        {
                            strlist.add(model.getLog().get(i).getError_msg().replace("\\", ""));
                        }
                    }
                    Log.e(TAG, "onReqSuccess: " + strlist.get(0));
                    ErrorAdapter adapter = new ErrorAdapter(ErrorLogActivity.this, strlist);
                    listview.setAdapter(adapter);
                }
            }

            @Override
            public void onReqFailed(String errorMsg)
            {
                super.onReqFailed(errorMsg);
            }
        });

        back = (ImageView) findViewById(R.id.back);
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
