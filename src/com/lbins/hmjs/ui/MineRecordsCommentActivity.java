package com.lbins.hmjs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lbins.hmjs.R;
import com.lbins.hmjs.adapter.ItemRecordCommentAdapter;
import com.lbins.hmjs.adapter.OnClickContentItemListener;
import com.lbins.hmjs.base.BaseActivity;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.data.RecordCommentData;
import com.lbins.hmjs.library.PullToRefreshBase;
import com.lbins.hmjs.library.PullToRefreshListView;
import com.lbins.hmjs.module.RecordComment;
import com.lbins.hmjs.util.StringUtil;
import com.lbins.hmjs.widget.CustomProgressDialog;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/8/30.
 * 动态评论
 */
public class MineRecordsCommentActivity extends BaseActivity implements View.OnClickListener,OnClickContentItemListener {
    private TextView title;
    private ImageView btn_right;

    private PullToRefreshListView lstv;
    private ItemRecordCommentAdapter adapter;
    List<RecordComment> lists = new ArrayList<RecordComment>();
    private int pageIndex = 1;
    private static boolean IS_REFRESH = true;
    private ImageView search_null;

    private String record_id;

    private EditText content;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_records_comment_activity);
        record_id = getIntent().getExtras().getString("record_id");
        initView();
        progressDialog = new CustomProgressDialog(MineRecordsCommentActivity.this, "请稍后...",R.anim.custom_dialog_frame);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        getData();
    }

    private void initView() {
        this.findViewById(R.id.back).setOnClickListener(this);
        this.findViewById(R.id.btn_right).setVisibility(View.GONE);
        title = (TextView) this.findViewById(R.id.title);
        title.setText("动态评论");

        search_null = (ImageView) this.findViewById(R.id.search_null);
        lstv = (PullToRefreshListView) this.findViewById(R.id.lstv);
        adapter = new ItemRecordCommentAdapter(lists, MineRecordsCommentActivity.this);
        lstv.setMode(PullToRefreshBase.Mode.BOTH);
        lstv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(MineRecordsCommentActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                IS_REFRESH = true;
                pageIndex = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(MineRecordsCommentActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                IS_REFRESH = false;
                pageIndex++;
                getData();
            }
        });
        lstv.setAdapter(adapter);
        lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (lists.size() > (position - 1)) {
//                    RecordVO emp = lists.get(position-1);
//                    if (emp !=null) {
//                        Intent intent = new Intent(MineRecordsActivity.this , ProfileEmpActivity.class);
//                        intent.putExtra("empid", emp.getEmpid());
//                        startActivity(intent);
//                    }
                }
            }
        });
        adapter.setOnClickContentItemListener(this);
        content = (EditText) this.findViewById(R.id.content);
        btn = (Button) this.findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.btn:
            {
                if(StringUtil.isNullOrEmpty(content.getText().toString())){
                    showMsg(MineRecordsCommentActivity.this, "请输入评论内容！");
                    return;
                }
                progressDialog = new CustomProgressDialog(MineRecordsCommentActivity.this, "请稍后...",R.anim.custom_dialog_frame);
                progressDialog.setCancelable(true);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                publishAll();
            }
                break;
        }
    }

    private void getData() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appRecordsComment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    RecordCommentData data = getGson().fromJson(s, RecordCommentData.class);
                                    if (IS_REFRESH) {
                                        lists.clear();
                                    }
                                    lists.addAll(data.getData());
                                    lstv.onRefreshComplete();
                                    adapter.notifyDataSetChanged();
                                    if(lists.size()>0){
                                        search_null.setVisibility(View.GONE);
                                    }else{
                                        search_null.setVisibility(View.VISIBLE);
                                    }
                                }else {
                                    Toast.makeText(MineRecordsCommentActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("page", String.valueOf(pageIndex));
                params.put("record_id", record_id);
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
    public void onClickContentItem(int position, int flag, Object object) {
        RecordComment info = lists.get(position);
        switch (flag){
            case 1:
            {
                //点击头像和昵称
                Intent intent = new Intent(MineRecordsCommentActivity.this, ProfileEmpActivity.class);
                intent.putExtra("empid", info.getComment_empid());
                startActivity(intent);
            }
                break;
        }
    }


    private void publishAll() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appRecordCommentSave,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    Toast.makeText(MineRecordsCommentActivity.this, "评论成功！", Toast.LENGTH_SHORT).show();
                                    IS_REFRESH = true;
                                    pageIndex = 1;
                                    getData();
                                    content.setText("");
                                    Intent intent1 = new Intent("add_record_comment_success");
                                    sendBroadcast(intent1);
                                } else {
                                    Toast.makeText(MineRecordsCommentActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(MineRecordsCommentActivity.this, "添加数据失败，请稍后重试！", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("record_id", record_id);
                params.put("comment_cont", content.getText().toString());
                params.put("comment_empid", getGson().fromJson(getSp().getString("empid", ""), String.class));
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
