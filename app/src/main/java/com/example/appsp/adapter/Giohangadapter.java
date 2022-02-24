package com.example.appsp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appsp.MainActivity;
import com.example.appsp.R;
import com.example.appsp.model.Shopping;
import com.example.appsp.object.GioHang;

import java.text.DecimalFormat;
import java.util.List;

public class Giohangadapter extends BaseAdapter {
    Context context;
    List<Shopping> listgiohang;

    public Giohangadapter(Context context, List<Shopping> listgiohang) {
        this.context = context;
        this.listgiohang = listgiohang;
    }

    @Override
    public int getCount() {
        return listgiohang.size();
    }

    @Override
    public Object getItem(int i) {
        return listgiohang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Viewholder{
        ImageView img_giohang;
        TextView txttenspgh, txtgiaspgh;
        Button btnleft, btnright, btnvalue;
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Viewholder viewholder = null;
        if(view == null){
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.giohangview, null);

            viewholder.btnleft = view.findViewById(R.id.btnleft);
            viewholder.btnright = view.findViewById(R.id.btnright);
            viewholder.btnvalue = view.findViewById(R.id.btnvalue);
            viewholder.btnvalue = view.findViewById(R.id.btnvalue);
            viewholder.img_giohang = view.findViewById(R.id.imgview_giohang);
            viewholder.txttenspgh = view.findViewById(R.id.txttenspgh);
            viewholder.txtgiaspgh = view.findViewById(R.id.txtgiaspgh);

            view.setTag(viewholder);
        }else {
            viewholder = (Viewholder) view.getTag();
        }
        viewholder.txttenspgh.setMaxLines(1);
        viewholder.txttenspgh.setEllipsize(TextUtils.TruncateAt.END);
        viewholder.txttenspgh.setText(listgiohang.get(i).getTensp());
        viewholder.txtgiaspgh.setText("Giá " + numbercurrenFormat(Math.toIntExact(listgiohang.get(i).getGiasp())) +"đ");
        Glide.with(context).
                load(listgiohang.get(i).getImgsp())
                .into(viewholder.img_giohang);
        viewholder.btnvalue.setText(listgiohang.get(i).getSoluongsp() + "");

        int sl = Integer.parseInt(viewholder.btnvalue.getText().toString());
        if(sl > 9){
            viewholder.btnright.setVisibility(View.INVISIBLE);
            viewholder.btnleft.setVisibility(View.VISIBLE);
        }else if(sl <= 1){
            viewholder.btnleft.setVisibility(View.INVISIBLE);
        }else if(sl > 1){
            viewholder.btnleft.setVisibility(View.VISIBLE);
            viewholder.btnright.setVisibility(View.VISIBLE);
        }
        Viewholder finalViewholder = viewholder;
        viewholder.btnright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slnew = Integer.parseInt(finalViewholder.btnvalue.getText().toString()) + 1;
                int slhientai = MainActivity.arrayshopping.get(i).getSoluongsp();

                long giahientai = MainActivity.arrayshopping.get(i).getGiasp();

                MainActivity.arrayshopping.get(i).setSoluongsp(slnew);

                long giamoi = (giahientai * slnew) / slhientai;

                MainActivity.arrayshopping.get(i).setGiasp(giamoi);

                GioHang.EventUtil();
                notifyDataSetChanged();
            }
        });

        viewholder.btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slnew = Integer.parseInt(finalViewholder.btnvalue.getText().toString()) - 1;
                int slhientai = MainActivity.arrayshopping.get(i).getSoluongsp();

                long giahientai = MainActivity.arrayshopping.get(i).getGiasp();

                MainActivity.arrayshopping.get(i).setSoluongsp(slnew);

                long giamoi = (giahientai * slnew) / slhientai;

                MainActivity.arrayshopping.get(i).setGiasp(giamoi);

                GioHang.EventUtil();
                notifyDataSetChanged();
            }
        });
        return view;
    }

    private String numbercurrenFormat(int number){
        DecimalFormat format = new DecimalFormat("###,###,###");
        return format.format((number));
    }

}
