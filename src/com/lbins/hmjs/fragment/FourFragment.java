package com.lbins.hmjs.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lbins.hmjs.MainActivity;
import com.lbins.hmjs.MeetLoveApplication;
import com.lbins.hmjs.R;
import com.lbins.hmjs.adapter.AnimateFirstDisplayListener;
import com.lbins.hmjs.adapter.ItemPicAdapter;
import com.lbins.hmjs.base.BaseFragment;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.data.HappyHandPhotoData;
import com.lbins.hmjs.module.HappyHandPhoto;
import com.lbins.hmjs.ui.*;
import com.lbins.hmjs.util.StringUtil;
import com.lbins.hmjs.widget.CustomProgressDialog;
import com.lbins.hmjs.widget.PictureGridview;
import com.loopj.android.http.AsyncHttpClient;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/7/1.
 * 推荐
 */
public class FourFragment extends BaseFragment implements View.OnClickListener  {
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

    private String pics = "";
    private static final File PHOTO_CACHE_DIR = new File(Environment.getExternalStorageDirectory() + "/meetlove/PhotoCache");

    AsyncHttpClient client = new AsyncHttpClient();


    private View view;
    private Resources res;
    private ImageView cover;
    private TextView nickname;
    private TextView is_tuijian;
    private TextView is_state;
    private TextView sign;
    private ImageView vip_1;
    private TextView vip_2;
    private ImageView vip_3;
    private TextView vip_4;
    private TextView age;
    private TextView heightl;
    private TextView address;

    private PictureGridview gridview;
    private ItemPicAdapter adapterGrid;
    private List<String> picLists = new ArrayList<String>();

    private ImageView btn_right;

    private TextView txt_pic;

    private UMShareListener mShareListener;
    private ShareAction mShareAction;

