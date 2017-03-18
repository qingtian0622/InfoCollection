package com.anfeng.infocollection.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/11/30.
 */

public abstract class BaseFragment extends Fragment
{
    protected abstract int getLayoutResId();

    private View parentView;
    private FragmentActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        activity = getSupportActivity();
        return parentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

        super.onViewCreated(view, savedInstanceState);
        finishCreateView(view);
    }

    public abstract void finishCreateView(View view);

    @Override
    public void onAttach(Activity activity)
    {

        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    @Override
    public void onDetach()
    {

        super.onDetach();
        this.activity = null;
    }

    public FragmentActivity getSupportActivity()
    {
        return super.getActivity();
    }
}
