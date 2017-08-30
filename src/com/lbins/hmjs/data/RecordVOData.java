package com.lbins.hmjs.data;

import com.lbins.hmjs.module.RecordVO;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30 0030.
 */
public class RecordVOData extends Data {
    private List<RecordVO> data;

    public List<RecordVO> getData() {
        return data;
    }

    public void setData(List<RecordVO> data) {
        this.data = data;
    }
}
