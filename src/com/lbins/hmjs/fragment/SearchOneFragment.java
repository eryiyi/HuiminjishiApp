package com.lbins.hmjs.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.lbins.hmjs.R;
import com.lbins.hmjs.base.BaseFragment;
import com.lbins.hmjs.module.HappyHandLike;
import com.lbins.hmjs.ui.SearchPeopleActivity;
import com.lbins.hmjs.util.StringUtil;
import com.lbins.hmjs.widget.PopAgeWindow;
import com.lbins.hmjs.widget.PopEducationWindow2;
import com.lbins.hmjs.widget.PopHeightlWindow;
import com.lbins.hmjs.widget.PopMarryWindow2;

import java.util.ArrayList;
import java.util.List;

public class SearchOneFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private Resources res;

    private Button btn_login;
    private EditText keywords;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_one_fragment, null);
        res = getActivity().getResources();
        initView();
        return view;
    }

    void initView(){
        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        keywords = (EditText) view.findViewById(R.id.keywords);
        keywords.addTextChangedListener(watcher);
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
                    if(!StringUtil.isNullOrEmpty(keywords.getText().toString())){
                            btn_login.setBackgroundResource(R.drawable.btn_big_active);
                            btn_login.setTextColor(getResources().getColor(R.color.white));
                            Intent intent = new Intent(getActivity(), SearchPeopleActivity.class);
                            intent.putExtra("keywords", keywords.getText().toString());
                            startActivity(intent);
                    }else{
                        if(!StringUtil.isNullOrEmpty(keywords.getText().toString()))
                        {
                            btn_login.setBackgroundResource(R.drawable.btn_big_active);
                            btn_login.setTextColor(getResources().getColor(R.color.white));
                            Intent intent = new Intent(getActivity(), SearchPeopleActivity.class);
                            intent.putExtra("keywords", keywords.getText().toString());
                            startActivity(intent);
                        } else {
                            btn_login.setBackgroundResource(R.drawable.btn_big_unactive);
                            btn_login.setTextColor(getResources().getColor(R.color.textColortwo));
                            Toast.makeText(getActivity(), "请选择查询条件!", Toast.LENGTH_SHORT).show();
                        }
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
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }
        @Override
        public void afterTextChanged(Editable s) {

            if(!StringUtil.isNullOrEmpty(keywords.getText().toString()))
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
                        btn_login.setBackgroundResource(R.drawable.btn_big_active);
                        btn_login.setTextColor(getResources().getColor(R.color.white));
                        Intent intent = new Intent(getActivity(), SearchPeopleActivity.class);
                        intent.putExtra("keywords", keywords.getText().toString());
                        startActivity(intent);
                }else{
                    if(!StringUtil.isNullOrEmpty(keywords.getText().toString())){
                                Intent intent = new Intent(getActivity(), SearchPeopleActivity.class);
                                intent.putExtra("keywords", keywords.getText().toString());
                                startActivity(intent);
                    }else {
                        Toast.makeText(getActivity(), "请选择查询条件!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
                break;
        }
    }

}
