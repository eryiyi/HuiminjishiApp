package com.lbins.hmjs.data;

import com.lbins.hmjs.module.PaopaoGoods;

import java.util.List;

/**
 * Created by zhl on 2016/9/13.
 */
public class PaopaoGoodsData extends Data {
    private List<PaopaoGoods> data;

    public List<PaopaoGoods> getData() {
        return data;
    }

    public void setData(List<PaopaoGoods> data) {
        this.data = data;
    }
}
