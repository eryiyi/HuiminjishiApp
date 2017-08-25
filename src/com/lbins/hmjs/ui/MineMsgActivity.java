package com.lbins.hmjs.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lbins.hmjs.R;
import com.lbins.hmjs.base.BaseActivity;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.dao.*;
import com.lbins.hmjs.data.MsgCountData;
import com.lbins.hmjs.module.MsgCount;
import com.lbins.hmjs.util.StringUtil;
import com.lbins.hmjs.widget.CustomProgressDialog;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/8/30.
 */
public class MineMsgActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private MsgCount msgCount;

    private TextView one_number;
    private TextView three_number;

    private TextView one_msg;
    private TextView three_msg;

    private TextView one_dateline;
    private TextView three_dateline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_msg_activity);
        registerBoradcastReceiver();
        initView();
        progressDialog = new CustomProgressDialog(MineMsgActivity.this, "请稍后...",R.anim.custom_dialog_frame);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        getData();
    }

    private void initView() {
        this.findViewById(R.id.back).setOnClickListener(this);
        this.findViewById(R.id.btn_right).setVisibility(View.GONE);
        title = (TextView) this.findViewById(R.id.title);
        title.setText("系统通知");

        this.findViewById(R.id.relate_one).setOnClickListener(this);
        this.findViewById(R.id.relate_three).setOnClickListener(this);

        one_number = (TextView) this.findViewById(R.id.one_number);
        three_number = (TextView) this.findViewById(R.id.three_number);

        one_msg = (TextView) this.findViewById(R.id.one_msg);
        three_msg = (TextView) this.findViewById(R.id.three_msg);

        one_dateline = (TextView) this.findViewById(R.id.one_dateline);
        three_dateline = (TextView) this.findViewById(R.id.three_dateline);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.relate_one:
            {
                //系统消息
                Intent intent = new Intent(MineMsgActivity.this, MessagesActivity.class);
                startActivity(intent);
            }
                break;
            case R.id.relate_three:
            {
                //活动公告
                Intent intent = new Intent(MineMsgActivity.this, NoticesActivity.class);
                startActivity(intent);
            }
                break;

        }
    }

    private void getData() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appMsgAllList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    MsgCountData data = getGson().fromJson(s, MsgCountData.class);
                                    if(data != null){
                                        msgCount = data.getData();
                                        if(msgCount != null){
                                            initData();
                                        }
                                    }
                                }else {
                                    Toast.makeText(MineMsgActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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

    void initData(){
        if(msgCount != null){
            //系统消息
            List<HappyHandMessage> list1 = msgCount.getList1();
            if(list1 != null){
                for(HappyHandMessage happyHandMessage:list1){
                    HappyHandMessage tmpT =DBHelper.getInstance(MineMsgActivity.this).getHappyHandMessageById(happyHandMessage.getMsgid());
                    if(tmpT != null){
                        //说明数据库里有该条记录
                    }else{
                        //说明数据库里没有该记录
                        DBHelper.getInstance(MineMsgActivity.this).saveHappyHandMessage(happyHandMessage);
                    }
                }
                if(list1.size()>0){
                    HappyHandMessage happyHandMessage = list1.get(0);
                    if(happyHandMessage != null){
                        one_msg.setText(happyHandMessage.getTitle());
                        one_dateline.setText(happyHandMessage.getDateline());
                    }
                }
            }

            //查询未读的系统消息
            List<HappyHandMessage> lists1 = DBHelper.getInstance(MineMsgActivity.this).getHappyHandMessageQuery("0");
            if(lists1 != null){
                if(lists1.size() > 0){
                    one_number.setVisibility(View.VISIBLE);
                    one_number.setText(String.valueOf(lists1.size()));
                }else {
                    one_number.setVisibility(View.GONE);
                }
            }else{
                one_number.setVisibility(View.GONE);
            }

            //活动公告
            List<HappyHandNotice> list3 = msgCount.getList3();
            if(list3 != null){
                for(HappyHandNotice happyHandNotice:list3){
                    HappyHandNotice tmpT =DBHelper.getInstance(MineMsgActivity.this).getHappyHandNoticeById(happyHandNotice.getNoticeid());
                    if(tmpT != null){
                        //说明数据库里有该条记录
                    }else{
                        //说明数据库里没有该记录
                        DBHelper.getInstance(MineMsgActivity.this).saveHappyHandNotice(happyHandNotice);
                    }
                }
                if(list3.size()>0){
                    HappyHandNotice happyHandNotice = list3.get(0);
                    if(happyHandNotice != null){
                        three_msg.setText(happyHandNotice.getTitle());
                        three_dateline.setText(happyHandNotice.getDateline());
                    }
                }
            }
            List<HappyHandNotice> lists3 = DBHelper.getInstance(MineMsgActivity.this).getHappyHandNoticeQuery("0");
            if(lists3 != null){
                if(lists3.size() > 0){
                    three_number.setVisibility(View.VISIBLE);
                    three_number.setText(String.valueOf(lists3.size()));
                }else {
                    three_number.setVisibility(View.GONE);
                }
            }else{
                three_number.setVisibility(View.GONE);
            }

        }
    }

    //广播接收动作
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("update_message_success")) {
                initData();
            }
        }
    };

    //注册广播
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("update_message_success");
        myIntentFilter.addAction("update_jwdx_success");
        myIntentFilter.addAction("update_jwdx_refuse");
        //注册广播
       registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

}
