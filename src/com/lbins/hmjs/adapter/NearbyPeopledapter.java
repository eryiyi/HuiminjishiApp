package com.lbins.hmjs.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.hyphenate.util.LatLng;
import com.lbins.hmjs.MeetLoveApplication;
import com.lbins.hmjs.R;
import com.lbins.hmjs.dao.Emp;
import com.lbins.hmjs.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * 推荐首页 推荐人列表
 */
public class NearbyPeopledapter extends BaseAdapter {
    private ViewHolder holder;
    private List<Emp> contents;
    private Context mContext;
    private Resources res;

    private OnClickContentItemListener onClickContentItemListener;

    public void setOnClickContentItemListener(OnClickContentItemListener onClickContentItemListener) {
        this.onClickContentItemListener = onClickContentItemListener;
    }


    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

    public NearbyPeopledapter(List<Emp> contents, Context mContext) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_index_tuijian_people, null);
            holder.cover = (ImageView) convertView.findViewById(R.id.cover);
            holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
            holder.distance = (TextView) convertView.findViewById(R.id.distance);
            holder.role = (TextView) convertView.findViewById(R.id.role);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Emp cell = contents.get(position);//获得元素
        if (cell != null) {
            if(!StringUtil.isNullOrEmpty(cell.getNickname())){
                holder.nickname.setText(cell.getNickname());
            }
            if(!StringUtil.isNullOrEmpty(cell.getCover())){
                imageLoader.displayImage(cell.getCover(), holder.cover, MeetLoveApplication.txOptions, animateFirstListener);
            }
            if(cell.getRolestate().equals("0")){
                holder.role.setText("技师");
                holder.role.setTextColor(mContext.getResources().getColor(R.color.button_color_orange_n));
            }else {
                holder.role.setText("会员");
                holder.role.setTextColor(mContext.getResources().getColor(R.color.textColortwo));
            }


            if(!StringUtil.isNullOrEmpty(MeetLoveApplication.latStr) && !StringUtil.isNullOrEmpty(MeetLoveApplication.lngStr) && !StringUtil.isNullOrEmpty(cell.getLng())&& !StringUtil.isNullOrEmpty(cell.getLat()) ){
                LatLng latLng = new LatLng(Double.valueOf(MeetLoveApplication.latStr), Double.valueOf(MeetLoveApplication.lngStr));
                LatLng latLng1 = new LatLng(Double.valueOf(cell.getLat()), Double.valueOf(cell.getLng()));
                String distance = StringUtil.getDistance(latLng, latLng1);
                holder.distance.setText(distance + "km");
            }
        }
        return convertView;
    }

    class ViewHolder {
        ImageView cover;
        TextView nickname;
        TextView role;
        TextView distance;
    }

}
