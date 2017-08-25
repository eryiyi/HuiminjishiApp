package com.lbins.hmjs.data;


import com.lbins.hmjs.dao.Friends;

import java.util.List;

/**
 * Created by zhl on 2017/4/21.
 */
public class FriendsData extends Data {
    private List<Friends> data;

    public List<Friends> getData() {
        return data;
    }

    public void setData(List<Friends> data) {
        this.data = data;
    }
}
