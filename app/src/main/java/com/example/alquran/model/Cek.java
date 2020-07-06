package com.example.alquran.model;

import com.google.gson.annotations.SerializedName;

public class Cek {
    @SerializedName("data")
    private Data data;

    public Cek(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
