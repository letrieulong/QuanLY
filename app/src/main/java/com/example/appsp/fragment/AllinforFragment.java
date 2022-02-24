package com.example.appsp.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.appsp.R;
import com.example.appsp.adapter.SanphamAdapter;
import com.example.appsp.object.Insertactivity;
import com.example.appsp.object.List_order_Activity;
import com.example.appsp.object.Login;
import com.example.appsp.object.ProfileActivity;
import com.example.appsp.object.SanPhamActivity;
import com.example.appsp.object.Upload;

public class AllinforFragment extends Fragment {

    View view;
    CardView cardviewadd;
    LinearLayout linearorder, linearhelp, linearsetting, linearaddsp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_allinfor, container, false);

        init();
        event();

        return view;
    }

    private void event() {
        linearorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), List_order_Activity.class));
            }
        });

        linearhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });

        linearsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });

        linearaddsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alerdialog();
//                startActivity(new Intent(getContext(), SanPhamActivity.class));
            }
        });
    }

    private void alerdialog(){
        final Drawable positiveIcon = getContext().getResources().getDrawable(R.drawable.noimage);

        AlertDialog.Builder al = new AlertDialog.Builder(getContext());
        al.setTitle("           Chọn Chức Năng");
        al.setNegativeButton("Thêm Sản Phẩm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getContext(), Insertactivity.class));
            }
        });

        al.setPositiveButton("                  Chi Tiết", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getContext(), SanPhamActivity.class));
            }
        });

        AlertDialog alertDialog = al.create();
        alertDialog.show();
    }

    private void dialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment_dialog);
        dialog.show();
    }

    private void init() {
        linearaddsp    = view.findViewById(R.id.linearaddsp);
        linearorder    = view.findViewById(R.id.linearorder);
        linearhelp     = view.findViewById(R.id.linearhelp);
        linearsetting  = view.findViewById(R.id.linearsetting);
        cardviewadd    = view.findViewById(R.id.cardviewadd);
        if(Login.edtacount.getText().toString().contains("admin")){
            cardviewadd.setVisibility(View.VISIBLE);
        }
    }
}