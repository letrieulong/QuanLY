package com.example.appsp.object;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsp.R;
import com.example.appsp.UrlServer;
import com.example.appsp.adapter.Adapterorder;
import com.example.appsp.model.OrderList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class List_order_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapterorder adapterorder;
    List<OrderList> orlist;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        init();
        getDatanews();
        setActionBar();
    }

    private void setActionBar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("Danh Sách Sản Phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        toolbar  = findViewById(R.id.toolbar);
        orlist = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclert_frag_layout1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterorder = new Adapterorder(orlist, this);
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
                        Toast.makeText(List_order_Activity.this, "Kết Nối Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(this).add(request);
    }
}