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
import com.lbins.hmjs.module.RecordComment;
import com.lbins.hmjs.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * 动态评论
 */
public class ItemRecordCommentAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<RecordComment> records;
    private Context mContext;

    private OnClickContentItemListener onClickContentItemListener;

    public void setOnClickContentItemListener(OnClickContentItemListener onClickContentItemListener) {
        this.onClickContentItemListener = onClickContentItemListener;
    }

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

    public ItemRecordCommentAdapter(List<RecordComment> records, Context mContext) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_record_comment, null);
            holder.cover = (ImageView) convertView.findViewById(R.id.cover);
            holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
            holder.dateline = (TextView) convertView.findViewById(R.id.dateline);
            holder.cont = (TextView) convertView.findViewById(R.id.cont);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final RecordComment cell = records.get(position);
        if (cell != null) {
            if(!StringUtil.isNullOrEmpty(cell.getCommentCover())){
                imageLoader.displayImage(cell.getCommentCover(), holder.cover, MeetLoveApplication.txOptions, animateFirstListener);
            }
            holder.nickname.setText(cell.getCommentNickname());
            holder.cont.setText(cell.getComment_cont());
            holder.dateline.setText(cell.getComment_dateline());
        }
        holder.nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickContentItemListener.onClickContentItem(position, 1, null);
            }
        });
        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickContentItemListener.onClickContentItem(position, 1, null);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView cover;
        TextView nickname;
        TextView dateline;
        TextView cont;
    }
}
