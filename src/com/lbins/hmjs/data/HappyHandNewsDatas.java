package com.lbins.hmjs.data;


import com.lbins.hmjs.dao.HappyHandNews;

import java.util.List;

/**
 * Created by zhl on 2017/4/21.
 */
public class HappyHandNewsDatas extends Data {
    private List<HappyHandNews> data;

    public List<HappyHandNews> getData() {
        return data;
    }

    public void setData(List<HappyHandNews> data) {
        this.data = data;
    }
}
