package com.lbins.hmjs.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.lbins.hmjs.R;
import com.lbins.hmjs.base.BaseFragment;
import com.lbins.hmjs.ui.MineRecordsActivity;
import com.lbins.hmjs.ui.NearbyActivity;
import com.lbins.hmjs.ui.SearchActivity;

/**
 * Created by zhl on 2016/7/1.
 * 发现
 */
public class FindFragment extends BaseFragment implements View.OnClickListener  {

    private View view;
    private Resources res;

    private ImageView btn_right;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.find_fragment, null);
        res = getActivity().getResources();
        initView();
        return view;
    }

    void initView(){
        view.findViewById(R.id.back).setVisibility(View.GONE);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText("发现");

        btn_right = (ImageView) view.findViewById(R.id.btn_right);
        btn_right.setImageDrawable(res.getDrawable(R.drawable.icon_navbar_search));
        btn_right.setOnClickListener(this);

        view.findViewById(R.id.liner_quan).setOnClickListener(this);
        view.findViewById(R.id.liner_nearyby).setOnClickListener(this);
        view.findViewById(R.id.liner_goods).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_right:
            {
                //搜索
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
                break;
            case R.id.liner_quan:
            {
                //朋友圈
                Intent intent = new Intent(getActivity() , MineRecordsActivity.class);
                startActivity(intent);
            }
                break;
            case R.id.liner_nearyby:
            {
                //附近
                Intent intent = new Intent(getActivity() , NearbyActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.liner_goods:
            {
                //商城
                Intent intent = new Intent(getActivity() , NearbyActivity.class);
                startActivity(intent);
            }
            break;
        }
    }


}
