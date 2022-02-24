package com.example.appsp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appsp.R;
import com.example.appsp.model.OrderList;

import java.text.DecimalFormat;
import java.util.List;

public class Adapterorder extends RecyclerView.Adapter<Adapterorder.Viewholder> {
    List<OrderList> orderlist;
    Context context;

    public Adapterorder(List<OrderList> orderlist, Context context) {
        this.orderlist = orderlist;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_frag_layout1, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int i) {
        holder.txtnamesp.setMaxLines(1);
        holder.txtnamesp.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtnamesp.setText(orderlist.get(i).getNamesp());
        holder.txtpricesp.setText(numberformat(orderlist.get(i).getPricesp()) + "đ");
        holder.txtquantitysp.setText(orderlist.get(i).getQuantity());
        holder.txtdateorder.setText(orderlist.get(i).getDateorder());
        Glide.with(context).load(orderlist.get(i).getImage()).into(holder.imgsp);
    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgsp;
        TextView txtnamesp, txtpricesp, txtquantitysp, txtdateorder;
        View vv;
        public Viewholder(@NonNull View view) {
            super(view);
            imgsp = view.findViewById(R.id.imgview_layout);
            txtnamesp = view.findViewById(R.id.txttensporder);
            txtpricesp = view.findViewById(R.id.txtgiasporder);
            txtquantitysp = view.findViewById(R.id.txtslsporder);
            txtdateorder = view.findViewById(R.id.txttimeorder);
            vv = view;
        }
    }
    private String numberformat(String number){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return decimalFormat.format(Double.parseDouble(number));
    }
}
