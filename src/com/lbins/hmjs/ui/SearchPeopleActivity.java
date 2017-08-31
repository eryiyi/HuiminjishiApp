package com.lbins.hmjs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lbins.hmjs.R;
import com.lbins.hmjs.adapter.ItemSearchPeopleAdapter;
import com.lbins.hmjs.base.BaseActivity;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.dao.Emp;
import com.lbins.hmjs.data.EmpsData;
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
public class SearchPeopleActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private ListView lstv;
    private ItemSearchPeopleAdapter adapter;
    private List<Emp> lists = new ArrayList<Emp>();
    private ImageView search_null;

    private String keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuijian_people_activity);
        keywords = getIntent().getExtras().getString("keywords");

        initView();
        progressDialog = new CustomProgressDialog(SearchPeopleActivity.this, "请稍后...",R.anim.custom_dialog_frame);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        getData();
    }

    private void initView() {
        this.findViewById(R.id.back).setOnClickListener(this);
        this.findViewById(R.id.btn_right).setVisibility(View.GONE);
        title = (TextView) this.findViewById(R.id.title);
        title.setText("搜索结果");

        search_null = (ImageView) this.findViewById(R.id.search_null);
        search_null.setVisibility(View.GONE);

        lstv = (ListView) this.findViewById(R.id.lstv);
        adapter = new ItemSearchPeopleAdapter(lists, SearchPeopleActivity.this);
        lstv.setAdapter(adapter);
        lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(lists.size()>position){
                    Emp emp = lists.get(position);
                    if(emp != null){
                        Intent intent =  new Intent(SearchPeopleActivity.this, ProfileEmpActivity.class);
                        intent.putExtra("empid", emp.getEmpid());
                        startActivity(intent);
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
                InternetURL.appSearchPeoplesByKeyWords,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    EmpsData data = getGson().fromJson(s, EmpsData.class);
                                    if(data != null){
                                        lists.addAll(data.getData());
                                    }
                                    adapter.notifyDataSetChanged();
                                    if(lists.size()>0){
                                        search_null.setVisibility(View.GONE);
                                        lstv.setVisibility(View.VISIBLE);
                                    }else{
                                        search_null.setVisibility(View.VISIBLE);
                                        lstv.setVisibility(View.GONE);
                                    }
                                }else {
                                    Toast.makeText(SearchPeopleActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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
                if(!StringUtil.isNullOrEmpty(keywords)){
                    params.put("keywords", keywords);
                }
                params.put("rolestate", getGson().fromJson(getSp().getString("rolestate", ""), String.class));
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
