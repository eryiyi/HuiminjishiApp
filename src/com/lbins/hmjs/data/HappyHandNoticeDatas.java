package com.lbins.hmjs.data;


import com.lbins.hmjs.dao.HappyHandNotice;

import java.util.List;

/**
 * Created by zhl on 2017/4/21.
 */
public class HappyHandNoticeDatas extends Data {
    private List<HappyHandNotice> data;

    public List<HappyHandNotice> getData() {
        return data;
    }

    public void setData(List<HappyHandNotice> data) {
        this.data = data;
    }
}
