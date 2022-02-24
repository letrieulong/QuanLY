package com.example.appsp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapteCustomdialog extends RecyclerView.Adapter<AdapteCustomdialog.ViewHolder> {
//    List<User> users;
    Context context;
//
//    public AdapteCustomdialog(List<User> users, Context context) {
//        this.users = users;
//        this.context = context;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_custom_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {


//        holder.txtnamelog.setText(users.get(i).getFullname());
//        holder.txtname.setText(users.get(i).getUsername());
//        holder.txtemail.setText(users.get(i).getNameimg());
//        holder.txtphone.setText(users.get(i).getUrlimg());
//        holder.txtbirtday.setText(users.get(i).getPassword());


        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Login");
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Login", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // code for matching password
                String user = holder.etUsername.getText().toString();
                String pass = holder.etPassword.getText().toString();
                Toast.makeText(context, "Username: " + user + " Password: " + pass, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
//        Glide.with(context)
//                .load(users.get(i).getUrlimg())
//                .placeholder(R.drawable.noimage)
//                .error(R.drawable.error)
//                .into(holder.circleImageView);

//        holder.vv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle b = new Bundle();
//                b.putSerializable("image", users.get(i));
//                Intent intent = new Intent(context, ChiTiet.class);
//                intent.putExtras(b);
//                v.getContext().startActivity(intent);
//            }
//        });
//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slidenewmain);
//        holder.circleImageView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View vv;

        EditText etUsername, etPassword;
        CircleImageView circleImageView;
        TextView txtname, txtnamelog, txtbirtday, txtset, txtemail, txtphone;

        public ViewHolder(@NonNull View view) {
            super(view);
            etUsername    = view.findViewById(R.id.et_Username);
            etPassword    = view.findViewById(R.id.et_Password);
//            txtname       = view.findViewById(R.id.txtfullname);
//            txtnamelog    = view.findViewById(R.id.txtusername);
//            txtbirtday    = view.findViewById(R.id.txtbirtday);
//            txtset        = view.findViewById(R.id.txtset);
//            txtemail      = view.findViewById(R.id.txtemail);
//            txtphone      = view.findViewById(R.id.txtphone);
//            circleImageView      = view.findViewById(R.id.imguserfrag);
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
