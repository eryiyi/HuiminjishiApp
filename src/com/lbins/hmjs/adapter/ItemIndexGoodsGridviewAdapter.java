package com.lbins.hmjs.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lbins.hmjs.MeetLoveApplication;
import com.lbins.hmjs.R;
import com.lbins.hmjs.module.PaihangObj;
import com.lbins.hmjs.module.PaopaoGoods;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by Administrator on 2015/5/27.
 * 首页下方的推荐商品套餐
 */
public class ItemIndexGoodsGridviewAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<PaihangObj> lists;
    private Context mContect;
    Resources res;

    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private OnClickContentItemListener onClickContentItemListener;

    public void setOnClickContentItemListener(OnClickContentItemListener onClickContentItemListener) {
        this.onClickContentItemListener = onClickContentItemListener;
    }


    public ItemIndexGoodsGridviewAdapter(List<PaihangObj> lists, Context mContect) {
        this.lists = lists;
        this.mContect = mContect;
    }

    @Override
    public int getCount() {
        return lists.size();
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
        res = mContect.getResources();
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContect).inflate(R.layout.item_index_gridv_goods, null);
            holder.cover = (ImageView) convertView.findViewById(R.id.cover);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.money_one = (TextView) convertView.findViewById(R.id.money_one);
            holder.icon_baoyou = (TextView) convertView.findViewById(R.id.icon_baoyou);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PaihangObj cell = lists.get(position);
        if (cell != null) {
            imageLoader.displayImage(cell.getGoods_cover(), holder.cover, MeetLoveApplication.options, animateFirstListener);
            String nameStr = cell.getGoods_name()==null?"":cell.getGoods_name();
            if(nameStr.length()>20){
                nameStr = nameStr.substring(0,19);
            }
            holder.name.setText(nameStr);
            holder.money_one.setText("￥" + cell.getSell_price());
            holder.icon_baoyou.setText(cell.getMarket_price());
            holder.icon_baoyou.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        }

        return convertView;
    }

    class ViewHolder {
        ImageView cover;
        TextView name;
        TextView money_one;
        TextView icon_baoyou;
    }
}
