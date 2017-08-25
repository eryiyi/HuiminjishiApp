package com.lbins.hmjs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lbins.hmjs.MainActivity;
import com.lbins.hmjs.MeetLoveApplication;
import com.lbins.hmjs.R;
import com.lbins.hmjs.base.BaseActivity;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.dao.DBHelper;
import com.lbins.hmjs.dao.Emp;
import com.lbins.hmjs.data.EmpData;
import com.lbins.hmjs.util.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhl on 2016/5/6.
 */
public class WelcomeActivity extends BaseActivity implements Runnable,AMapLocationListener {
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //定位
    private AMapLocationClient mlocationClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(20000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();


        // 启动一个线程
        new Thread(WelcomeActivity.this).start();
    }


    @Override
    public void run() {
        try {
            // 3秒后跳转到登录界面
            Thread.sleep(3000);
//            boolean isFirstRun = getSp().getBoolean("isFirstRun", true);
//            boolean isFirstRun = false;
//            if (isFirstRun) {
//                SharedPreferences.Editor editor = getSp().edit();
//                editor.putBoolean("isFirstRun", false);
//                editor.commit();
//                Intent loadIntent = new Intent(WelcomeActivity.this, AboutActivity.class);
//                startActivity(loadIntent);
//                finish();
//            } else {
                if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("mobile", ""), String.class)) && !StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("password", ""), String.class))){
                    //查看是否有用户  是否使用用户----0否 1是 2尚未维护资料
                    if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("empid",""), String.class)) && !StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("is_use",""), String.class)) ){
                        if("0".equals(getGson().fromJson(getSp().getString("is_use",""), String.class))){
                            //用户被禁用
                            showMsg(WelcomeActivity.this, "该用户已被禁用，请联系客服！");
                        }else{
                            loginData();
                        }
                    }else {
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }else{
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
//            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loginData(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code = jo.getString("code");
                                if (Integer.parseInt(code) == 200) {
                                    EmpData data = getGson().fromJson(s,EmpData.class);
                                    saveAccount(data.getData());
                                } else {
                                    showMsg(WelcomeActivity.this, jo.getString("message"));
                                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (JSONException e) {
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
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", getGson().fromJson(getSp().getString("mobile", ""), String.class));
                params.put("password", getGson().fromJson(getSp().getString("password", ""), String.class));
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
        save("mobile", emp.getMobile());
        save("nickname", emp.getNickname());
        save("cover", emp.getCover());
        save("provinceid", emp.getProvinceid());
        save("cityid", emp.getCityid());
        save("areaid", emp.getAreaid());
        save("rzstate2", emp.getRzstate2());
        save("is_use", emp.getIs_use());
        save("pname", emp.getPname());
        save("cityName", emp.getCityName());
        save("yqnum", emp.getYqnum());
        save("tjempid", emp.getTjempid());
        save("rolestate", emp.getRolestate());
        save("sign", emp.getSign());

        DBHelper.getInstance(WelcomeActivity.this).saveEmp(emp);
        EMClient.getInstance().logout(true);
        EMClient.getInstance().login(emp.getEmpid(), "123456", new EMCallBack() {
            @Override
            public void onSuccess() {
                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        MeetLoveApplication.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }

                // get user's info (this should be get from App's server or 3rd party service)
//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();

                Intent intent = new Intent(WelcomeActivity.this,
                        MainActivity.class);
                startActivity(intent);

                finish();
            }

            @Override
            public void onError(int i, String s) {
                Intent intent = new Intent(WelcomeActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

    }
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                MeetLoveApplication.latStr = String.valueOf(amapLocation.getLatitude());
                MeetLoveApplication.lngStr = String.valueOf(amapLocation.getLongitude());
                MeetLoveApplication.locationAddress = amapLocation.getAddress();
                MeetLoveApplication.locationProvinceName = String.valueOf(amapLocation.getProvince());
                MeetLoveApplication.locationCityName = String.valueOf(amapLocation.getCity());
                MeetLoveApplication.locationAreaName = String.valueOf(amapLocation.getDistrict());

                if(!StringUtil.isNullOrEmpty(MeetLoveApplication.latStr) && !StringUtil.isNullOrEmpty(MeetLoveApplication.lngStr) && !StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("empid", ""), String.class))){
                    sendLocation();
                }

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    void sendLocation() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appUpdateLocation,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code = jo.getString("code");
                                if (Integer.parseInt(code) == 200) {

                                } else {
                                }
                            } catch (JSONException e) {
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
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("lat", (MeetLoveApplication.latStr == null ? "" : MeetLoveApplication.latStr));
                params.put("lng", (MeetLoveApplication.lngStr == null ? "" : MeetLoveApplication.lngStr));
                if(!StringUtil.isNullOrEmpty(getGson().fromJson(getSp().getString("empid", ""), String.class))){
                    params.put("empid", getGson().fromJson(getSp().getString("empid", ""), String.class));
                }
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
