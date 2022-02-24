package com.example.appsp.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appsp.R;
import com.example.appsp.model.Sanpham;
import com.example.appsp.object.ChiTiet;

import java.util.List;

public class Adapternews extends RecyclerView.Adapter<Adapternews.ViewHolder> {
    List<Sanpham> listsp;
    Context context;

    public Adapternews(List<Sanpham> listsp, Context context) {
        this.listsp = listsp;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.txtname_sp.setMaxLines(2);
        holder.txtname_sp.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtname_sp.setText(listsp.get(i).getName());
        holder.txtprice_sp.setText(numbercurrenFormat(listsp.get(i).getPrice()) + "đ");

        ImageView imageView = new ImageView(context);

        Glide.with(context).load(listsp.get(i).getImage()).into(imageView);
//        holder.viewFlipper.addView(imageView);

        Glide.with(context)
                .load(listsp.get(i).getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.img_sp);

        holder.vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("image", listsp.get(i));
                Intent intent = new Intent(context, ChiTiet.class);
                intent.putExtras(b);
                v.getContext().startActivity(intent);
            }
        });
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slidenewmain);
        holder.txtviewnewx.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return listsp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View vv;
        ImageView img_sp;
        TextView txtname_sp;
        TextView txtprice_sp;
        TextView txtviewnewx;
        ViewFlipper viewFlipper;
        public ViewHolder(@NonNull View view) {
            super(view);
            img_sp      = view.findViewById(R.id.img_sp);
            txtname_sp  = view.findViewById(R.id.txtname_sp);
            txtprice_sp = view.findViewById(R.id.txtprice_sp);
            txtviewnewx = view.findViewById(R.id.txtviewnews);
            viewFlipper = view.findViewById(R.id.viewflipper);
            vv = view;
        }
    }
    public String numbercurrenFormat(String number){
        DecimalFormat format = new DecimalFormat("###,###,###");
        return format.format(Double.parseDouble(number));
    }
}
