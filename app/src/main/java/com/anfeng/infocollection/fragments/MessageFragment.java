package com.anfeng.infocollection.fragments;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.anfeng.infocollection.R;
import com.anfeng.infocollection.adapter.UserAdapter;
import com.anfeng.infocollection.base.BaseFragment;
import com.anfeng.infocollection.http.RequestManager;
import com.anfeng.infocollection.http.callback.StringCallBack;
import com.anfeng.infocollection.model.UserModel;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/21.
 */

public class MessageFragment extends BaseFragment
{
    private static final String TAG = "MessageFragment";
    private ListView mlistview;
    private  UserAdapter adapter;

//    private List<>
    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_message;
    }

    @Override
    public void finishCreateView(View view)
    {
        mlistview = (ListView) view.findViewById(R.id.listview);
       // adapter=new UserAdapter(getActivity(),)
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_id", "1");
        RequestManager.getInstance(getActivity()).requestAsyn("api/user_all", RequestManager.TYPE_GET, map, new StringCallBack()
        {
            @Override
            public void onReqSuccess(String result)
            {
                super.onReqSuccess(result);
                Log.e(TAG, "onReqSuccess: "+result );
                Gson gson=new Gson();
                UserModel model=gson.fromJson(result,UserModel.class);
                UserAdapter adapter=new UserAdapter(getActivity(),model.getUser());
                mlistview.setAdapter(adapter);

            }

            @Override
            public void onReqFailed(String errorMsg)
            {
                super.onReqFailed(errorMsg);
                Log.e(TAG, "onReqFailed: "+errorMsg );
            }
        });
    }

}
