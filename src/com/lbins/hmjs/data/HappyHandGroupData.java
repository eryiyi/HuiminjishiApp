package com.lbins.hmjs.data;


import com.lbins.hmjs.dao.HappyHandGroup;

import java.util.List;

/**
 * Created by zhl on 2017/4/20.
 */
public class HappyHandGroupData extends  Data {
    private List<HappyHandGroup> data;

    public List<HappyHandGroup> getData() {
        return data;
    }

    public void setData(List<HappyHandGroup> data) {
        this.data = data;
    }
}
