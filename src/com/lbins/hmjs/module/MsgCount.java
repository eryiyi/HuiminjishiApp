package com.lbins.hmjs.module;

import com.lbins.hmjs.dao.HappyHandJw;
import com.lbins.hmjs.dao.HappyHandMessage;
import com.lbins.hmjs.dao.HappyHandNews;
import com.lbins.hmjs.dao.HappyHandNotice;

import java.util.List;

/**
 * Created by zhl on 2017/4/24.
 */
public class MsgCount {
    private List<HappyHandMessage> list1;
    private List<HappyHandNotice> list3;

    public List<HappyHandMessage> getList1() {
        return list1;
    }

    public void setList1(List<HappyHandMessage> list1) {
        this.list1 = list1;
    }

    public List<HappyHandNotice> getList3() {
        return list3;
    }

    public void setList3(List<HappyHandNotice> list3) {
        this.list3 = list3;
    }

}
