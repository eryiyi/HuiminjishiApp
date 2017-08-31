package com.lbins.hmjs.data;

import com.lbins.hmjs.module.RecordComment;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30 0030.
 */
public class RecordCommentData extends Data {
    private List<RecordComment> data;

    public List<RecordComment> getData() {
        return data;
    }

    public void setData(List<RecordComment> data) {
        this.data = data;
    }
}
