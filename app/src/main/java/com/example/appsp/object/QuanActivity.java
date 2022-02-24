package com.example.appsp.object;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsp.R;
import com.example.appsp.UrlServer;
import com.example.appsp.adapter.DataAdapter;
import com.example.appsp.model.Sanpham;
import com.example.appsp.until.Checkconnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuanActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    DataAdapter adapter;
    List<Sanpham> arraysanpham;
    int idquan = 0;
    int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan);

        anhxa();
        if(Checkconnection.haveNetworkConnection(this)){
            Getidloaisp();
            ActiontoolBar();
            Getdata(page);
        }else {
            Checkconnection.Show_toast(this, "Vui Lòng Kiểm Tra Lại Kết Nói Internet");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutoolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemshopping:
                Intent intent = new Intent(this, GioHang.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Getdata(int Page) {
        String url = UrlServer.urlquan + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response != null){
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    Sanpham sanpham = new Sanpham();

                                    sanpham.setID(object.getInt("id"));
                                    sanpham.setName(object.getString("tensp"));
                                    sanpham.setPrice(object.getString("giasp"));
                                    sanpham.setMota(object.getString("motasp"));
                                    sanpham.setChatLieu(object.getString("chatlieu"));
                                    sanpham.setSize(object.getString("kichthuoc"));
                                    sanpham.setXuatxu(object.getString("xuatxu"));
                                    sanpham.setImage(object.getString("urlimg"));
                                    sanpham.setMota(object.getString("idloaisp"));

                                    arraysanpham.add(sanpham);
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                HashMap<String, String> param = new HashMap<String, String>();

                param.put("idloaisp", String.valueOf(idquan));
                return param;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void ActiontoolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Getidloaisp() {
        idquan = getIntent().getIntExtra("idloaisp", -1);
        Log.d("main", idquan +"");
    }

    private void anhxa() {
        recyclerView = findViewById(R.id.recycler_view_quan);
        toolbar = findViewById(R.id.toolbarquan);

        arraysanpham = new ArrayList<>();
        adapter = new DataAdapter(this, arraysanpham);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
    }
}