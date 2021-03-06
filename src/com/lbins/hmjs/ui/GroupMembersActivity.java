package com.lbins.hmjs.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lbins.hmjs.R;
import com.lbins.hmjs.adapter.ItemEmpGroupsAdapter;
import com.lbins.hmjs.base.BaseActivity;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.data.EmpGroupsData;
import com.lbins.hmjs.module.EmpGroups;
import com.lbins.hmjs.util.StringUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/8/30.
 */
public class GroupMembersActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private String groupId;
    private GridView gridview;
    List<EmpGroups> listsAll = new ArrayList<EmpGroups>();
    private ItemEmpGroupsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBoradcastReceiver();
        setContentView(R.layout.members_activity);
        groupId = getIntent().getStringExtra("groupId");
        initView();
        getData();
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
        this.findViewById(R.id.btn_right).setVisibility(View.GONE);
        title = (TextView) this.findViewById(R.id.title);
        title.setText("群成员");
        this.findViewById(R.id.back).setOnClickListener(this);

        gridview = (GridView) this.findViewById(R.id.gridview);
        adapter = new ItemEmpGroupsAdapter(listsAll, GroupMembersActivity.this);
        gridview.setAdapter(adapter);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listsAll.size()>position){
                    EmpGroups empGroups = listsAll.get(position);
                    if(empGroups != null){
                        if(!getGson().fromJson(getSp().getString("empid",""), String.class).equals(empGroups.getEmpid())){
                            Intent intent = new Intent(GroupMembersActivity.this, ProfileEmpActivity.class);
                            intent.putExtra("empid", empGroups.getEmpid());
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
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
                                        listsAll.clear();
                                        listsAll.addAll(data.getData());
                                        adapter.notifyDataSetChanged();
                                    }
                                }else {
                                    Toast.makeText(GroupMembersActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                                title.setText("群成员("+listsAll.size()+")");
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
