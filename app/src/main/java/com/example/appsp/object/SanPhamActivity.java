package com.example.appsp.object;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsp.R;
import com.example.appsp.UrlServer;
import com.example.appsp.adapter.DataAdapter;
import com.example.appsp.adapter.SanphamAdapter;
import com.example.appsp.model.Sanpham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SanPhamActivity extends AppCompatActivity {
    ArrayList<Sanpham> mangsp;
    SanphamAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);

        anhxa();
        getdata();
        adapter.notifyDataSetChanged();
    }

    private void anhxa() {
        recyclerView = findViewById(R.id.recycsanpham);

        mangsp = new ArrayList<>();
        adapter = new SanphamAdapter(this, mangsp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void getdata(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, UrlServer.listmoresp, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for(int i = 0; i < response.length(); i ++) {
                                JSONObject object = response.getJSONObject(i);

                                Sanpham sp = new Sanpham();

                                sp.setID(object.getInt("id"));
                                sp.setName(object.getString("tensp"));
                                sp.setPrice(object.getString("giasp"));
                                sp.setMota(object.getString("motasp"));
                                sp.setSize(object.getString("kichthuoc"));
                                sp.setChatLieu(object.getString("chatlieu"));
                                sp.setXuatxu(object.getString("xuatxu"));
                                sp.setImage(object.getString("urlimg"));

                                mangsp.add(sp);
                                adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SanPhamActivity.this, "Kết Nối Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(this).add(request);
    }

}