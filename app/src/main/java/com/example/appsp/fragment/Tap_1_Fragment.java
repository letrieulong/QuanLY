package com.example.appsp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsp.MainActivity;
import com.example.appsp.R;
import com.example.appsp.UrlServer;
import com.example.appsp.adapter.Adapterorder;
import com.example.appsp.model.OrderList;
import com.example.appsp.model.Sanpham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tap_1_Fragment extends Fragment {
    RecyclerView recyclerView;
    Adapterorder adapterorder;
    List<OrderList> orlist;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_lay_out_1, container,false);
        init();
        getDatanews();
        return view;
    }

    private void init() {
        orlist = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclert_frag_layout1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterorder = new Adapterorder(orlist, getActivity());
        recyclerView.setAdapter(adapterorder);
    }

    public void getDatanews(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, UrlServer.listdonhang, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);

                                OrderList or = new OrderList();

                                or.setId(object.getInt("id"));
                                or.setPricesp(object.getString("giasanpham"));
                                or.setQuantity(object.getString("soluongsp"));
                                or.setDateorder(object.getString("dateorder"));
                                or.setImage(object.getString("hinhanh"));
                                or.setNamesp(object.getString("tensanpham"));

                                orlist.add(or);
                                adapterorder.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Kết Nối Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(getActivity()).add(request);
    }
}
