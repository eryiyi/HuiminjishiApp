package com.lbins.hmjs.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hyphenate.chat.EMClient;
import com.lbins.hmjs.R;
import com.lbins.hmjs.base.BaseActivity;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.dao.Emp;
import com.lbins.hmjs.dao.Friends;
import com.lbins.hmjs.dao.HappyHandJw;
import com.lbins.hmjs.data.EmpData;
import com.lbins.hmjs.data.FriendsData;
import com.lbins.hmjs.data.HappyHandJwDatas;
import com.lbins.hmjs.util.StringUtil;
import com.lbins.hmjs.widget.CustomProgressDialog;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/8/30.
 */
public class ProfileSetActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private Emp emp ;
    private String empid ;

    private String is_friends = "";//1是好友
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_set_activity);
        empid = getIntent().getExtras().getString("empid");
        initView();
        progressDialog = new CustomProgressDialog(ProfileSetActivity.this, "请稍后...",R.anim.custom_dialog_frame);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        getEmpById();
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
                                    }
                                }else {
                                    Toast.makeText(ProfileSetActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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
                                            is_friends = "1";
                                        }else{
                                            is_friends = "0";
                                        }
                                    }
                                }else {
                                    Toast.makeText(ProfileSetActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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

    private void initView() {
        this.findViewById(R.id.back).setOnClickListener(this);
        this.findViewById(R.id.btn_right).setVisibility(View.GONE);
        title = (TextView) this.findViewById(R.id.title);
        title.setText("详细设置");

        this.findViewById(R.id.liner1).setOnClickListener(this);
        this.findViewById(R.id.liner3).setOnClickListener(this);
        this.findViewById(R.id.liner4).setOnClickListener(this);
        this.findViewById(R.id.liner5).setOnClickListener(this);
        this.findViewById(R.id.liner9).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;

            case R.id.liner3:
            {
                //黑名单
                    //删除好友
                    progressDialog = new CustomProgressDialog(ProfileSetActivity.this, "正在处理",R.anim.custom_dialog_frame);
                    progressDialog.setCancelable(true);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                    deleteFriendsBlack();
            }
                break;
//            case R.id.liner4:
//            {
//                //删除好友
//                if("1".equals(is_friends)){
//                    //删除好友
//                    showVersionDialog();
//                }else{
//                    showMsg(ProfileSetActivity.this ,"对方不是您的好友，无法删除！");
//                }
//            }
//            break;
            case R.id.liner5:
            {
                //投诉
                Intent intent = new Intent(ProfileSetActivity.this, AddReportActivity.class);
                intent.putExtra("name" , emp.getNickname());
                intent.putExtra("empid" , emp.getEmpid());
                intent.putExtra("empmobileStr" , emp.getMobile());
                startActivity(intent);
            }
            break;
            case R.id.liner9:
            {
                Intent intent = new Intent(ProfileSetActivity.this, MineBlackListActivity.class);
                startActivity(intent);
            }
                break;
        }
    }





    private void deleteFriendsBlack() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appDeleteFriends,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    showMsg(ProfileSetActivity.this, "对方已经加入黑名单！");
                                    EMClient.getInstance().contactManager().addUserToBlackList(empid,true);
                                    Intent intent1 = new Intent("delete_friends_success");
                                    sendBroadcast(intent1);
                                }else{
                                    showMsg(ProfileSetActivity.this, jo.getString("message"));
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
