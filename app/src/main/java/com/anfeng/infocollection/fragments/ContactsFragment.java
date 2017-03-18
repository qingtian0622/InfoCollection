package com.anfeng.infocollection.fragments;

import android.util.Log;
import android.view.View;

import com.anfeng.infocollection.R;
import com.anfeng.infocollection.base.BaseFragment;


/**
 * Created by Administrator on 2017/2/21.
 */

public class ContactsFragment extends BaseFragment
{
    private static final String TAG = "ContactsFragment";
    @Override
    protected int getLayoutResId()
    {
        return R.layout.fragment_contacts;
    }

    @Override
    public void finishCreateView(View view)
    {
        //Log.e(TAG, "finishCreateView: "+(1/0) );
    }
}
