package com.example.appsp.adapter;

import android.content.Context;

import com.example.appsp.model.Sanpham;

import java.util.List;

public class Adapter_add {
    List<Sanpham> listsp;
    Context context;

    public Adapter_add(List<Sanpham> listsp, Context context) {
        this.listsp = listsp;
        this.context = context;
    }
}
