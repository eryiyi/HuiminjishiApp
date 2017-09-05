package com.lbins.hmjs.data;

import com.lbins.hmjs.module.DianpuComment;

import java.util.List;

/**
 * Created by zhl on 2016/12/22.
 */
public class DianpuCommentData extends Data {
    private List<DianpuComment> data;

    public List<DianpuComment> getData() {
        return data;
    }

    public void setData(List<DianpuComment> data) {
        this.data = data;
    }
}
