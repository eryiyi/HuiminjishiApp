package com.lbins.hmjs.data;

import com.lbins.hmjs.module.City;

import java.util.List;

/**
 * Created by zhl on 2017/4/9.
 */
public class CityData extends Data {
    private List<City> data;

    public List<City> getData() {
        return data;
    }

    public void setData(List<City> data) {
        this.data = data;
    }
}
