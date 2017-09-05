package com.lbins.hmjs.ui;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lbins.hmjs.MeetLoveApplication;
import com.lbins.hmjs.R;
import com.lbins.hmjs.adapter.*;
import com.lbins.hmjs.base.BaseActivity;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.data.GoodsTypeData;
import com.lbins.hmjs.data.LxAdData;
import com.lbins.hmjs.data.PaihangObjData;
import com.lbins.hmjs.data.PaopaoGoodsData;
import com.lbins.hmjs.library.HeaderGridView;
import com.lbins.hmjs.library.PullToRefreshBase;
import com.lbins.hmjs.library.PullToRefreshHeadGridView;
import com.lbins.hmjs.module.GoodsType;
import com.lbins.hmjs.module.LxAd;
import com.lbins.hmjs.module.PaihangObj;
import com.lbins.hmjs.module.PaopaoGoods;
import com.lbins.hmjs.util.StringUtil;
import com.lbins.hmjs.widget.ClassifyGridview;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/8/30.
 */
public class ShangchengActivity extends BaseActivity implements View.OnClickListener,OnClickContentItemListener {

    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();


    private EditText keywords;

    private PullToRefreshHeadGridView lstv;
    private ItemIndexGoodsGridviewAdapter adapter;
    List<PaihangObj> listsgoods = new ArrayList<PaihangObj>();
    private int pageIndex = 1;
    private static boolean IS_REFRESH = true;

    //轮播广告
    private ViewPager viewpager;
    private ShangchengAdViewPagerAdapter adapterAd;
    private LinearLayout viewGroup;
    private ImageView dot, dots[];
    private Runnable runnable;
    private int autoChangeTime = 5000;
    private List<LxAd> listsAd = new ArrayList<LxAd>();

    //分类
    private GoodsTypeIndexAdapter adaptertype;
    ClassifyGridview gridv_one;//商品分类

    //商品分类
    public static List<GoodsType> listGoodsType = new ArrayList<GoodsType>();

    private ImageView img_new;
    private ImageView img_tehui;
    private LxAd lxadNew;
    private LxAd lxadTehui;

    private Handler myHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case 1:
                {
                    imageLoader.displayImage(lxadNew.getAd_pic(), img_new, MeetLoveApplication.options, animateFirstListener);
                }
                break;
                case 2:
                {
                    imageLoader.displayImage(lxadTehui.getAd_pic(), img_tehui, MeetLoveApplication.options, animateFirstListener);
                }
                break;
                case 3:
                {
                    //轮播广告
                    initViewPager();
                }
                break;
                case 4:
                {
                    adaptertype.notifyDataSetChanged();
                }
                break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shangcheng_activity);
        registerBoradcastReceiver();
        initView();
        //商城分类
        initViewType();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //查询首发和特惠专区
                getAdsNew();
                getAdTehui();
                //轮播广告
                getAdsOne();
                //查询商品分类
                getGoodsType();
            }
        }).start();


        initData();
    }


    //商城分类
    private void initViewType() {
        gridv_one = (ClassifyGridview) headLiner.findViewById(R.id.gridv_one);
        adaptertype = new GoodsTypeIndexAdapter(listGoodsType, ShangchengActivity.this);
        gridv_one.setAdapter(adaptertype);
        gridv_one.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listGoodsType.size()>(position)){
                    GoodsType goodsType = listGoodsType.get(position);
                    if(goodsType != null){
                        if("0".equals(goodsType.getTypeId())){
                            //更多
                            Intent intent = new Intent(ShangchengActivity.this, SearchMoreTypeActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(ShangchengActivity.this, SearchGoodsByTypeActivity.class);
                            intent.putExtra("typeId", "");
                            intent.putExtra("typeName", "");
                            intent.putExtra("keyContent", keywords.getText().toString());
                            startActivity(intent);
                        }
                    }
                }
            }
        });
        gridv_one.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    private LinearLayout headLiner;

    private void initView() {
        this.findViewById(R.id.back).setOnClickListener(this);
        headLiner = (LinearLayout) LayoutInflater.from(ShangchengActivity.this).inflate(R.layout.shangcheng_header, null);

        headLiner.findViewById(R.id.btn_cart).setOnClickListener(this);
        headLiner.findViewById(R.id.btn_cz).setOnClickListener(this);
        headLiner.findViewById(R.id.btn_order).setOnClickListener(this);

        keywords = (EditText) this.findViewById(R.id.keywords);
//        keywords.addTextChangedListener(watcher);

        keywords.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    Intent intent = new Intent(ShangchengActivity.this, SearchGoodsByTypeActivity.class);
                    intent.putExtra("typeId", "");
                    intent.putExtra("typeName", "");
                    intent.putExtra("keyContent", keywords.getText().toString());
                    startActivity(intent);
                    keywords.clearFocus();//失去焦点
                } else {
                    // 此处为失去焦点时的处理内容
                }
            }
        });


        img_new = (ImageView) headLiner.findViewById(R.id.img_new);
        img_tehui = (ImageView) headLiner.findViewById(R.id.img_tehui);
        img_new.setOnClickListener(this);
        img_tehui.setOnClickListener(this);
        lstv = (PullToRefreshHeadGridView) this.findViewById(R.id.lstv);

        lstv.setMode(PullToRefreshBase.Mode.BOTH);
