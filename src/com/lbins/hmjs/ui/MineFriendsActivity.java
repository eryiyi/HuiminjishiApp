package com.lbins.hmjs.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lbins.hmjs.MainActivity;
import com.lbins.hmjs.R;
import com.lbins.hmjs.adapter.ContactAdapter;
import com.lbins.hmjs.base.BaseActivity;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.dao.Friends;
import com.lbins.hmjs.data.FriendsData;
import com.lbins.hmjs.util.PinyinComparator;
import com.lbins.hmjs.util.StringUtil;
import com.lbins.hmjs.widget.CustomProgressDialog;
import com.lbins.hmjs.widget.SideBar;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by zhl on 2016/8/30.
 */
public class MineFriendsActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;

    List<Friends> friendses = new ArrayList<Friends>();

    private ListView lvContact;
    private SideBar indexBar;
    private WindowManager mWindowManager;
    private TextView mDialogText;
    private ContactAdapter adapter;

    private LinearLayout headLiner;
    private RelativeLayout relate_new_friends;
    private TextView new_friends_number;
    private RelativeLayout relate_groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_friends_activity);
        registerBoradcastReceiver();
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        initView();
        progressDialog = new CustomProgressDialog(MineFriendsActivity.this, "请稍后...",R.anim.custom_dialog_frame);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        getFriends();
        refresh();
    }


    public void refresh(){
        if(MainActivity.friendsCountUnRead > 0){
            new_friends_number.setVisibility(View.VISIBLE);
            new_friends_number.setText(String.valueOf(MainActivity.friendsCountUnRead));
        }else {
            new_friends_number.setVisibility(View.INVISIBLE);
        }
    }

    void initView(){
        this.findViewById(R.id.back).setOnClickListener(this);
        this.findViewById(R.id.btn_right).setVisibility(View.GONE);
        title = (TextView) this.findViewById(R.id.title);
        title.setText("通讯录");

        lvContact = (ListView) this.findViewById(R.id.lvContact);
        adapter = new ContactAdapter(MineFriendsActivity.this, friendses);
        headLiner = (LinearLayout) LayoutInflater.from(MineFriendsActivity.this).inflate(R.layout.three_header, null);
        relate_new_friends = (RelativeLayout) headLiner.findViewById(R.id.relate_new_friends);
        new_friends_number = (TextView) headLiner.findViewById(R.id.new_friends_number);


        lvContact.setAdapter(adapter);
        indexBar = (SideBar) this.findViewById(R.id.sideBar);
        indexBar.setListView(lvContact);
        lvContact.addHeaderView(headLiner);

        mDialogText = (TextView) LayoutInflater.from(MineFriendsActivity.this).inflate(
                R.layout.list_position, null);
        mDialogText.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mWindowManager.addView(mDialogText, lp);
        indexBar.setTextView(mDialogText);
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    if(friendses.size() > (position-1)){
                        Friends friends = friendses.get((position-1));
                        if(friends != null){
                            Intent intent = new Intent(MineFriendsActivity.this, ProfileEmpActivity.class);
                            intent.putExtra("empid", friends.getEmpid2());
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        headLiner.findViewById(R.id.relate_new_friends).setOnClickListener(this);
        headLiner.findViewById(R.id.relate_groups).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relate_new_friends:
            {
                //新的朋友申请
                Intent intent = new Intent(MineFriendsActivity.this, FriendsApplyActivity.class);
                startActivity(intent);
            }
            break;
//            case R.id.relate_groups:
//            {
//                //群聊
//                startActivity(new Intent(MineFriendsActivity.this, MineGroupsActivity.class));
//            }
//            break;
            case R.id.btn_right:
            {
                //搜索
                Intent intent = new Intent(MineFriendsActivity.this, SearchActivity.class);
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
                                        friendses.clear();
                                        friendses.addAll(data.getData());
                                        Collections.sort(friendses, new PinyinComparator());
                                        adapter.notifyDataSetChanged();
                                    }
                                }else {
                                    Toast.makeText(MineFriendsActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("is_check", "1");
                params.put("empid1", getGson().fromJson(getSp().getString("empid", ""), String.class));
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


    private void getFriendsCount() {
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
                                        List<Friends> listsFriends = new ArrayList<>();
                                        listsFriends.addAll(data.getData());
                                        int friendsCountUnRead = listsFriends.size();
                                        if(friendsCountUnRead> 0){
                                            new_friends_number.setVisibility(View.VISIBLE);
                                            new_friends_number.setText(String.valueOf(friendsCountUnRead));
                                        }else {
                                            new_friends_number.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                }else {
                                    Toast.makeText(MineFriendsActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("empid2", getGson().fromJson(getSp().getString("empid", ""), String.class));
                params.put("is_check", "0");
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
            if (action.equals("update_contact_success")) {
                getFriends();
                getFriendsCount();
            }
            if (action.equals("delete_friends_success")) {
                //删除了好友
                getFriends();
                getFriendsCount();
            }
        }
    };

    //注册广播
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("update_contact_success");
        myIntentFilter.addAction("delete_friends_success");
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
    
}
