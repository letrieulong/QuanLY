package com.example.appsp.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsp.MainActivity;
import com.example.appsp.R;
import com.example.appsp.UrlServer;
import com.example.appsp.object.GioHang;
import com.example.appsp.object.Login;
import com.example.appsp.until.Checkconnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderFragment extends Fragment {

    View view;
    Button btnttorder, btncancle;
    EditText edtnameor, edtphoneor, edtemailor, edtaddressor;
    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order, container, false);
        anhxa();
        Order();
        Cancle();
        return view;
    }

    private void Cancle() {
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông Báo").setMessage("Bạn Có Chắc Hủy Quá Trình Thanh Toán !!!");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(), GioHang.class));
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void Order() {
        btnttorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone   = edtphoneor.getText().toString().trim();
                String email   = edtemailor.getText().toString().trim();
                String address = edtaddressor.getText().toString().trim();
                String name    = edtnameor.getText().toString().trim();
                    if(phone.equals("")){
                        edtphoneor.setError("Vui Lòng Nhập Số Điện Thoại");
                    }
                    if(address.equals("")){
                        edtaddressor.setError("Vui Lòng Nhập Địa Chỉ Giao Hàng");
                    }
                    if(name.equals("")){
                        edtnameor.setError("Vui Lòng Nhập Họ Tên");
                    }
                    if(email.equals("")){
                        edtemailor.setError("Vui Lòng Nhập Email");
                    }else
                        if (!Patterns.EMAIL_ADDRESS.matcher(edtemailor.getText()).matches()){
                            edtemailor.setError("Vui Lòng Nhập Email Chính Xác !!!");
                        }else {
                            getdata();
                            //Toast.makeText(getActivity(), "Thanh Toán Thành Công", Toast.LENGTH_SHORT).show();
                        }
                }
        });
    }

    public void anhxa(){
        btncancle    = view.findViewById(R.id.btncancleor);
        btnttorder   = view.findViewById(R.id.btnttorder);

        edtnameor    = view.findViewById(R.id.edtnameorder);
        edtphoneor   = view.findViewById(R.id.edtphoneorder);
        edtemailor   = view.findViewById(R.id.edtemailorder);
        edtaddressor = view.findViewById(R.id.edtaddressorder);
    }

    public void getdata(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlServer.donhang,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String madonhang) {
                       Log.d("main", madonhang);

                       if(Integer.parseInt(madonhang) > 0){
                           StringRequest request = new StringRequest(Request.Method.POST, UrlServer.chitiet,
                                   new Response.Listener<String>() {
                                       @Override
                                       public void onResponse(String response) {
                                            if(response.equalsIgnoreCase("1")){
                                                MainActivity.arrayshopping.clear();
                                                Toast.makeText(getContext(), "Thanh Toán Thành Công", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getActivity(), MainActivity.class));
                                                Toast.makeText(getContext(), "Mời Bạn Tiếp Tục Mua Hàng", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Checkconnection.Show_toast(getActivity(), "Dữ Liệu Bị Lỗi");
                                            }
                                       }
                                   },
                                   new Response.ErrorListener() {
                                       @Override
                                       public void onErrorResponse(VolleyError error) {

                                       }
                                   }){
                               @Nullable
                               @Override
                               protected Map<String, String> getParams() throws AuthFailureError {
                                   Date datetiem = new Date();
                                   SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss 'Ngày' dd-MM-yyyy");
                                   String date = ft.format(datetiem);
                                   Log.d("year",date);
                                   // chuyển dữ liệu sang json;
                                   JSONArray jsonArray = new JSONArray();
                                   for (int i = 0; i < MainActivity.arrayshopping.size(); i++){
                                       JSONObject object = new JSONObject();
                                       try {
                                           object.put("madonhang", madonhang);
                                           object.put("masanpham", MainActivity.arrayshopping.get(i).getIdsp());
                                           object.put("tensanpham", MainActivity.arrayshopping.get(i).getTensp());
                                           object.put("giasanpham", MainActivity.arrayshopping.get(i).getGiasp());
                                           object.put("soluongsp", MainActivity.arrayshopping.get(i).getSoluongsp());
                                           object.put("hinhanh", MainActivity.arrayshopping.get(i).getImgsp());
                                           object.put("dateorder", date);
                                       } catch (JSONException e) {
                                           e.printStackTrace();
                                       }
                                       jsonArray.put(object); // đưa dữ liệu vào jsonarray;
                                   }
                                   Map<String, String> data = new HashMap<String, String>();
                                   data.put("json", jsonArray.toString()); // gửi chuổi jsonarray lên server;
                                   return data;
                               }
                           };
                           Volley.newRequestQueue(getContext()).add(request);
                       }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String tenkh = edtnameor.getText().toString().trim();
                String phone = edtphoneor.getText().toString().trim();
                String email = edtemailor.getText().toString().trim();
                String adres = edtaddressor.getText().toString().trim();

                Map<String, String> data = new HashMap<String, String>();
                data.put("tenkhachhang", tenkh);
                data.put("sodienthoai", phone);
                data.put("email", email);
                data.put("address", adres);
//                data.put("dateorder", "dá");
                return data;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}