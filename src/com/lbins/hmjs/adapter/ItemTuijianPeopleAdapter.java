package com.lbins.hmjs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lbins.hmjs.MeetLoveApplication;
import com.lbins.hmjs.R;
import com.lbins.hmjs.dao.Emp;
import com.lbins.hmjs.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 */
public class ItemTuijianPeopleAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<Emp> records;
    private Context mContext;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

    public ItemTuijianPeopleAdapter(List<Emp> records, Context mContext) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tuijian_people, null);
            holder.item_cover = (ImageView) convertView.findViewById(R.id.item_cover);
            holder.item_nickname = (TextView) convertView.findViewById(R.id.item_nickname);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Emp cell = records.get(position);
        if (cell != null) {

            if(!StringUtil.isNullOrEmpty(cell.getCover())){
                imageLoader.displayImage(cell.getCover(), holder.item_cover, MeetLoveApplication.txOptions, animateFirstListener);
            }
            holder.item_nickname.setText(cell.getNickname());
        }
        return convertView;
    }

    class ViewHolder {
        ImageView item_cover;
        TextView item_nickname;
    }
}
