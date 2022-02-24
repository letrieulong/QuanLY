package com.example.appsp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appsp.R;
import com.example.appsp.model.Sanpham;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_ListSP extends RecyclerView.Adapter<Adapter_ListSP.ViewHolder>{

    Context context;
    List<Sanpham> list_sp;
    Sanpham sp = new Sanpham();

    public Adapter_ListSP(Context context, List<Sanpham> list_sp) {
        this.context = context;
        this.list_sp = list_sp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_listsp, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.txtname_sp.setText(list_sp.get(i).getName());
        holder.txtprice_sp.setText(list_sp.get(i).getPrice());
        Glide.with(context).load(list_sp.get(i).getImage()).into(holder.img_sp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        holder.txtprice_sp.setText("Giá : " + decimalFormat.format(list_sp.get(i).getPrice() + "Đ"));

    }

    @Override
    public int getItemCount() {
        return list_sp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View vv;
        ImageView img_sp;
        TextView txtname_sp;
        TextView txtprice_sp;
        Button btnmore;
        public ViewHolder(@NonNull View view) {
            super(view);
            img_sp = view.findViewById(R.id.img_sp);
            txtname_sp = view.findViewById(R.id.txtname_sp);
            txtprice_sp = view.findViewById(R.id.txtprice_sp);
            btnmore = view.findViewById(R.id.btnmore);
            vv = view;
        }

    }

    public void delete(int i){
        list_sp.remove(i);
        notifyDataSetChanged();
    }
}
