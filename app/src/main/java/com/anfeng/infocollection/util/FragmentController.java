package com.anfeng.infocollection.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.anfeng.infocollection.fragments.ContactsFragment;
import com.anfeng.infocollection.fragments.MessageFragment;
import com.anfeng.infocollection.fragments.MineFragment;

import java.util.ArrayList;


public class FragmentController
{

    private static FragmentController controller;
    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private FragmentController(FragmentActivity activity, int containerId)
    {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    public static FragmentController getInstance(FragmentActivity activity, int containerId)
    {

        if (controller == null)
        {
            synchronized (FragmentController.class)
            {
                if (controller == null)
                {
                    controller = new FragmentController(activity, containerId);
                }
            }
        }

        return controller;
    }

    public static void onDestroy()
    {
        if (controller != null)
        {
            Log.e("controller", "onDestoryController: aaaaa");
            controller = null;
        }
    }

    private void initFragment()
    {
        fragments = new ArrayList<Fragment>();
        fragments.add(new MessageFragment());
        fragments.add(new ContactsFragment());
        fragments.add(new MineFragment());

        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments)
        {
            ft.add(containerId, fragment);
        }
        ft.commitAllowingStateLoss();
    }

    public void showFragment(int position)
    {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    public void hideFragments()
    {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments)
        {
            if (fragment != null)
            {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
    }

    public Fragment getFragment(int position)
    {
        return fragments.get(position);
    }
}