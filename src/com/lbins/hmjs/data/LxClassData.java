package com.lbins.hmjs.data;

import com.lbins.hmjs.module.LxClass;

import java.util.List;

/**
 * Created by zhl on 2016/11/30.
 */
public class LxClassData extends Data {
    private List<LxClass> data;

    public List<LxClass> getData() {
        return data;
    }

    public void setData(List<LxClass> data) {
        this.data = data;
    }
}