    private TextView notice_number;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.four_fragment, null);
        res = getActivity().getResources();
        registerBoradcastReceiver();
        mShareListener = new CustomShareListener(getActivity());
        initView();
        progressDialog = new CustomProgressDialog(getActivity(), "请稍后...",R.anim.custom_dialog_frame);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        initData();
        getPhotos();
        return view;
    }

    private void initData() {
        if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("nickname", ""), String.class))){
            nickname.setText(getGson().fromJson(getSp().getString("nickname", ""), String.class));
        }
        if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("cover", ""), String.class))){
            imageLoader.displayImage(getGson().fromJson(getSp().getString("cover", ""), String.class), cover, MeetLoveApplication.txOptions, animateFirstListener);
        }

        if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("cityName", ""), String.class))){
            address.setText(getGson().fromJson(getSp().getString("cityName", ""), String.class));
        }


        if("0".equals(getGson().fromJson(getSp().getString("rolestate", ""), String.class))){
            //技师
            is_state.setText("技师");
            if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("yqnum", ""), String.class))){
                sign.setText("邀请码：" + getGson().fromJson(getSp().getString("yqnum", ""), String.class));
            }
        }else{
            is_state.setText("会员");
            if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("sign", ""), String.class))){
                sign.setText(getGson().fromJson(getSp().getString("sign", ""), String.class));
            }
        }
        if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("rzstate2", ""), String.class))){
            if("1".equals(getGson().fromJson(getSp().getString("rzstate2", ""), String.class))){
                vip_1.setImageDrawable(res.getDrawable(R.drawable.icon_verify_id_enabled));
                vip_2.setTextColor(res.getColor(R.color.main_color));
                vip_3.setImageDrawable(res.getDrawable(R.drawable.icon_verify_honesty_enabled));
                vip_4.setTextColor(res.getColor(R.color.main_color));
            }else {
                vip_1.setImageDrawable(res.getDrawable(R.drawable.icon_verify_id_disable));
                vip_2.setTextColor(res.getColor(R.color.textColortwo));
                vip_3.setImageDrawable(res.getDrawable(R.drawable.icon_verify_honesty_disable));
                vip_4.setTextColor(res.getColor(R.color.textColortwo));
            }
        }else {
            vip_1.setImageDrawable(res.getDrawable(R.drawable.icon_verify_id_disable));
            vip_2.setTextColor(res.getColor(R.color.textColortwo));
            vip_3.setImageDrawable(res.getDrawable(R.drawable.icon_verify_honesty_disable));
            vip_4.setTextColor(res.getColor(R.color.textColortwo));
        }

        if(MainActivity.friendsCountUnRead == 0){
            notice_number.setVisibility(View.INVISIBLE);
        }else {
            notice_number.setVisibility(View.VISIBLE);
            notice_number.setText(String.valueOf(MainActivity.friendsCountUnRead));
        }
    }

    void initView(){
        view.findViewById(R.id.back).setVisibility(View.GONE);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText("我");
        gridview = (PictureGridview) view.findViewById(R.id.gridview);
        adapterGrid = new ItemPicAdapter(picLists, getActivity());
        gridview.setAdapter(adapterGrid);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PhotosActivity.class);
                intent.putExtra("empid", getGson().fromJson(getSp().getString("empid", ""), String.class));
                startActivity(intent);
            }
        });
        cover = (ImageView) view.findViewById(R.id.cover);
        notice_number = (TextView) view.findViewById(R.id.notice_number);
        nickname = (TextView) view.findViewById(R.id.nickname);
        is_tuijian = (TextView) view.findViewById(R.id.is_tuijian);
        is_state = (TextView) view.findViewById(R.id.is_state);
        sign = (TextView) view.findViewById(R.id.sign);
        vip_1 = (ImageView) view.findViewById(R.id.vip_1);
        vip_2 = (TextView) view.findViewById(R.id.vip_2);
        vip_3 = (ImageView) view.findViewById(R.id.vip_3);
        vip_4 = (TextView) view.findViewById(R.id.vip_4);
        age = (TextView) view.findViewById(R.id.age);
        heightl = (TextView) view.findViewById(R.id.heightl);
        address = (TextView) view.findViewById(R.id.address);
        btn_right = (ImageView) view.findViewById(R.id.btn_right);
        btn_right.setImageDrawable(res.getDrawable(R.drawable.icon_navbar_search));

        view.findViewById(R.id.liner_photo).setOnClickListener(this);
        view.findViewById(R.id.liner_rz).setOnClickListener(this);
        view.findViewById(R.id.liner_share).setOnClickListener(this);
        view.findViewById(R.id.liner_set).setOnClickListener(this);
        view.findViewById(R.id.liner_friends).setOnClickListener(this);
        view.findViewById(R.id.liner_package).setOnClickListener(this);
        cover.setOnClickListener(this);
        sign.setOnClickListener(this);
        btn_right.setOnClickListener(this);

        view.findViewById(R.id.top_cover).setOnClickListener(this);
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
            case R.id.liner_photo:
            {
                //相册
                Intent intent = new Intent(getActivity(), PhotosActivity.class);
                intent.putExtra("empid", getGson().fromJson(getSp().getString("empid", ""), String.class));
                startActivity(intent);
            }
            break;

            case R.id.liner_rz:
            {
                //认证
                Intent intent = new Intent(getActivity(), PayEmpRzActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.liner_share:
            {
                //分享
                UMImage image = new UMImage(getActivity(), R.drawable.logo_bordered_256w);
                String title =  "想找靠谱的服务 就来丫丫保健\n";
                String content = "真实信息 靠谱服务";

                 /*无自定按钮的分享面板*/
                mShareAction = new ShareAction(getActivity()).setDisplayList(
                        SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN,  SHARE_MEDIA.WEIXIN_FAVORITE,
                        SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE
                        )
                        .withText(content)
                        .withTitle(title)
                        .withTargetUrl(InternetURL.UPDATE_URL)
                        .withMedia(image)
                        .setCallback(mShareListener);

                ShareBoardConfig config = new ShareBoardConfig();
                config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景
                mShareAction.open(config);
//                new ShareAction(getActivity()).setDisplayList()
//                        .addButton("umeng_sharebutton_custom_wx", "umeng_sharebutton_custom_wx", "icon_share_weixin", "icon_share_weixin")
//                        .addButton("umeng_sharebutton_custom_sina","umeng_sharebutton_custom_sina","icon_share_sina","icon_share_sina")
//                        .setShareboardclickCallback(shareBoardlistener).open();
            }
            break;
            case R.id.liner_set:
            {
                //设置
                Intent intent = new Intent(getActivity(), MineSettingActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.top_cover:
            case R.id.cover:
            {
                //头像点击
                Intent intent =  new Intent(getActivity(), RegUpdateActivity.class);
                intent.putExtra("empid", getGson().fromJson(getSp().getString("empid", ""), String.class));
                startActivity(intent);
            }
                break;
            case R.id.sign:
            {
                //签名
            }
                break;
            case R.id.liner_friends:
            {
                //通讯录
                Intent intent =  new Intent(getActivity(), MineFriendsActivity.class);
                startActivity(intent);
            }
                break;
            case R.id.liner_package:
            {
                //钱包
                Intent intent = new Intent(getActivity() , MinePackageActivity.class);
                startActivity(intent);
            }
                break;
        }
    }

    private ShareBoardlistener shareBoardlistener = new  ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform,SHARE_MEDIA share_media) {
            if (share_media==null){
                if (snsPlatform.mKeyword.equals("umeng_sharebutton_custom_wx")){
                    //微信朋友圈分享
                }
                if (snsPlatform.mKeyword.equals("umeng_sharebutton_custom_sina")){
                    //新浪分享
                }
            }
            else {
                new ShareAction(getActivity()).setPlatform(share_media).setCallback(mShareListener)
                        .withText("多平台分享")
                        .share();
            }
        }
    };




    private static class CustomShareListener implements UMShareListener {

        private WeakReference<MainActivity> mActivity;

        private CustomShareListener(Context context) {
            mActivity = new WeakReference(context);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
//                Toast.makeText(mActivity.get(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform!= SHARE_MEDIA.MORE&&platform!=SHARE_MEDIA.SMS
                        &&platform!=SHARE_MEDIA.EMAIL
                        &&platform!=SHARE_MEDIA.FLICKR
                        &&platform!=SHARE_MEDIA.FOURSQUARE
                        &&platform!=SHARE_MEDIA.TUMBLR
                        &&platform!=SHARE_MEDIA.POCKET
                        &&platform!=SHARE_MEDIA.PINTEREST
                        &&platform!=SHARE_MEDIA.LINKEDIN
                        &&platform!=SHARE_MEDIA.INSTAGRAM
                        &&platform!=SHARE_MEDIA.GOOGLEPLUS
                        &&platform!=SHARE_MEDIA.YNOTE
                        &&platform!=SHARE_MEDIA.EVERNOTE){
//                    Toast.makeText(mActivity.get(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform!= SHARE_MEDIA.MORE&&platform!=SHARE_MEDIA.SMS
                    &&platform!=SHARE_MEDIA.EMAIL
                    &&platform!=SHARE_MEDIA.FLICKR
                    &&platform!=SHARE_MEDIA.FOURSQUARE
                    &&platform!=SHARE_MEDIA.TUMBLR
                    &&platform!=SHARE_MEDIA.POCKET
                    &&platform!=SHARE_MEDIA.PINTEREST
                    &&platform!=SHARE_MEDIA.LINKEDIN
                    &&platform!=SHARE_MEDIA.INSTAGRAM
                    &&platform!=SHARE_MEDIA.GOOGLEPLUS
                    &&platform!=SHARE_MEDIA.YNOTE
                    &&platform!=SHARE_MEDIA.EVERNOTE){
                if (t != null) {
                    Log.d("throw", "throw:" + t.getMessage());
                }
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mShareAction.close();
    }

    private void showMsgDialog() {
        final Dialog picAddDialog = new Dialog(getActivity(), R.style.dialog);
        View picAddInflate = View.inflate(getActivity(), R.layout.msg_dialog, null);
        TextView btn_sure = (TextView) picAddInflate.findViewById(R.id.btn_sure);
        TextView msg = (TextView) picAddInflate.findViewById(R.id.msg);
        msg.setText("请做会员认证");
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PayEmpRzActivity.class);
                startActivity(intent);
                picAddDialog.dismiss();
            }
        });

        //取消
        TextView btn_cancel = (TextView) picAddInflate.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picAddDialog.dismiss();
            }
        });
        picAddDialog.setContentView(picAddInflate);
        picAddDialog.show();
    }


    private void getPhotos() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appPhotos,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    HappyHandPhotoData data = getGson().fromJson(s, HappyHandPhotoData.class);
                                    if(data != null){
                                        HappyHandPhoto happyHandPhoto  = data.getData();
                                        if(happyHandPhoto != null){
                                            String photos = happyHandPhoto.getPhotos();
                                            if(!StringUtil.isNullOrEmpty(photos)){
                                                String[] arras = photos.split(",");
                                                if(arras != null){
                                                    picLists.clear();
                                                    for(int i=0;i<(arras.length>3?3:arras.length);i++){
                                                        if(!StringUtil.isNullOrEmpty(arras[i])){
                                                            picLists.add(arras[i]);
                                                        }
                                                    }
                                                }
                                            }else {
                                                picLists.clear();
                                            }
                                        }else {
                                            picLists.clear();
                                        }
                                    }
                                    adapterGrid.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
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
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("empid", getGson().fromJson(getSp().getString("empid", ""), String.class));
                params.put("size", "3");

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
            if (action.equals("rzstate1_success")) {
                vip_2.setTextColor(res.getColor(R.color.main_color));
            }
            if (action.equals("update_photo_success")) {
                getPhotos();
            }

            if (action.equals("rzstate2_success")) {
                //会员认证了
                vip_1.setImageDrawable(res.getDrawable(R.drawable.icon_verify_id_enabled));
            }

            if(action.equals("update_mine_profile_success")){
                initData();
            }
        }
    };

    //注册广播
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("rzstate2_success");
        myIntentFilter.addAction("update_photo_success");
        myIntentFilter.addAction("update_mine_profile_success");
        //注册广播
        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    public void refresh() {
        if(!handler.hasMessages(MSG_REFRESH)){
            handler.sendEmptyMessage(MSG_REFRESH);
        }
    }

    private final static int MSG_REFRESH = 2;
    protected Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_REFRESH:
                {
                    if(MainActivity.friendsCountUnRead == 0){
                        notice_number.setVisibility(View.INVISIBLE);
                    }else {
                        notice_number.setVisibility(View.VISIBLE);
                        notice_number.setText(String.valueOf(MainActivity.friendsCountUnRead));
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

}
