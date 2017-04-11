package com.example.vlada.countryselector.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoResponse {
    @SerializedName("geonames")
    private List<City> result;

    public List<City> getResult() {
        return result;
    }

    public void setResult(List<City> result) {
        this.result = result;
    }
}
