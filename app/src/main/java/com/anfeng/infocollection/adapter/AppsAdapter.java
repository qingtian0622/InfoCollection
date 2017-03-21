package com.anfeng.infocollection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anfeng.infocollection.R;
import com.anfeng.infocollection.model.AppsModel;
import com.anfeng.infocollection.model.UserModel;
import com.anfeng.infocollection.util.DeviceUtils;

import java.util.List;

/**
 * @author liunw 2016/11/17.
 */
public class AppsAdapter extends BaseAdapter
{
    private List<AppsModel.AppsInfoBean> mGameArr;
    Context mCxt;


    public AppsAdapter(Context cxt, List<AppsModel.AppsInfoBean> gameArr)
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
    public AppsModel.AppsInfoBean getItem(int position)
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
            convertView = LayoutInflater.from(mCxt).inflate(R.layout.item_app, null);
            holder.iconImg = (ImageView) convertView.findViewById(R.id.image);
            holder.gameNameTv = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        final AppsModel.AppsInfoBean gameItem = mGameArr.get(position);

        holder.iconImg.setImageDrawable(DeviceUtils.getAppIcon(mCxt, gameItem.getPackageName()));

        holder.gameNameTv.setText(gameItem.getAppName());

        return convertView;

    }


    static class ViewHolder
    {
        ImageView iconImg;
        TextView gameNameTv;
    }
}
