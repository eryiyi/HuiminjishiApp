package com.lbins.hmjs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.lbins.hmjs.MeetLoveApplication;
import com.lbins.hmjs.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 */
public class ItemPhotosAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<String> records;
    private Context mContext;
    private int tmpS = 0;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

    private OnClickContentItemListener onClickContentItemListener;

    public void setOnClickContentItemListener(OnClickContentItemListener onClickContentItemListener) {
        this.onClickContentItemListener = onClickContentItemListener;
    }

    public ItemPhotosAdapter(List<String> records, Context mContext, int tmpS) {
        this.records = records;
        this.mContext = mContext;
        this.tmpS = tmpS;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_photos, null);
            holder.item_pic = (ImageView) convertView.findViewById(R.id.item_pic);
            holder.btnDelete = (ImageView) convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String cell = records.get(position);
        if (cell != null) {
            imageLoader.displayImage(cell, holder.item_pic, MeetLoveApplication.options, animateFirstListener);
            if(tmpS == 1){
                holder.btnDelete.setVisibility(View.VISIBLE);
            }else {
                holder.btnDelete.setVisibility(View.GONE);
            }
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickContentItemListener.onClickContentItem(position, 1, "1000");
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        ImageView item_pic;
        ImageView btnDelete;
    }
}