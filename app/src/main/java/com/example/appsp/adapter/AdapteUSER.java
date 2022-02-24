package com.example.appsp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appsp.R;
import com.example.appsp.model.User;
import com.example.appsp.object.Login;
import com.example.appsp.object.Updateuser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapteUSER extends RecyclerView.Adapter<AdapteUSER.ViewHolder> {
    List<User> users;
    Context context;

    public AdapteUSER(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_profile, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        holder.edtnamelog.setEnabled(false); //== holder.txtnamelog.setFocusable(false);
        holder.edtname.setEnabled(false);
        holder.edtemail.setEnabled(false);
        holder.edtphone.setEnabled(false);
        holder.edtbirtday.setEnabled(false);
        holder.edtset.setEnabled(false);

        holder.edtnamelog.setText(users.get(i).getFullname());
        holder.edtname.setText(users.get(i).getUsername());
        holder.edtemail.setText(users.get(i).getEmail());
        holder.edtphone.setText(users.get(i).getPhone());
        holder.edtbirtday.setText(users.get(i).getBirthday());
        holder.edtset.setText(users.get(i).getSet());

        Glide.with(context)
                .load(users.get(i).getUrlimg())
                .placeholder(R.drawable.noimage)
                .into(holder.cirImgpro);

        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.edtnamelog.setEnabled(true); //== holder.txtnamelog.setFocusable(false);
                holder.edtname.setEnabled(true);
                holder.edtemail.setEnabled(true);
                holder.edtbirtday.setEnabled(true);
                holder.edtset.setEnabled(true);
                holder.edtphone.setEnabled(true);
//                holder.btncancle.setVisibility(View.VISIBLE);
//                holder.btnupdate.setVisibility(View.VISIBLE);
//                holder.btnedit.setVisibility(View.GONE);
//
                Intent intent = new Intent(context, Updateuser.class);
                intent.putExtra("key", users.get(i));
                context.startActivity(intent);
            }
        });

        holder.btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Login.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View vv;
        RelativeLayout realeimgae;
        TextView txt_openDialog;
        Button btnedit, btnlogout;

        CircleImageView cirImgpro;
        EditText edtname, edtnamelog, edtbirtday, edtset, edtemail, edtphone;
        public ViewHolder(@NonNull View view) {
            super(view);
            realeimgae    = view.findViewById(R.id.realeimgae);
            edtname       = view.findViewById(R.id.edtusername);
            edtnamelog    = view.findViewById(R.id.edtfullname);
            edtbirtday    = view.findViewById(R.id.edtbirtday);
            edtset        = view.findViewById(R.id.edtset);
            edtemail      = view.findViewById(R.id.edtemail);
            edtphone      = view.findViewById(R.id.edtphone);
            cirImgpro     = view.findViewById(R.id.cirImgpro);
            btnedit       = view.findViewById(R.id.btnedit);
            btnlogout     = view.findViewById(R.id.btnlogout);

            vv = view;
        }
    }





//    private void update(){
//        StringRequest request = new StringRequest(Request.Method.GET, UrlServer.updateprofile,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, "Kết nói thất bại", Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> data = new HashMap<String, String>();
//                data.put("TEN",);
//                data.put("HINH",);
//                data.put("TENANH",);
//                data.put("MATKHAU",);
//                data.put("SINHNHAT",);
//                data.put("GIOTINH",);
//                data.put("EMAIL",);
//                data.put("SODIENTHOAI",);
//
//                return data;
//            }
//        };
//    }
}
