package com.example.appsp.object;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsp.R;
import com.example.appsp.UrlServer;
import com.example.appsp.adapter.DataAdapter;
import com.example.appsp.model.Sanpham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class All_moreActivity extends AppCompatActivity {

    ArrayList<Sanpham> mangsp;
    DataAdapter dataAdapter;
    RecyclerView recyclerView;
    Toolbar toolbar;
    EditText edtsearch;
    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_more);

        anhxa();
        search();
        ActiontoolBar();
        getdata();

    }

    private void anhxa() {
        recyclerView = findViewById(R.id.recycler_more);
        toolbar      = findViewById(R.id.toolbarall_more);
        autoCompleteTextView = findViewById(R.id.autoComplete);
        edtsearch    = findViewById(R.id.edtsearch);

        mangsp = new ArrayList<>();
        dataAdapter = new DataAdapter(this, mangsp);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(dataAdapter);



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
//
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
                                dataAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(All_moreActivity.this, "Kết Nối Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(this).add(request);
    }

    private void search(){
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int i1, int i2) {
                dataAdapter.filter(s);
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}