package com.anfeng.infocollection.fragments;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.anfeng.infocollection.ErrorLogActivity;
import com.anfeng.infocollection.R;
import com.anfeng.infocollection.adapter.ErrorAdapter;
import com.anfeng.infocollection.base.BaseFragment;
import com.anfeng.infocollection.http.RequestManager;
import com.anfeng.infocollection.http.callback.StringCallBack;
import com.anfeng.infocollection.model.SearchModel;
import com.anfeng.infocollection.util.SpUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2017/2/21.
 */

public class ContactsFragment extends BaseFragment
{
    private ListView listview;

    private static final String TAG = "ContactsFragment";
    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_contacts;
    }

    @Override
    public void finishCreateView(View view)
    {
        listview= (ListView) view.findViewById(R.id.listview);

        String userphone = (String) SpUtil.getParam(getActivity(), "userphone", "");
        HashMap<String, String> map = new HashMap<>();
        RequestManager.getInstance(getActivity()).requestAsyn("api/user_show/" + userphone, RequestManager.TYPE_GET, map, new StringCallBack()
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
                            strlist.add(model.getLog().get(i).getError_msg().replace("\\",""));
                        }
                    }
                    Log.e(TAG, "onReqSuccess: "+strlist.get(0) );
                    ErrorAdapter adapter=new ErrorAdapter(getActivity(),strlist);
                    listview.setAdapter(adapter);
                }
            }

            @Override
            public void onReqFailed(String errorMsg)
            {
                super.onReqFailed(errorMsg);
            }
        });
    }

}
