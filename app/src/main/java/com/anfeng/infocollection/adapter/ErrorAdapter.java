package com.anfeng.infocollection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anfeng.infocollection.R;
import com.anfeng.infocollection.model.ErrorModel;
import com.anfeng.infocollection.model.UserModel;
import com.google.gson.Gson;

import java.util.List;

/**
 * @author liunw 2016/11/17.
 */
public class ErrorAdapter extends BaseAdapter
{
    private List<String> mGameArr;
    Context mCxt;


    public ErrorAdapter(Context cxt, List<String> gameArr)
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
    public String getItem(int position)
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

        final String gameItem = mGameArr.get(position);
        Gson gson=new Gson();
        ErrorModel model=gson.fromJson(gameItem,ErrorModel.class);

//        if (("男").equals(gameItem.getGender()))
//        {
//            holder.iconImg.setImageResource(R.mipmap.head_man);
//        }
//        else if (("女").equals(gameItem.getGender()))
//        {
//            holder.iconImg.setImageResource(R.mipmap.head_women);
//        }
//        else
//        {
//            holder.iconImg.setImageResource(R.mipmap.default_head);
//        }

        holder.gameNameTv.setText(model.getError_summer());
        holder.timeTv.setText(model.getError_time());

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
