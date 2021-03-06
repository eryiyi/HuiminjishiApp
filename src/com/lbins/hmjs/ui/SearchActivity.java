package com.lbins.hmjs.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.lbins.hmjs.R;
import com.lbins.hmjs.base.BaseActivity;
import com.lbins.hmjs.fragment.SearchOneFragment;
import com.lbins.hmjs.util.GuirenHttpUtils;

/**
 * Created by zhl on 2016/8/30.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fm;

    private SearchOneFragment oneFragment;

    private TextView btn1;

    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        res = getResources();
        fm = getSupportFragmentManager();
        initView();
        switchFragment(R.id.btn1);
    }

    private void initView() {
        this.findViewById(R.id.back).setOnClickListener(this);
        btn1 = (TextView) this.findViewById(R.id.btn1);

//        btn1.setOnClickListener(this);
    }

    public void switchFragment(int id) {
        fragmentTransaction = fm.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (id) {
            case R.id.btn1:
                if (oneFragment == null) {
                    oneFragment = new SearchOneFragment();
                    fragmentTransaction.add(R.id.search_content_frame, oneFragment);
                } else {
                    fragmentTransaction.show(oneFragment);
                }
                btn1.setTextColor(res.getColor(R.color.white));
                break;

        }
        fragmentTransaction.commit();
    }

    private void hideFragments(FragmentTransaction ft) {
        if (oneFragment != null) {
            ft.hide(oneFragment);
        }
    }

    boolean isMobileNet, isWifiNet;

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.back){
            finish();
        }
        try {
            isMobileNet = GuirenHttpUtils.isMobileDataEnable(getApplicationContext());
            isWifiNet = GuirenHttpUtils.isWifiDataEnable(getApplicationContext());
            if (!isMobileNet && !isWifiNet) {
                Toast.makeText(this, R.string.net_work_error, Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        switchFragment(view.getId());
    }
}
