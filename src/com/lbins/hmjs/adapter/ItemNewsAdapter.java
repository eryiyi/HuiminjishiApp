package com.lbins.hmjs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.lbins.hmjs.R;
import com.lbins.hmjs.dao.HappyHandNews;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 */
public class ItemNewsAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<HappyHandNews> records;
    private Context mContext;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

    public ItemNewsAdapter(List<HappyHandNews> records, Context mContext) {
        this.records = records;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return records.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_mine_news, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.dateline = (TextView) convertView.findViewById(R.id.dateline);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final HappyHandNews cell = records.get(position);
        if (cell != null) {
            holder.title.setText(cell.getTitle());
            holder.dateline.setText(cell.getDateline());
        }
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView dateline;
    }
}
