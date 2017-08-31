package com.lbins.hmjs.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lbins.hmjs.R;
import com.lbins.hmjs.adapter.OnClickContentItemListener;
import com.lbins.hmjs.adapter.RecordAdapter;
import com.lbins.hmjs.base.BaseActivity;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.data.RecordVOData;
import com.lbins.hmjs.library.PullToRefreshBase;
import com.lbins.hmjs.library.PullToRefreshListView;
import com.lbins.hmjs.module.RecordVO;
import com.lbins.hmjs.util.StringUtil;
import com.lbins.hmjs.widget.CustomProgressDialog;
import com.lbins.hmjs.widget.SelectPhotoPopWindow;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/8/30.
 */
public class MineRecordsActivity extends BaseActivity implements View.OnClickListener,OnClickContentItemListener {
    private TextView title;
    private ImageView btn_right;

    private PullToRefreshListView lstv;
    private RecordAdapter adapter;
    List<RecordVO> lists = new ArrayList<RecordVO>();
    private int pageIndex = 1;
    private static boolean IS_REFRESH = true;
    private ImageView search_null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_records_activity);
        registerBoradcastReceiver();
        initView();
        progressDialog = new CustomProgressDialog(MineRecordsActivity.this, "请稍后...",R.anim.custom_dialog_frame);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        getData();
    }

    private void initView() {
        this.findViewById(R.id.back).setOnClickListener(this);
        this.findViewById(R.id.btn_right).setVisibility(View.VISIBLE);
        title = (TextView) this.findViewById(R.id.title);
        title.setText("朋友圈");
        btn_right = (ImageView) this.findViewById(R.id.btn_right);
        btn_right.setOnClickListener(this);
        btn_right.setImageDrawable(getResources().getDrawable(R.drawable.icon_talk));

        search_null = (ImageView) this.findViewById(R.id.search_null);
        lstv = (PullToRefreshListView) this.findViewById(R.id.lstv);
        adapter = new RecordAdapter(lists, MineRecordsActivity.this);
        lstv.setMode(PullToRefreshBase.Mode.BOTH);
        lstv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(MineRecordsActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                IS_REFRESH = true;
                pageIndex = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(MineRecordsActivity.this, System.currentTimeMillis(),
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
                if(lists.size() > (position-1)){
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.btn_right:
            {
                //发布
                showDialogPhoto();
            }
                break;
        }
    }
    private SelectPhotoPopWindow photosWindow;

    public void showDialogPhoto(){
        photosWindow = new SelectPhotoPopWindow(MineRecordsActivity.this, itemsOnClickPhoto);
        //显示窗口
        setBackgroundAlpha(0.5f);//设置屏幕透明度

        photosWindow.setBackgroundDrawable(new BitmapDrawable());
        photosWindow.setFocusable(true);
        photosWindow.showAtLocation(this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        photosWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) MineRecordsActivity.this).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) MineRecordsActivity.this).getWindow().setAttributes(lp);
    }

    private View.OnClickListener itemsOnClickPhoto = new View.OnClickListener() {
        public void onClick(View v) {
            photosWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_photo: {
                    Intent pic = new Intent(MineRecordsActivity.this, PublishRecordActivity.class);
                    pic.putExtra("SELECT_PHOTOORPIIC", "0");
                    startActivity(pic);
                }
                break;
                case R.id.btn_camera: {
                    Intent photo = new Intent(MineRecordsActivity.this, PublishRecordActivity.class);
                    photo.putExtra("SELECT_PHOTOORPIIC", "1");
                    startActivity(photo);
                }
                break;
                default:
                    break;
            }
        }
    };

    private void getData() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appRecords,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    RecordVOData data = getGson().fromJson(s, RecordVOData.class);
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
                                    Toast.makeText(MineRecordsActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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



    //广播接收动作
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("add_record_success")) {
                IS_REFRESH = true;
                pageIndex = 1;
                getData();
            }
            if (action.equals("add_record_comment_success")) {
                getData();
            }
        }
    };

    //注册广播
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("add_record_success");
        myIntentFilter.addAction("add_record_comment_success");
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    private int tmpInt;
    @Override
    public void onClickContentItem(int position, int flag, Object object) {
        RecordVO info = lists.get(position);
        switch (flag){
            case 1:
            {
                //点击头像和昵称
                Intent intent = new Intent(MineRecordsActivity.this, ProfileEmpActivity.class);
                intent.putExtra("empid", info.getEmpid());
                startActivity(intent);
            }
                break;
            case 2:
            {
                //评论点击
                Intent intent = new Intent(MineRecordsActivity.this, MineRecordsCommentActivity.class);
                intent.putExtra("record_id", info.getRecord_id());
                startActivity(intent);
            }
                break;
            case 3:
            {
                //赞
                tmpInt = position;
                addFavour(info.getRecord_id());
            }
                break;
        }
    }

    private void addFavour(final String record_id) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appRecordFavourSave,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    Toast.makeText(MineRecordsActivity.this, "点赞成功！", Toast.LENGTH_SHORT).show();
                                    lists.get(tmpInt).setFavourNum(lists.get(tmpInt).getFavourNum()+1);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(MineRecordsActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MineRecordsActivity.this, "操作失败，请稍后重试！", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("record_id", record_id);
                params.put("favour_empid", getGson().fromJson(getSp().getString("empid", ""), String.class));
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
