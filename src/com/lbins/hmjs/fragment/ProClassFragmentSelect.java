package com.lbins.hmjs.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.lbins.hmjs.MeetLoveApplication;
import com.lbins.hmjs.R;
import com.lbins.hmjs.adapter.AnimateFirstDisplayListener;
import com.lbins.hmjs.adapter.GridClassViewAdapter;
import com.lbins.hmjs.adapter.GridClassViewAdapter2;
import com.lbins.hmjs.base.BaseFragment;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.data.LxClassData;
import com.lbins.hmjs.module.LxClass;
import com.lbins.hmjs.ui.SearchMoreClassActivitySelect;
import com.lbins.hmjs.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProClassFragmentSelect extends BaseFragment {

	private ArrayList<LxClass> list = new ArrayList<LxClass>();
	private GridView gridView;
	private GridClassViewAdapter2 adapter;
	private LxClass goodsType;
	private TextView toptype;
	private ImageView icon;

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pro_type, null);
		gridView = (GridView) view.findViewById(R.id.listView);
		int index = getArguments().getInt("index");
		goodsType = SearchMoreClassActivitySelect.list.get(index);
		toptype = (TextView) view.findViewById(R.id.toptype);
		icon = (ImageView) view.findViewById(R.id.icon);
		if(goodsType != null){
			toptype.setText(goodsType.getLx_class_name());
			imageLoader.displayImage(goodsType.getLx_class_cover(), icon, MeetLoveApplication.txOptions, animateFirstListener);
		}
		GetTypeList();
		adapter = new GridClassViewAdapter2(getActivity(), list);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				if(list !=null){
					if(list.size() > i){
						LxClass lxClass = list.get(i);
						if(lxClass != null){
							Intent mIntent = new Intent();
							mIntent.putExtra("cloud_caoping_guige_id", lxClass.getLx_class_id());
							mIntent.putExtra("cloud_caoping_guige_cont", lxClass.getLx_class_name());
							// 设置结果，并进行传送
							getActivity().setResult(1001, mIntent);
							getActivity().finish();
						}
					}
				}

			}
		});

		return view;
	}

	private void GetTypeList() {
			StringRequest request = new StringRequest(
					Request.Method.POST,
					InternetURL.appGetLxClass,
					new Response.Listener<String>() {
						@Override
						public void onResponse(String s) {
							if (StringUtil.isJson(s)) {
								try {
									JSONObject jo = new JSONObject(s);
									String code = jo.getString("code");
									if (Integer.parseInt(code) == 200) {
										list.clear();
										Gson gson = getGson();
										if(gson != null){
											LxClassData data = gson.fromJson(s, LxClassData.class);
											list.addAll(data.getData());
											adapter.notifyDataSetChanged();
										}
									}else {
										Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
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
							Toast.makeText(getActivity(), getResources().getString(R.string.get_data_error), Toast.LENGTH_SHORT).show();
						}
					}
			) {
				@Override
				protected Map<String, String> getParams() throws AuthFailureError {
					Map<String, String> params = new HashMap<String, String>();
					params.put("f_lx_class_id", goodsType.getLx_class_id());
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
	public void onStop() {
		releaseImageViews();
		super.onStop();
	}

	private void releaseImageViews() {
		if(icon != null){
			releaseImageView(icon);
		}
	}

	private void releaseImageView(ImageView imageView) {
		Drawable d = imageView.getDrawable();
		if (d != null)
			d.setCallback(null);
		imageView.setImageDrawable(null);
		imageView.setBackgroundDrawable(null);
	}

}
