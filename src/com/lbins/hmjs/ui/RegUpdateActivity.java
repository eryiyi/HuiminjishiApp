package com.lbins.hmjs.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.lbins.hmjs.MeetLoveApplication;
import com.lbins.hmjs.R;
import com.lbins.hmjs.adapter.AnimateFirstDisplayListener;
import com.lbins.hmjs.baidu.Utils;
import com.lbins.hmjs.base.BaseActivity;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.dao.Emp;
import com.lbins.hmjs.data.EmpData;
import com.lbins.hmjs.module.City;
import com.lbins.hmjs.module.Province;
import com.lbins.hmjs.util.CompressPhotoUtil;
import com.lbins.hmjs.util.StringUtil;
import com.lbins.hmjs.widget.CustomProgressDialog;
import com.lbins.hmjs.widget.SelectPhotoPopWindow;
import com.lbins.hmjs.widget.SelectPopQuiteWindow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhl on 2016/8/30.
 */
public class RegUpdateActivity extends BaseActivity implements View.OnClickListener {
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

    private String pics = "";
    private static final File PHOTO_CACHE_DIR = new File(Environment.getExternalStorageDirectory() + "/meetlove/PhotoCache");

    AsyncHttpClient client = new AsyncHttpClient();

    private TextView title;

    private ImageView cover;
    private EditText nickname;
    private TextView address;

    private Button btn_login;

    private String empid;//注册成功返回的会员ID

    private String provinceid = "";
    private String cityid = "";

    private TextView txt_pic;
    private TextView mobile;

