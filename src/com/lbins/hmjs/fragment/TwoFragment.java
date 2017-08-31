package com.lbins.hmjs.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.util.NetUtils;
import com.lbins.hmjs.MainActivity;
import com.lbins.hmjs.R;
import com.lbins.hmjs.base.InternetURL;
import com.lbins.hmjs.chat.Constant;
import com.lbins.hmjs.chat.db.InviteMessgeDao;
import com.lbins.hmjs.chat.ui.ChatActivity;
import com.lbins.hmjs.dao.DBHelper;
import com.lbins.hmjs.dao.Emp;
import com.lbins.hmjs.data.EmpData;
import com.lbins.hmjs.ui.PayEmpRzActivity;
import com.lbins.hmjs.util.StringUtil;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhl on 2016/7/1.
 * 推荐
 */
public class TwoFragment  extends EaseConversationListFragment {

    private TextView errorText;

    @Override
    protected void initView() {
        super.initView();
        View errorView = (LinearLayout) View.inflate(getActivity(),R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.conversationId();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    if("1".equals(getGson().fromJson(getSp().getString("rzstate2", ""), String.class))){
                            Intent intent = new Intent(getActivity(), ChatActivity.class);
                            if(conversation.isGroup()){
                                if(conversation.getType() == EMConversation.EMConversationType.ChatRoom){
                                    intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                                }else{
                                    intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                                }
                            }
                            intent.putExtra(Constant.EXTRA_USER_ID, username);
                            startActivity(intent);
                    }else{
                        showMsgDialog();
                    }

                }
            }
        });
        super.setUpView();
    }

    private void showMsgDialog() {
        final Dialog picAddDialog = new Dialog(getActivity(), R.style.dialog);
        View picAddInflate = View.inflate(getActivity(), R.layout.msg_dialog, null);
        TextView btn_sure = (TextView) picAddInflate.findViewById(R.id.btn_sure);
        TextView msg = (TextView) picAddInflate.findViewById(R.id.msg);
        msg.setText("请做会员认证");
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PayEmpRzActivity.class);
                startActivity(intent);
                picAddDialog.dismiss();
            }
        });
        TextView btn_cancel = (TextView) picAddInflate.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picAddDialog.dismiss();
            }
        });
        picAddDialog.setContentView(picAddInflate);
        picAddDialog.show();
    }

    private void getEmpById(final String empid) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.appEmpByEmpId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                int code1 = jo.getInt("code");
                                if (code1 == 200) {
                                    EmpData data = getGson().fromJson(s, EmpData.class);
                                    if(data != null){
                                        Emp emp = data.getData();
                                        if(emp != null){
                                            DBHelper.getInstance(getActivity()).saveEmp(emp);
                                            Intent intent = new Intent(getActivity(), ChatActivity.class);
                                            intent.putExtra(Constant.EXTRA_USER_ID, empid);
                                            startActivity(intent);
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), jo.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("empid", empid);
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
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())){
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
        EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
        if (tobeDeleteCons == null) {
            return true;
        }
        if(tobeDeleteCons.getType() == EMConversation.EMConversationType.GroupChat){
            EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.conversationId());
        }
        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId(), deleteMessage);
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
            inviteMessgeDao.deleteMessage(tobeDeleteCons.conversationId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();

        // update unread count
        ((MainActivity) getActivity()).updateUnreadLabel();
        return true;
    }

}
