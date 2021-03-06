package com.lbins.hmjs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.lbins.hmjs.R;
import com.lbins.hmjs.module.Area;

import java.util.List;

/**
 * author: ${zhanghailong}
 * Date: 2015/3/9
 * Time: 8:42
 * 类的功能、说明写在此处.
 */
public class ItemAreaAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<Area> findEmps;
    private Context mContext;

    public ItemAreaAdapter(List<Area> findEmps, Context mContext) {
        this.findEmps = findEmps;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return findEmps.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_address_province, null);
            holder.province_title = (TextView) convertView.findViewById(R.id.province_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Area favour = findEmps.get(position);
        if (findEmps != null) {
            holder.province_title.setText(favour.getAreaName());
        }
        return convertView;
    }

    class ViewHolder {
        TextView province_title;
    }
}