    private EditText sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_update_activity);
        empid = getIntent().getExtras().getString("empid");
        initView();
        initData();
        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                Utils.getMetaValue(RegUpdateActivity.this, "api_key"));
    }

    //实例化
    private void initData() {
        if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("nickname", ""), String.class))){
            nickname.setText(getGson().fromJson(getSp().getString("nickname", ""), String.class));
        }
        if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("cover", ""), String.class))){
            imageLoader.displayImage(getGson().fromJson(getSp().getString("cover", ""), String.class), cover, MeetLoveApplication.txOptions, animateFirstListener);
            txt_pic.setText("更换头像");
        }else {
            txt_pic.setText("上传照片");
        }

        if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("mobile", ""), String.class))){
            mobile.setText(getGson().fromJson(getSp().getString("mobile", ""), String.class));
        }

        if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("pname", ""), String.class)) && !StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("cityName", ""), String.class))){
            address.setText(getGson().fromJson(getSp().getString("pname", ""), String.class)+getGson().fromJson(getSp().getString("cityName", ""), String.class));
        }
        if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("provinceid", ""), String.class))){
            provinceid = getGson().fromJson(getSp().getString("provinceid", ""), String.class);
        }
        if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("cityid", ""), String.class))){
            cityid = getGson().fromJson(getSp().getString("cityid", ""), String.class);
        }

        if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("sign", ""), String.class))){
            sign.setText(getGson().fromJson(getSp().getString("sign", ""), String.class));
        }

    }

    private void initView() {
        this.findViewById(R.id.back).setOnClickListener(this);
        this.findViewById(R.id.btn_right).setVisibility(View.GONE);
        title = (TextView) this.findViewById(R.id.title);
        title.setText("个人资料");

        cover = (ImageView) this.findViewById(R.id.cover);
        nickname = (EditText)this.findViewById(R.id.nickname);
        address = (TextView) this.findViewById(R.id.address);

        txt_pic = (TextView) this.findViewById(R.id.txt_pic);
        mobile = (TextView) this.findViewById(R.id.mobile);
        sign = (EditText) this.findViewById(R.id.sign);

        btn_login = (Button) this.findViewById(R.id.btn_login);

        cover.setOnClickListener(this);
        address.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        nickname.addTextChangedListener(watcher);
        sign.addTextChangedListener(watcher);
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(!StringUtil.isNullOrEmpty(sign.getText().toString()) && !StringUtil.isNullOrEmpty(nickname.getText().toString())){
                btn_login.setBackgroundResource(R.drawable.btn_big_active);
                btn_login.setTextColor(getResources().getColor(R.color.white));
            }else{
                btn_login.setBackgroundResource(R.drawable.btn_big_unactive);
                btn_login.setTextColor(getResources().getColor(R.color.textColortwo));
            }
        }
    };

    void hiddenKeyBoard(View v){
         /*隐藏软键盘*/
        InputMethodManager imm = (InputMethodManager) v
                .getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(
                    v.getApplicationWindowToken(), 0);
        }
    }

    private SelectPopQuiteWindow selectPopQuiteWindow;
    public void showQuitePop(){
        selectPopQuiteWindow = new SelectPopQuiteWindow(RegUpdateActivity.this, itemsOnClickQuite);
        //显示窗口
        setBackgroundAlpha(0.5f);//设置屏幕透明度

        selectPopQuiteWindow.setBackgroundDrawable(new BitmapDrawable());
        selectPopQuiteWindow.setFocusable(true);
        selectPopQuiteWindow.showAtLocation(this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopQuiteWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
    }

    private View.OnClickListener itemsOnClickQuite = new View.OnClickListener() {
        public void onClick(View v) {
            selectPopQuiteWindow.dismiss();
            switch (v.getId()) {
                case R.id.btnQuite: {
                    finish();
                }
                break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                hiddenKeyBoard(view);
//                if (!TextUtils.isEmpty(et_sendmessage.getText().toString().trim())|| dataList.size()!=0) {   //这里trim()作用是去掉首位空格，防止不必要的错误
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(sign.getWindowToken(), 0); //强制隐藏键盘
                showQuitePop();
//                } else {
//                    finish();
//                }
                finish();
                break;
            case R.id.cover:
            {
                //头像点击
                hiddenKeyBoard(view);
                showDialogPhoto();
            }
                break;
            case R.id.address:
            {
                //所在地
                Intent intent = new Intent(RegUpdateActivity.this, SelectAreaActivity.class);
                startActivityForResult(intent, 1000);
            }
            break;
            case R.id.btn_login:
            {
                if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("cover", ""), String.class))){
                }else {
                    if(StringUtil.isNullOrEmpty(pics)){
                        showMsg(RegUpdateActivity.this, "请上传头像");
                        return;
                    }
                }
                if(StringUtil.isNullOrEmpty(nickname.getText().toString())){
                    showMsg(RegUpdateActivity.this, "请输入姓名");
                    return;
                }
                if(nickname.getText().toString().length()>10){
                    showMsg(RegUpdateActivity.this, "姓名太长，请检查！");
                    return;
                }
                if(StringUtil.isNullOrEmpty(sign.getText().toString())){
                    showMsg(RegUpdateActivity.this, "请输入个性签名");
                    return;
                }
                if(sign.getText().toString().length()>32){
                    showMsg(RegUpdateActivity.this, "个性签名字数超限");
                    return;
                }

                if(StringUtil.isNullOrEmpty(provinceid) || StringUtil.isNullOrEmpty(cityid)){
                    showMsg(RegUpdateActivity.this, "请选择您的所在地");
                    return;
                }
                progressDialog = new CustomProgressDialog(RegUpdateActivity.this, "请稍后...",R.anim.custom_dialog_frame);
                progressDialog.setCancelable(true);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                updateProfile();
            }
                break;
        }
    }

    private void updateProfile(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appUpdateProfile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code = jo.getString("code");
                                if (Integer.parseInt(code) == 200) {
                                    EmpData data = getGson().fromJson(s, EmpData.class);
                                    saveAccount(data.getData());
                                    //调用广播，刷新主页
                                    Intent intent1 = new Intent("update_mine_profile_success");
                                    sendBroadcast(intent1);
                                    if (progressDialog != null) {
                                        progressDialog.dismiss();
                                    }
                                    finish();
                                }  else {
                                    if (progressDialog != null) {
                                        progressDialog.dismiss();
                                    }
                                    showMsg(RegUpdateActivity.this,  jo.getString("message"));

                                }
                            } catch (JSONException e) {
                                if (progressDialog != null) {
                                    progressDialog.dismiss();
                                }
                                e.printStackTrace();
                            }
                        }else{
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(RegUpdateActivity.this, "操作失败，请稍后重试！", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("empid", empid);
                params.put("sign", sign.getText().toString());
                params.put("nickname", nickname.getText().toString().trim());
                params.put("provinceid", provinceid);
                params.put("cityid", cityid);

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

    public void saveAccount(final Emp emp) {
        save("empid", emp.getEmpid());
        save("mobile", mobile.getText().toString());
        save("is_use", "1");
        save("rolestate", emp.getRolestate());
        save("nickname", nickname.getText().toString());
        save("cover", emp.getCover());
        save("provinceid", emp.getProvinceid());
        save("cityid", emp.getCityid());
        save("areaid", "");
        save("rzstate2", emp.getRzstate2());
        save("pname", emp.getPname());
        save("cityName",  emp.getCityName());
        save("is_push", "");
        save("yqnum", emp.getYqnum());
        save("tjempid", emp.getTjempid());
        save("sign", emp.getSign());
    }

    SelectPhotoPopWindow photosWindow;
    public void showDialogPhoto(){
        photosWindow = new SelectPhotoPopWindow(RegUpdateActivity.this, itemsOnClickPhoto);
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

    private View.OnClickListener itemsOnClickPhoto = new View.OnClickListener() {
        public void onClick(View v) {
            photosWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_photo: {
                    Intent mapstorage = new Intent(Intent.ACTION_PICK, null);
                    mapstorage.setDataAndType(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "image/*");
                    startActivityForResult(mapstorage, 1);
                }
                break;
                case R.id.btn_camera: {
                    Intent camera = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    //下面这句指定调用相机拍照后的照片存储的路径
                    camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                            .fromFile(new File(Environment
                                    .getExternalStorageDirectory(),
                                    "meetlove_cover.jpg")));
                    startActivityForResult(camera, 2);
                }
                break;
                default:
                    break;
            }
        }
    };

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) RegUpdateActivity.this).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) RegUpdateActivity.this).getWindow().setAttributes(lp);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // 如果是直接从相册获取
            case 1:
                if (data != null) {
                    startPhotoZoom(data.getData());
                }
                break;
            // 如果是调用相机拍照时
            case 2:
                File temp = new File(Environment.getExternalStorageDirectory()
                        + "/meetlove_cover.jpg");
                startPhotoZoom(Uri.fromFile(temp));
                break;
            // 取得裁剪后的图片
            case 3:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            case 1000:
            {
                if(resultCode == 10001){
                    City cityObj = (City) data.getExtras().get("cityObj");
                    Province provinceObj = (Province) data.getExtras().get("provinceObj");
                    if(provinceObj != null && cityObj != null){
                        address.setText(provinceObj.getPname() + cityObj.getCityName());
                        provinceid = provinceObj.getProvinceid();
                        cityid = cityObj.getCityid();
                    }
                }
            }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoomBg(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 3);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            if (photo != null) {
                pics = CompressPhotoUtil.saveBitmap2file(photo, System.currentTimeMillis() + ".jpg", PHOTO_CACHE_DIR);
                cover.setImageBitmap(photo);
                //上传图片到七牛
                uploadCover();
            }
        }
    }

    //上传到七牛云存贮
    void uploadCover(){
        Map<String,String> map = new HashMap<String,String>();
        map.put("space", InternetURL.QINIU_SPACE);
        RequestParams params = new RequestParams(map);
        client.get(InternetURL.UPLOAD_TOKEN, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String token = response.getString("data");
                    UploadManager uploadManager = new UploadManager();
                    uploadManager.put(StringUtil.getBytes(pics), StringUtil.getUUID(), token,
                            new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject response) {
                                    updateCover(key);
                                }
                            }, null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    //更新
    private void updateCover(final String uploadpic) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appUpdateCover,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code = jo.getString("code");
                                if(Integer.parseInt(code) == 200) {
                                    save("cover", InternetURL.QINIU_URL + uploadpic);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("empid", empid);
                params.put("cover", uploadpic);
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
