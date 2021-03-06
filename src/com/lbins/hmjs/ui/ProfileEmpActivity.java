package com.lbins.hmjs.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lbins.hmjs.MeetLoveApplication;
import com.lbins.hmjs.R;
import com.lbins.hmjs.adapter.AnimateFirstDisplayListener;
import com.lbins.hmjs.adapter.ItemPicAdapter;
import com.lbins.hmjs.base.BaseActivity;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.chat.Constant;
import com.lbins.hmjs.chat.ui.ChatActivity;
import com.lbins.hmjs.dao.DBHelper;
import com.lbins.hmjs.dao.Emp;
import com.lbins.hmjs.dao.Friends;
import com.lbins.hmjs.data.EmpData;
import com.lbins.hmjs.data.FriendsData;
import com.lbins.hmjs.data.HappyHandPhotoData;
import com.lbins.hmjs.module.HappyHandPhoto;
import com.lbins.hmjs.util.StringUtil;
import com.lbins.hmjs.widget.CustomProgressDialog;
import com.lbins.hmjs.widget.PictureGridview;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/8/30.
 */
public class ProfileEmpActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;

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

    private String empid;
    private Emp emp;
    private int isFriends = 0;//是否是好友   默认0否 1是
    private Button btn_login;//按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_emp_activity);
        empid = getIntent().getExtras().getString("empid");
        res = getResources();
        initView();
        progressDialog = new CustomProgressDialog(ProfileEmpActivity.this, "请稍后...",R.anim.custom_dialog_frame);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        getEmpById();
        getPhotos();
        //判断我和他之间的关系
        getFriends();
    }

    private void getEmpById() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appEmpByEmpId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    EmpData data = getGson().fromJson(s, EmpData.class);
                                    if(data != null){
                                        emp = data.getData();
                                        if(emp != null){
                                            initData();
                                            DBHelper.getInstance(ProfileEmpActivity.this).saveEmp(emp);
                                        }
                                    }
                                }else {
                                    Toast.makeText(ProfileEmpActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("empid", empid);
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

    private void initView() {
        this.findViewById(R.id.back).setOnClickListener(this);
        this.findViewById(R.id.btn_right).setVisibility(View.VISIBLE);
        title = (TextView) this.findViewById(R.id.title);
        title.setText("会员资料");

        gridview = (PictureGridview) this.findViewById(R.id.gridview);
        adapterGrid = new ItemPicAdapter(picLists, ProfileEmpActivity.this);
        gridview.setAdapter(adapterGrid);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProfileEmpActivity.this, PhotosActivity.class);
                intent.putExtra("empid", empid);
                startActivity(intent);
            }
        });

        cover = (ImageView) this.findViewById(R.id.cover);
        nickname = (TextView) this.findViewById(R.id.nickname);
        is_tuijian = (TextView) this.findViewById(R.id.is_tuijian);
        is_state = (TextView) this.findViewById(R.id.is_state);
        sign = (TextView) this.findViewById(R.id.sign);
        vip_1 = (ImageView) this.findViewById(R.id.vip_1);
        vip_2 = (TextView) this.findViewById(R.id.vip_2);
        vip_3 = (ImageView) this.findViewById(R.id.vip_3);
        vip_4 = (TextView) this.findViewById(R.id.vip_4);
        age = (TextView) this.findViewById(R.id.age);
        heightl = (TextView) this.findViewById(R.id.heightl);
        address = (TextView) this.findViewById(R.id.address);

        this.findViewById(R.id.liner_photo).setOnClickListener(this);
        this.findViewById(R.id.btn_right).setOnClickListener(this);
        btn_login = (Button) this.findViewById(R.id.btn_login);

        this.findViewById(R.id.top_cover).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.liner_photo:
            {
                //相册
                Intent intent = new Intent(ProfileEmpActivity.this, PhotosActivity.class);
                intent.putExtra("empid", empid);
                startActivity(intent);
            }
                break;
            case R.id.btn_right:
            {
                Intent intent = new Intent(ProfileEmpActivity.this, ProfileSetActivity.class);
                intent.putExtra("empid", empid);
                startActivity(intent);
            }
                break;
        }
    }

    public void sendMsg(View view){
        if( "1".equals(getGson().fromJson(getSp().getString("rzstate2", ""), String.class))){
            Intent intent = new Intent(ProfileEmpActivity.this, ChatActivity.class);
            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
            intent.putExtra(Constant.EXTRA_USER_ID, empid);
            startActivity(intent);
        }else {
            //未进行身份认证
            showMsgDialog();
        }
    }

    public void AddToFriends(View view){
        if( "1".equals(getGson().fromJson(getSp().getString("rzstate2", ""), String.class))){
            //进行身份认证了 可以添加好友申请了
            if(isFriends == 0){
                showFriendsDialog();
            }else if(isFriends == 1){
                //已经是好友  发消息
//                Intent intent = new Intent(ProfileEmpActivity.this, ChatActivity.class);
//                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
//                intent.putExtra(Constant.EXTRA_USER_ID, empid);
//                startActivity(intent);
                showDialogMsg("对方已经是您的好友！");
            }
        }else {
            //未进行身份认证
            showMsgDialog();
        }
    }


    private void showDialogMsg(String msgStr) {
        final Dialog picAddDialog = new Dialog(ProfileEmpActivity.this, R.style.dialog);
        View picAddInflate = View.inflate(this, R.layout.msg_msg_dialog, null);
        final TextView msg = (TextView) picAddInflate.findViewById(R.id.msg);
        msg.setText(msgStr);
        TextView btn_sure = (TextView) picAddInflate.findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picAddDialog.dismiss();
            }
        });
        picAddDialog.setContentView(picAddInflate);
        picAddDialog.show();
    }

    private void showFriendsDialog() {
        final Dialog picAddDialog = new Dialog(ProfileEmpActivity.this, R.style.dialog);
        View picAddInflate = View.inflate(this, R.layout.msg_friends_dialog, null);
        final EditText msg = (EditText) picAddInflate.findViewById(R.id.msg);
        TextView btn_sure = (TextView) picAddInflate.findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringUtil.isNullOrEmpty(msg.getText().toString())){
                    showMsg(ProfileEmpActivity.this, "请输入申请理由！");
                }else {
                    //隐藏键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(msg.getApplicationWindowToken(), 0);
                    addToFriends(msg.getText().toString());
                    picAddDialog.dismiss();
                }

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

    private void showMsgDialog() {
        final Dialog picAddDialog = new Dialog(ProfileEmpActivity.this, R.style.dialog);
        View picAddInflate = View.inflate(this, R.layout.msg_dialog, null);
        TextView msg = (TextView) picAddInflate.findViewById(R.id.msg);
        msg.setText("请做会员认证");
        TextView btn_sure = (TextView) picAddInflate.findViewById(R.id.btn_sure);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileEmpActivity.this, PayEmpRzActivity.class);
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

    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private void initData() {

        nickname.setText(emp.getNickname());
        if(!StringUtil.isNullOrEmpty(emp.getSign())){
            sign.setText(emp.getSign());
        }
        if(!StringUtil.isNullOrEmpty(emp.getCover())){
            imageLoader.displayImage(emp.getCover(), cover, MeetLoveApplication.options, animateFirstListener);
        }
        if("0".equals(emp.getRolestate())){
            //技师
            is_state.setText("技师");
        }else{
            is_state.setText("会员");
        }
        if(!StringUtil.isNullOrEmpty(emp.getRzstate2())){
            if("1".equals(emp.getRzstate2())){
                vip_2.setTextColor(res.getColor(R.color.main_color));
                vip_1.setImageDrawable(res.getDrawable(R.drawable.icon_verify_id_enabled));
            }else {
                vip_2.setTextColor(res.getColor(R.color.textColortwo));
                vip_1.setImageDrawable(res.getDrawable(R.drawable.icon_verify_id_disable));
            }
        }else {
            vip_1.setImageDrawable(res.getDrawable(R.drawable.icon_verify_id_disable));
            vip_2.setTextColor(res.getColor(R.color.textColortwo));
        }
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
                                                adapterGrid.notifyDataSetChanged();
                                            }
                                        }
                                    }
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
                params.put("empid", empid);
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

    private void addToFriends(final String content) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appSaveFriends,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                   showMsg(ProfileEmpActivity.this, "申请成功，请等待对方确认！");
                                }else{
                                    showMsg(ProfileEmpActivity.this, jo.getString("message"));
                                    getFriends();
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
                params.put("empid1", getGson().fromJson(getSp().getString("empid", ""), String.class));
                params.put("empid2", empid);
                params.put("applytitle", content);
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

    List<Friends> listsIS = new ArrayList<>();

    private void getFriends() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appFriends,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    FriendsData data = getGson().fromJson(s, FriendsData.class);
                                    if(data != null){
                                        listsIS.clear();
                                        listsIS.addAll(data.getData());
                                        if(listsIS != null && listsIS.size()>0){
                                            //是好友
                                            isFriends = 1;
                                            btn_login.setText("已加好友");
                                        }else{
                                            isFriends = 0;
                                        }
                                    }
                                }else {
                                    Toast.makeText(ProfileEmpActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("is_check", "1");
                params.put("empid1", getGson().fromJson(getSp().getString("empid", ""), String.class));
                params.put("empid2", empid);
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
