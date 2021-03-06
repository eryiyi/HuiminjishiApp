package com.lbins.hmjs.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.lbins.hmjs.MeetLoveApplication;
import com.lbins.hmjs.R;
import com.lbins.hmjs.adapter.AnimateFirstDisplayListener;
import com.lbins.hmjs.adapter.ItemEmpGroupsAdapter;
import com.lbins.hmjs.base.BaseActivity;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.chat.ui.ChatActivity;
import com.lbins.hmjs.dao.HappyHandGroup;
import com.lbins.hmjs.data.EmpGroupManagerData;
import com.lbins.hmjs.data.EmpGroupsData;
import com.lbins.hmjs.data.HappyHandGroupDataSingle;
import com.lbins.hmjs.module.EmpGroupManager;
import com.lbins.hmjs.module.EmpGroups;
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
public class GroupsInfoActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_EXIT = 1;
    private TextView title;
    private TextView selectAllEmp;
    private ImageView btn_right;
    private String groupId;
    private EMGroup group;

    private PictureGridview gridview;
    List<EmpGroups> lists = new ArrayList<EmpGroups>();
    List<EmpGroups> listsAll = new ArrayList<EmpGroups>();
    private ItemEmpGroupsAdapter adapter;

    private ImageView cover;
    private TextView titleName;
    private TextView content;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

    HappyHandGroup happyHandGroup;

    private Button btn_exit_grp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBoradcastReceiver();
        groupId = getIntent().getStringExtra("groupId");
        group = EMClient.getInstance().groupManager().getGroup(groupId);
        if(group == null){
            finish();
            return;
        }
        setContentView(R.layout.groups_info_activity);
        initView();
        progressDialog = new CustomProgressDialog(GroupsInfoActivity.this, "请稍后",R.anim.custom_dialog_frame);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        getData();
        getGroupsById();
        getGroupsManager();
    }

    //广播接收动作
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("delete_group_member")) {
                getData();
            }
        }
    };

    //注册广播
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("delete_group_member");
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }


    private void initView() {
        this.findViewById(R.id.back).setOnClickListener(this);
        title = (TextView) this.findViewById(R.id.title);
        selectAllEmp = (TextView) this.findViewById(R.id.selectAllEmp);
        btn_right = (ImageView) this.findViewById(R.id.btn_right);
        btn_right.setVisibility(View.VISIBLE);
        btn_right.setOnClickListener(this);
        selectAllEmp.setOnClickListener(this);
        title.setText("聊天信息");
        gridview = (PictureGridview) this.findViewById(R.id.gridview);
        adapter = new ItemEmpGroupsAdapter(lists, GroupsInfoActivity.this);
        gridview.setAdapter(adapter);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));

        titleName = (TextView) this.findViewById(R.id.titleName);
        cover = (ImageView) this.findViewById(R.id.cover);
        content = (TextView) this.findViewById(R.id.content);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(lists.size()>position){
                    EmpGroups empGroups = lists.get(position);
                    if(empGroups != null){
                        if(!getGson().fromJson(getSp().getString("empid",""), String.class).equals(empGroups.getEmpid())){
                            Intent intent = new Intent(GroupsInfoActivity.this, ProfileEmpActivity.class);
                            intent.putExtra("empid", empGroups.getEmpid());
                            startActivity(intent);
                        }
                    }
                }
            }
        });
        btn_exit_grp = (Button) this.findViewById(R.id.btn_exit_grp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.btn_right:
            {
                Intent intent = new Intent(GroupsInfoActivity.this, GroupSetSetActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }
                break;
            case R.id.selectAllEmp:
            {
                //查看全部群成员
                Intent intent = new Intent(GroupsInfoActivity.this, GroupMembersActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }
                break;
        }
    }

    public void exitGroup(View view){
        String st2 = getResources().getString(R.string.is_quit_the_group_chat);
        progressDialog.setMessage(st2);
        progressDialog.show();
        exitGrop();
    }
    /**
     * 退出群组
     *
     */
    private void exitGrop() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    EMClient.getInstance().groupManager().leaveGroup(groupId);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            setResult(RESULT_OK);
                            //退出群之后，删除数据库信息
                            deleteById();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.Exit_the_group_chat_failure) + " " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void deleteById() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appDeleteGroupsById,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    finish();
                                    if(ChatActivity.activityInstance != null){
                                        ChatActivity.activityInstance.finish();
                                    }
                                } else {
                                    Toast.makeText(GroupsInfoActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(GroupsInfoActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(GroupsInfoActivity.this, R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("empid", getGson().fromJson(getSp().getString("empid",""), String.class));
                params.put("groupid", groupId);
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

    private void getData() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appEmpByGroupId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    EmpGroupsData data = getGson().fromJson(s, EmpGroupsData.class);
                                    if(data != null){
                                        lists.clear();
                                        listsAll.clear();
                                        listsAll.addAll(data.getData());
                                        for(int i=0;i<(listsAll.size()<8?listsAll.size():8);i++){
                                            lists.add(listsAll.get(i));
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                }else {
                                    Toast.makeText(GroupsInfoActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
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
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("groupid", groupId);
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


    private void getGroupsById() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appGroupsById,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    HappyHandGroupDataSingle data = getGson().fromJson(s, HappyHandGroupDataSingle.class);
                                    if(data != null){
                                        happyHandGroup  = data.getData();
                                        if(happyHandGroup != null){
                                            titleName.setText(happyHandGroup.getTitle());
                                            content.setText(happyHandGroup.getContent());
                                            imageLoader.displayImage(happyHandGroup.getPic(), cover, MeetLoveApplication.txOptions, animateFirstListener);
                                        }
                                    }
                                }else {
                                    Toast.makeText(GroupsInfoActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
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
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("groupid", groupId);
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


    private List<EmpGroupManager> list = new ArrayList<>();
    private void getGroupsManager() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appGroupsManager,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    EmpGroupManagerData data = getGson().fromJson(s, EmpGroupManagerData.class);
                                    if(data != null){
                                        list.clear();
                                        list.addAll(data.getData());
                                        if(list.size()>0){
                                            EmpGroupManager empGroupManager = list.get(0);
                                            if(empGroupManager != null && getGson().fromJson(getSp().getString("empid", ""), String.class).equals(empGroupManager.getEmpid())){
                                                btn_exit_grp.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                }else {
                                    Toast.makeText(GroupsInfoActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
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
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("groupid", groupId);
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

