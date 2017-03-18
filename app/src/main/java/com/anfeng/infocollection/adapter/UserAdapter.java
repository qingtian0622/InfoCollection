package com.anfeng.infocollection.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anfeng.infocollection.R;
import com.anfeng.infocollection.model.UserModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liunw 2016/11/17.
 */
public class UserAdapter extends BaseAdapter
{
    private List<UserModel.UserBean> mGameArr;
    Context mCxt;


    public UserAdapter(Context cxt, List<UserModel.UserBean> gameArr)
    {
        this.mCxt = cxt;
        this.mGameArr = gameArr;

    }

    @Override
    public int getCount()
    {
        if (mGameArr == null)
            return 0;
        return mGameArr.size();
    }

    @Override
    public UserModel.UserBean getItem(int position)
    {
        return mGameArr.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mCxt).inflate(R.layout.item_user, null);
            holder.iconImg = (ImageView) convertView.findViewById(R.id.future_firstpay_icon_img);
            holder.gameNameTv = (TextView) convertView.findViewById(R.id.future_firatpay_name_tv);
            holder.timeTv = (TextView) convertView.findViewById(R.id.future_firatpay_time_tv);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        final UserModel.UserBean gameItem = mGameArr.get(position);

        if (("男").equals(gameItem.getGender()))
        {
            holder.iconImg.setImageResource(R.mipmap.head_man);
        }
        else if (("女").equals(gameItem.getGender()))
        {
            holder.iconImg.setImageResource(R.mipmap.head_women);
        }
        else
        {
            holder.iconImg.setImageResource(R.mipmap.default_head);
        }

        holder.gameNameTv.setText(gameItem.getName());
        holder.timeTv.setText(gameItem.getMobile());

        return convertView;

    }


    static class ViewHolder
    {
        ImageView iconImg;
        TextView oriPriceTv;
        TextView gameNameTv;
        TextView leftNumTv;
        TextView timeTv;
    }
}
