package com.lbins.hmjs.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.hyphenate.util.LatLng;
import com.lbins.hmjs.MeetLoveApplication;
import com.lbins.hmjs.R;
import com.lbins.hmjs.dao.Emp;
import com.lbins.hmjs.module.RecordVO;
import com.lbins.hmjs.ui.GalleryUrlActivity;
import com.lbins.hmjs.util.Constants;
import com.lbins.hmjs.util.StringUtil;
import com.lbins.hmjs.widget.PictureGridview;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * 动态
 */
public class RecordAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<RecordVO> contents;
    private Context mContext;
    private Resources res;

    private OnClickContentItemListener onClickContentItemListener;

    public void setOnClickContentItemListener(OnClickContentItemListener onClickContentItemListener) {
        this.onClickContentItemListener = onClickContentItemListener;
    }


    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

    public RecordAdapter(List<RecordVO> contents, Context mContext) {
        this.contents = contents;
        this.mContext = mContext;
        res = mContext.getResources();
    }

    @Override
    public int getCount() {
        return contents.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_record, null);
            holder.cover = (ImageView) convertView.findViewById(R.id.cover);
            holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
            holder.cont = (TextView) convertView.findViewById(R.id.cont);
            holder.gridview = (PictureGridview) convertView.findViewById(R.id.gridview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final RecordVO cell = contents.get(position);//获得元素
        if (cell != null) {
            if(!StringUtil.isNullOrEmpty(cell.getNickname())){
                holder.nickname.setText(cell.getNickname());
            }
            if(!StringUtil.isNullOrEmpty(cell.getRecord_cont())){
                holder.cont.setText(cell.getRecord_cont());
            }
            if(!StringUtil.isNullOrEmpty(cell.getCover())){
                imageLoader.displayImage(cell.getCover(), holder.cover, MeetLoveApplication.txOptions, animateFirstListener);
            }
            if(!StringUtil.isNullOrEmpty(cell.getRecord_pic())){
                String picStr = cell.getRecord_pic();
                if(!StringUtil.isNullOrEmpty(picStr) && !",".equals(picStr)){
                    final String[] arras = picStr.split(",");
                    if(arras != null && arras.length > 0){
                        holder.gridview.setVisibility(View.VISIBLE);
                        holder.gridview.setAdapter(new ImageGridViewAdapter(arras, mContext));
                        holder.gridview.setClickable(true);
                        holder.gridview.setPressed(true);
                        holder.gridview.setEnabled(true);
                    }else {
                        holder.gridview.setVisibility(View.GONE);
                    }
                    holder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(mContext, GalleryUrlActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            intent.putExtra(Constants.IMAGE_URLS, arras);
                            intent.putExtra(Constants.IMAGE_POSITION, position);
                            mContext.startActivity(intent);
                        }
                    });
                }
            }
        }
        return convertView;
    }

    class ViewHolder {
        ImageView cover;
        TextView nickname;
        TextView cont;
        PictureGridview gridview;
    }

}
