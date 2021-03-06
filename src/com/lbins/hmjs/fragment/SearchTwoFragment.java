package com.lbins.hmjs.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lbins.hmjs.R;
import com.lbins.hmjs.adapter.ItemLikesAdapter;
import com.lbins.hmjs.base.BaseFragment;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.data.HappyHandLikeData;
import com.lbins.hmjs.module.HappyHandLike;
import com.lbins.hmjs.ui.SearchGroupsActivity;
import com.lbins.hmjs.util.StringUtil;
import com.lbins.hmjs.widget.CustomProgressDialog;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchTwoFragment extends BaseFragment implements View.OnClickListener  {

    private View view;
    private Resources res;

    private EditText keywords;
    private Button btn_login;

    private GridView gridview;
    private ItemLikesAdapter adapterGrid;
    private List<HappyHandLike> lists = new ArrayList<HappyHandLike>();

    private String likeids= "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_two_fragment, null);
        res = getActivity().getResources();

        initView();
        progressDialog = new CustomProgressDialog( getActivity(), "请稍后",R.anim.custom_dialog_frame);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        getDataLikes();

        return view;
    }

    void initView(){
        keywords = (EditText) view.findViewById(R.id.keywords);
        keywords.addTextChangedListener(watcher);
        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        gridview = (GridView) view.findViewById(R.id.gridview);
        adapterGrid = new ItemLikesAdapter(lists, getActivity());
        gridview.setAdapter(adapterGrid);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(lists.size()>position){
                    HappyHandLike happyHandLike = lists.get(position);
                    if(happyHandLike != null){
                        if("0".equals(happyHandLike.getIs_use())){
                            Toast.makeText(getActivity(), "暂不可用!", Toast.LENGTH_SHORT).show();
                        }else{
                            keywords.setText("");
                            if(!StringUtil.isNullOrEmpty(likeids)){
                                //不是空 说明有选中的
                                if(likeids.equals(happyHandLike.getLikeid())){
                                    //说明点中的是同一个
                                    lists.get(position).setIs_use("1");
                                    likeids = "";
                                    adapterGrid.notifyDataSetChanged();
                                    btn_login.setBackgroundResource(R.drawable.btn_big_unactive);
                                    btn_login.setTextColor(getResources().getColor(R.color.textColortwo));
                                }else {
                                    //说明选中的不是同一个
                                    for(HappyHandLike cell:lists){
                                        cell.setIs_use("1");
                                    }
                                    lists.get(position).setIs_use("3");
                                    likeids = lists.get(position).getLikeid();
                                    adapterGrid.notifyDataSetChanged();
                                    btn_login.setBackgroundResource(R.drawable.btn_big_active);
                                    btn_login.setTextColor(getResources().getColor(R.color.white));
                                }

                            }else {
                                //是空的 没选中
                                //只要不是禁用的爱好兴趣 就选中状态
                                lists.get(position).setIs_use("3");
                                likeids = lists.get(position).getLikeid();
                                adapterGrid.notifyDataSetChanged();
                                btn_login.setBackgroundResource(R.drawable.btn_big_active);
                                btn_login.setTextColor(getResources().getColor(R.color.white));
                            }
//                            if("3".equals(happyHandLike.getIs_use())){
//                                lists.get(position).setIs_use("1");
//                                likeids = "";
//                                adapterGrid.notifyDataSetChanged();
//                                btn_login.setBackgroundResource(R.drawable.btn_big_unactive);
//                                btn_login.setTextColor(getResources().getColor(R.color.textColortwo));
//                            }else{
//                                //只要不是禁用的爱好兴趣 就选中状态
//                                lists.get(position).setIs_use("3");
//                                likeids = lists.get(position).getLikeid();
//                                adapterGrid.notifyDataSetChanged();
//                                btn_login.setBackgroundResource(R.drawable.btn_big_active);
//                                btn_login.setTextColor(getResources().getColor(R.color.white));
//                            }
                        }
                    }
                }
            }
        });

        keywords.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }

                    //查找
                    if(!StringUtil.isNullOrEmpty(keywords.getText().toString())){
                        Intent intent = new Intent(getActivity(), SearchGroupsActivity.class);
                        intent.putExtra("keywords", keywords.getText().toString());
                        intent.putExtra("likeids", "");
                        startActivity(intent);
                    }else if(!StringUtil.isNullOrEmpty(likeids)){
                        Intent intent = new Intent(getActivity(), SearchGroupsActivity.class);
                        intent.putExtra("keywords", "");
                        intent.putExtra("likeids", likeids);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getActivity(), "请选择查询条件!", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
                return false;
            }
        });
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            for(int i= 0 ;i<lists.size();i++){
                lists.get(i).setIs_use("1");
            }
            adapterGrid.notifyDataSetChanged();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!StringUtil.isNullOrEmpty(keywords.getText().toString())
                    || !StringUtil.isNullOrEmpty(likeids)
                    )
            {
                btn_login.setBackgroundResource(R.drawable.btn_big_active);
                btn_login.setTextColor(getResources().getColor(R.color.white));
            } else {
                btn_login.setBackgroundResource(R.drawable.btn_big_unactive);
                btn_login.setTextColor(getResources().getColor(R.color.textColortwo));
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
            {
                if(!StringUtil.isNullOrEmpty(keywords.getText().toString())){
                    Intent intent = new Intent(getActivity(), SearchGroupsActivity.class);
                    intent.putExtra("keywords", keywords.getText().toString());
                    intent.putExtra("likeids", "");
                    startActivity(intent);
                }else if(!StringUtil.isNullOrEmpty(likeids)){
                    Intent intent = new Intent(getActivity(), SearchGroupsActivity.class);
                    intent.putExtra("keywords", "");
                    intent.putExtra("likeids", likeids);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "请选择查询条件!", Toast.LENGTH_SHORT).show();
                }

            }
                break;
        }
    }

    private void getDataLikes() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appLikes,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    HappyHandLikeData data = getGson().fromJson(s, HappyHandLikeData.class);
                                    if(data != null){
                                        lists.clear();
                                        lists.addAll(data.getData());
                                        adapterGrid.notifyDataSetChanged();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
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

}
