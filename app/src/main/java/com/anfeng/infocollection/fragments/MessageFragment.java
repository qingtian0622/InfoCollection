package com.anfeng.infocollection.fragments;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.anfeng.infocollection.PersonActivity;
import com.anfeng.infocollection.R;
import com.anfeng.infocollection.adapter.UserAdapter;
import com.anfeng.infocollection.base.BaseFragment;
import com.anfeng.infocollection.cache.ACache;
import com.anfeng.infocollection.http.RequestManager;
import com.anfeng.infocollection.http.callback.StringCallBack;
import com.anfeng.infocollection.model.UserModel;
import com.google.gson.Gson;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

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
    private UserAdapter adapter;
    private ShimmerTextView shimmerTextView;
    private Shimmer shimmer;
    //    private List<>
    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_message;
    }

    @Override
    public void finishCreateView(View view)
    {
        shimmerTextView= (ShimmerTextView) view.findViewById(R.id.shimmer_tv);
        shimmer = new Shimmer();
        shimmer.start(shimmerTextView);

        mlistview = (ListView) view.findViewById(R.id.listview);
        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                UserModel.UserBean model= (UserModel.UserBean) adapterView.getItemAtPosition(i);
                //Toast.makeText(getActivity(),model.getName(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(), PersonActivity.class);
                intent.putExtra("userphone",model.getMobile());
                intent.putExtra("username",model.getName());
                startActivity(intent);
            }
        });
        ACache macache = ACache.get(getActivity());
        String result_acache = macache.getAsString("list");
        if (!TextUtils.isEmpty(result_acache))
        {
            Log.e(TAG, "finishCreateView: "+"缓存" +result_acache);
            Gson gson = new Gson();
            UserModel model = gson.fromJson(result_acache, UserModel.class);
            UserAdapter adapter = new UserAdapter(getActivity(), model.getUser());
            mlistview.setAdapter(adapter);
            shimmerTextView.setVisibility(View.GONE);
            shimmer.cancel();
        }
        else
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("user_id", "1");
            RequestManager.getInstance(getActivity()).requestAsyn("api/user_all", RequestManager.TYPE_GET, map, new StringCallBack()
            {
                @Override
                public void onReqSuccess(String result)
                {
                    super.onReqSuccess(result);
                    Log.e(TAG, "onReqSuccess: " + result);
                    Gson gson = new Gson();
                    UserModel model = gson.fromJson(result, UserModel.class);
                    UserAdapter adapter = new UserAdapter(getActivity(), model.getUser());
                    mlistview.setAdapter(adapter);
                    ACache aCache = ACache.get(getActivity());
                    aCache.put("list", result, ACache.TIME_DAY);
                    shimmerTextView.setVisibility(View.GONE);
                    shimmer.cancel();
                }

                @Override
                public void onReqFailed(String errorMsg)
                {
                    super.onReqFailed(errorMsg);
                    Log.e(TAG, "onReqFailed: " + errorMsg);
                    shimmerTextView.setText("加载失败");
                }
            });
        }
    }

}
