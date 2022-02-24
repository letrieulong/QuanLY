package com.example.appsp.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appsp.R;
import com.example.appsp.model.Sanpham;
import com.example.appsp.object.ChiTiet;
import com.example.appsp.object.Upload;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHodel> {
    Context context;
    List<Sanpham> listsp;
    List<Sanpham> listspcopy;

    public DataAdapter(Context context, List<Sanpham> listsp) {
        this.context = context;
        this.listsp = listsp;
        this.listspcopy = new ArrayList<>();
        listspcopy.addAll(listsp);
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listquan, parent, false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel viewHodel, int i) {
        viewHodel.txtgiaquan.setText(numberCurrenFormat(listsp.get(i).getPrice()) + "đ");
        viewHodel.txtTitlequan.setMaxLines(2);
        viewHodel.txtTitlequan.setEllipsize(TextUtils.TruncateAt.END);
        viewHodel.txtTitlequan.setText(listsp.get(i).getName());
        Glide.with(context)
                .load(listsp.get(i).getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHodel.imgviewquan);

        viewHodel.vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("image", listsp.get(i));
                Intent intent = new Intent(context, ChiTiet.class);
                intent.putExtras(b);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listsp.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder{
        Toolbar toolbar;
        TextView txtgiaquan;
        TextView txtTitlequan;
        ImageView imgviewquan;
        View vv;
        public ViewHodel(@NonNull View view) {
            super(view);
            imgviewquan = view.findViewById(R.id.imgquan);
            txtgiaquan = view.findViewById(R.id.txtgiaspquan);
            txtTitlequan = view.findViewById(R.id.txttenspquan);
            vv = view;
        }
    }
    public String numberCurrenFormat(String number){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return decimalFormat.format(Double.parseDouble(number));
    }

    public void filter(CharSequence charSequence){
        ArrayList<Sanpham> tempArraylist = new ArrayList<>();
        if(!TextUtils.isEmpty(charSequence)){
            for(Sanpham sanpham : listsp){
                if(sanpham.getName().toLowerCase().contains(charSequence)){
                    tempArraylist.add(sanpham);
                }
            }
        }else {
            tempArraylist.addAll(listspcopy);
        }
        listsp.clear();
        listsp.addAll(tempArraylist);
        notifyDataSetChanged();
        tempArraylist.clear();
    }
}