//        lstv.setAdapter(null);
        HeaderGridView lv = lstv.getRefreshableView();
        lv.setNumColumns(2);

        lv.addHeaderView(headLiner);
        adapter = new ItemIndexGoodsGridviewAdapter(listsgoods, ShangchengActivity.this);
        lstv.setAdapter(adapter);
        lstv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<HeaderGridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<HeaderGridView> refreshView) {
                String label = DateUtils.formatDateTime(ShangchengActivity.this.getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                IS_REFRESH = true;
                pageIndex = 1;
                initData();
                lstv.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<HeaderGridView> refreshView) {
                String label = DateUtils.formatDateTime(ShangchengActivity.this.getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                IS_REFRESH = false;
                pageIndex++;
                initData();
                lstv.onRefreshComplete();
            }
        });
        lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listsgoods.size() > (position-2) ) {
                    PaihangObj paihangObj = listsgoods.get(position - 2);
                    if(paihangObj != null){
                        Intent intent  = new Intent(ShangchengActivity.this, DetailPaopaoGoodsActivity.class);
                        intent.putExtra("emp_id_dianpu", paihangObj.getGoods_emp_id());
                        intent.putExtra("goods_id", paihangObj.getGoods_id());
                        startActivity(intent);
                    }
                }
            }
        });


        headLiner.findViewById(R.id.btn_first_more).setOnClickListener(this);
        headLiner.findViewById(R.id.btn_tehui_more).setOnClickListener(this);
    }


    void initData() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.GET_INDEX_TUIJIAN_LISTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code = jo.getString("code");
                                if (Integer.parseInt(code) == 200) {
                                    PaihangObjData data = getGson().fromJson(s, PaihangObjData.class);
                                    if (IS_REFRESH) {
                                        listsgoods.clear();
                                    }
                                    listsgoods.addAll(data.getData());
                                    lstv.onRefreshComplete();
                                    adapter.notifyDataSetChanged();
                                }else {
                                    Toast.makeText(ShangchengActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        lstv.onRefreshComplete();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("index", String.valueOf(pageIndex));
                params.put("is_type", "0");
                params.put("is_del", "0");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        getRequestQueue().add(request);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

//            case R.id.btn_first_more:
//            {
//                //首发更多
//                Intent intent = new Intent(ShangchengActivity.this, SearchTuijianActivity.class);
//                intent.putExtra("is_type", "1");
//                startActivity(intent);
//            }
//            break;
//            case R.id.btn_tehui_more:
//            {
//                //特惠更多
//                Intent intent = new Intent(ShangchengActivity.this, SearchTuijianActivity.class);
//                intent.putExtra("is_type", "2");
//                startActivity(intent);
//            }
//            break;
            case R.id.img_new:
            {
                if(lxadNew != null){
                    Intent intent  = new Intent(ShangchengActivity.this, DetailPaopaoGoodsActivity.class);
                    intent.putExtra("emp_id_dianpu", lxadNew.getAd_emp_id());
                    intent.putExtra("goods_id", lxadNew.getAd_msg_id());
                    startActivity(intent);
                }
            }
            break;
            case R.id.img_tehui:
            {
                if(lxadTehui != null){
                    Intent intent  = new Intent(ShangchengActivity.this, DetailPaopaoGoodsActivity.class);
                    intent.putExtra("emp_id_dianpu", lxadTehui.getAd_emp_id());
                    intent.putExtra("goods_id", lxadTehui.getAd_msg_id());
                    startActivity(intent);
                }
            }
            break;
            case R.id.btn_cart:
            {

                    Intent intent  = new Intent(ShangchengActivity.this, MineCartActivity.class);
                    startActivity(intent);
            }
            break;
            case R.id.btn_cz:
            {

                    Intent intent  = new Intent(ShangchengActivity.this, BankCardCztxActivity.class);
                    startActivity(intent);
            }
            break;
            case R.id.btn_order:
            {

                    Intent intent  = new Intent(ShangchengActivity.this, MineOrdersActivity.class);
                    intent.putExtra("status", "");
                    startActivity(intent);
            }
            break;
            case R.id.back:
            {
                finish();
            }
                break;
        }
    }


    private void initViewPager() {
        adapterAd = new ShangchengAdViewPagerAdapter(ShangchengActivity.this);
        adapterAd.change(listsAd);
        adapterAd.setOnClickContentItemListener(this);
        viewpager = (ViewPager) headLiner.findViewById(R.id.viewpager);
        viewpager.setAdapter(adapterAd);
        viewpager.setOnPageChangeListener(myOnPageChangeListener);
        initDot();
        runnable = new Runnable() {
            @Override
            public void run() {
                int next = viewpager.getCurrentItem() + 1;
                if (next >= adapterAd.getCount()) {
                    next = 0;
                }
                viewHandler.sendEmptyMessage(next);
            }
        };
        viewHandler.postDelayed(runnable, autoChangeTime);
    }


    // 初始化dot视图
    private void initDot() {
        viewGroup = (LinearLayout) headLiner.findViewById(R.id.viewGroup);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                20, 20);
        layoutParams.setMargins(4, 3, 4, 3);

        dots = new ImageView[adapterAd.getCount()];
        for (int i = 0; i < adapterAd.getCount(); i++) {
            dot = new ImageView(ShangchengActivity.this);
            dot.setLayoutParams(layoutParams);
            dots[i] = dot;
            dots[i].setTag(i);
            dots[i].setOnClickListener(onClick);

            if (i == 0) {
                dots[i].setBackgroundResource(R.drawable.dotc);
            } else {
                dots[i].setBackgroundResource(R.drawable.dotn);
            }

            viewGroup.addView(dots[i]);
        }
    }

    ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            setCurDot(arg0);
            viewHandler.removeCallbacks(runnable);
            viewHandler.postDelayed(runnable, autoChangeTime);
        }

    };
    // 实现dot点击响应功能,通过点击事件更换页面
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();
            setCurView(position);
        }

    };

    /**
     * 设置当前的引导页
     */
    private void setCurView(int position) {
        if (position < 0 || position > adapterAd.getCount()) {
            return;
        }
        viewpager.setCurrentItem(position);
//        if (!StringUtil.isNullOrEmpty(lists.get(position).getNewsTitle())){
//            titleSlide = lists.get(position).getNewsTitle();
//            if(titleSlide.length() > 13){
//                titleSlide = titleSlide.substring(0,12);
//                article_title.setText(titleSlide);//当前新闻标题显示
//            }else{
//                article_title.setText(titleSlide);//当前新闻标题显示
//            }
//        }

    }

    /**
     * 选中当前引导小点
     */
    private void setCurDot(int position) {
        for (int i = 0; i < dots.length; i++) {
            if (position == i) {
                dots[i].setBackgroundResource(R.drawable.dotc);
            } else {
                dots[i].setBackgroundResource(R.drawable.dotn);
            }
        }
    }

    /**
     * 每隔固定时间切换广告栏图片
     */
    @SuppressLint("HandlerLeak")
    private final Handler viewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setCurView(msg.what);
        }

    };

    @Override
    public void onClickContentItem(int position, int flag, Object object) {
        switch (flag){
            case 0:
                LxAd lxAd= (LxAd) object;
                if(lxAd != null){
                    Intent intent  = new Intent(ShangchengActivity.this, DetailPaopaoGoodsActivity.class);
                    intent.putExtra("emp_id_dianpu", lxAd.getAd_emp_id());
                    intent.putExtra("goods_id", lxAd.getAd_msg_id());
                    startActivity(intent);
                }
                break;
        }
    }

    private void getGoodsType() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.GET_GOODS_TYPE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {

                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    GoodsTypeData data = getGson().fromJson(s, GoodsTypeData.class);
                                    listGoodsType.clear();
                                    List<GoodsType> listsgoodstype = new ArrayList<GoodsType>();
                                    listsgoodstype.clear();
                                    listsgoodstype.addAll(data.getData());
                                    if(listsgoodstype != null){
                                        for(int i=0;i<(listsgoodstype.size()<7?listsgoodstype.size():7);i++){
                                            listGoodsType.add(listsgoodstype.get(i));
                                        }
                                    }
                                    GoodsType goodsType = new GoodsType();
                                    goodsType.setTypeId("0");
                                    goodsType.setTypeName("更多");
                                    goodsType.setTypeIsUse("0");
                                    listGoodsType.add(goodsType);
                                    Message msg = Message.obtain();
                                    msg.what = 4;
                                    msg.obj = "Rowandjj";
                                    myHandler.sendMessage(msg);
                                } else {
                                    Toast.makeText(ShangchengActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(ShangchengActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                        }
                        if(progressDialog != null){
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if(progressDialog != null){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(ShangchengActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        getRequestQueue().add(request);
    }

    //广播接收动作
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("update_location_success")) {
                //定位地址
            }

        }
    };

    //注册广播
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("update_location_success");
        //注册广播
        ShangchengActivity.this.registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShangchengActivity.this.unregisterReceiver(mBroadcastReceiver);
    }


    //1推荐顶部轮播图  2推荐中部广告（大） 3 推荐中部广告（小） 4 商城顶部轮播图  5 商城首发新品 6 商城特惠专区
    //获取轮播图-
    void getAdsOne() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.GET_AD_LIST_TYPE_LISTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code = jo.getString("code");
                                if (Integer.parseInt(code) == 200) {
                                    LxAdData data = getGson().fromJson(s, LxAdData.class);
                                    if(data != null && data.getData() != null){
                                        listsAd.clear();
                                        listsAd.addAll(data.getData());
                                    }
                                    Message msg = Message.obtain();
                                    msg.what = 3;
                                    msg.obj = "Rowandjj";
                                    myHandler.sendMessage(msg);
                                } else {
                                    Toast.makeText(ShangchengActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(progressDialog != null){
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if(progressDialog != null){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(ShangchengActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ad_type", "4");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        getRequestQueue().add(request);
    }

    void getAdsNew() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.GET_AD_LIST_TYPE_LISTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code = jo.getString("code");
                                if (Integer.parseInt(code) == 200) {
                                    LxAdData data = getGson().fromJson(s, LxAdData.class);
                                    if(data != null && data.getData().size()>0){
                                        lxadNew = data.getData().get(0);
                                        if(lxadNew != null){
                                            Message msg = Message.obtain();
                                            msg.what = 1;
                                            msg.obj = "Rowandjj";
                                            myHandler.sendMessage(msg);
                                        }

                                    }
                                } else {
                                    Toast.makeText(ShangchengActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(progressDialog != null){
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if(progressDialog != null){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(ShangchengActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ad_type", "5");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        getRequestQueue().add(request);
    }
    void getAdTehui() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.GET_AD_LIST_TYPE_LISTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code = jo.getString("code");
                                if (Integer.parseInt(code) == 200) {
                                    LxAdData data = getGson().fromJson(s, LxAdData.class);
                                    if(data != null && data.getData().size()>0){
                                        lxadTehui = data.getData().get(0);
                                        if(lxadNew != null){
                                            Message msg = Message.obtain();
                                            msg.what = 2;
                                            msg.obj = "Rowandjj";
                                            myHandler.sendMessage(msg);
                                        }
                                    }
                                } else {
                                    Toast.makeText(ShangchengActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if(progressDialog != null){
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if(progressDialog != null){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(ShangchengActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ad_type", "6");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        getRequestQueue().add(request);
    }


}
