package com.example.appsp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appsp.R;
import com.example.appsp.UrlServer;
import com.example.appsp.model.Sanpham;
import com.example.appsp.object.ChiTiet;
import com.example.appsp.object.Upload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ViewHodel> {
    Context context;
    List<Sanpham> listsp;
    List<Sanpham> listspcopy;

    public SanphamAdapter(Context context, List<Sanpham> listsp) {
        this.context = context;
        this.listsp = listsp;
        this.listspcopy = new ArrayList<>();
        listspcopy.addAll(listsp);
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listsanpham, parent, false);
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


        viewHodel.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Thông Báo").setMessage("Bạn có muốn xóa sản phẩm!!!");
                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlServer.delete,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        listsp.remove(listsp.get(i));
                                        notifyDataSetChanged();
                                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Kế nối thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> data = new HashMap<>();
                                data.put("ID", String.valueOf(listsp.get(i).getID()));
                                return data;
                            }
                        };
                        Volley.newRequestQueue(context).add(stringRequest);
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });

        viewHodel.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("data", listsp.get(i));
                Intent intent = new Intent(context, Upload.class);
                intent.putExtras(b);
                v.getContext().startActivity(intent);
            }
        });


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
        Button btnedit, btndelete;
        View vv;
        public ViewHodel(@NonNull View view) {
            super(view);
            imgviewquan = view.findViewById(R.id.imgquan);
            txtgiaquan = view.findViewById(R.id.txtgiaspquan);
            txtTitlequan = view.findViewById(R.id.txttenspquan);
            btndelete   = view.findViewById(R.id.btndelete);
            btnedit     = view.findViewById(R.id.btnedit);
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
