package com.anfeng.infocollection;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.anfeng.infocollection.adapter.UserAdapter;
import com.anfeng.infocollection.base.BaseActivity;
import com.anfeng.infocollection.cache.ACache;
import com.anfeng.infocollection.http.RequestManager;
import com.anfeng.infocollection.http.callback.StringCallBack;
import com.anfeng.infocollection.model.UserModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class SearchActivity extends BaseActivity
{
    private static final String TAG = "SearchActivity";

    private ACache macache;

    private EditText et_input;
    private ListView listview;

    private ImageView back;
    private List<UserModel.UserBean> list = new ArrayList<>();
    private UserAdapter adapter;
    List<UserModel.UserBean> ustArr = new ArrayList<UserModel.UserBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
        adapter = new UserAdapter(SearchActivity.this, list);
        listview.setAdapter(adapter);
    }

    private void initView()
    {
        et_input = (EditText) findViewById(R.id.et_phone);
        listview = (ListView) findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                UserModel.UserBean model = (UserModel.UserBean) adapterView.getItemAtPosition(i);
                //Toast.makeText(getActivity(),model.getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchActivity.this, PersonActivity.class);
                intent.putExtra("userphone", model.getMobile());
                intent.putExtra("username", model.getName());
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
        et_input.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

                String str = charSequence.toString();
                if (!TextUtils.isEmpty(str))
                {
                    ustArr.clear();
                    for (int index = 0; index < list.size(); index++)
                    {
                        if (list.get(index).getMobile().contains(str))
                        {
                            ustArr.add(list.get(index));
                        }
                    }
                    //listview.setVisibility(View.VISIBLE);
                    adapter.setData(ustArr);
                }
                else
                {
                    //listview.setVisibility(View.GONE);
                    adapter.setData(list);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
    }

    private void initData()
    {
        macache = ACache.get(this);
        String result_acache = macache.getAsString("list");
        if (!TextUtils.isEmpty(result_acache))
        {
            Log.e(TAG, "finishCreateView: " + "缓存" + result_acache);
            Gson gson = new Gson();
            UserModel model = gson.fromJson(result_acache, UserModel.class);
            list = model.getUser();
        }
        else
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("user_id", "1");
            RequestManager.getInstance(this).requestAsyn("api/user_all", RequestManager.TYPE_GET, map, new StringCallBack()
            {
                @Override
                public void onReqSuccess(String result)
                {
                    super.onReqSuccess(result);
                    Log.e(TAG, "onReqSuccess: " + result);
                    Gson gson = new Gson();
                    UserModel model = gson.fromJson(result, UserModel.class);
                    list = model.getUser();
                    macache.put("list", result, ACache.TIME_DAY);
                }

                @Override
                public void onReqFailed(String errorMsg)
                {
                    super.onReqFailed(errorMsg);
                    Log.e(TAG, "onReqFailed: " + errorMsg);
                    finish();
                }
            });
        }
    }
}
