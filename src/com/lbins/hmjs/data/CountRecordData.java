package com.lbins.hmjs.data;

import com.lbins.hmjs.module.CountRecord;

import java.util.List;

/**
 * Created by zhl on 2016/10/7.
 */
public class CountRecordData extends Data {
    private List<CountRecord> data;

    public List<CountRecord> getData() {
        return data;
    }

    public void setData(List<CountRecord> data) {
        this.data = data;
    }
}
