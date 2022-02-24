package com.example.appsp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appsp.R;
import com.example.appsp.model.Loaisp;

import java.util.ArrayList;

public class LoaiAdapter extends BaseAdapter {
    ArrayList<Loaisp> arrayListloai;
    Context context;

    public LoaiAdapter(ArrayList<Loaisp> arrayListloai, Context context) {
        this.arrayListloai = arrayListloai;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListloai.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListloai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolde{
        ImageView imgloaisp;
        TextView txttenloai;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolde viewHolde = null;
        if (view == null){
            viewHolde = new ViewHolde();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view_loaisp, null);

            viewHolde.imgloaisp  = view.findViewById(R.id.img_loaisp);
            viewHolde.txttenloai = view.findViewById(R.id.txtloaisp);

            view.setTag(viewHolde);
        }else {
            viewHolde = (ViewHolde) view.getTag();
        }
        viewHolde.txttenloai.setText(arrayListloai.get(i).getLoaisp());
        Glide.with(context).load(arrayListloai.get(i).getHinhLoai()).into(viewHolde.imgloaisp);
        return view;
    }
}
