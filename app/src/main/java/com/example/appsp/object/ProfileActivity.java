package com.example.appsp.object;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsp.R;
import com.example.appsp.UrlServer;
import com.example.appsp.adapter.AdapteUSER;
import com.example.appsp.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    ArrayList<User> arrayuser;
    AdapteUSER adapteUSER;
    RecyclerView r;
    Toolbar toolbar;
    String keyname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        getActionbar();
        adapteUSER.notifyDataSetChanged();
        getdataaa();
    }

    private void getActionbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("Thông Tin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getdataaa() {
        keyname = Login.edtacount.getText().toString();
        Log.d("main", keyname);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, UrlServer.inforuser + keyname, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++){

                                JSONObject object = response.getJSONObject(i);

                                User u = new User();
                                u.setID(object.getInt("ID"));
                                u.setFullname(object.getString("fullname"));
                                u.setUsername(object.getString("usernamer"));
                                u.setPassword(object.getString("password"));
                                u.setNameimg(object.getString("nameimg"));
                                u.setUrlimg(object.getString("urlimage"));
                                u.setEmail(object.getString("email"));
                                u.setBirthday(object.getString("birthday"));
                                u.setPhone(object.getString("phone"));
                                u.setSet(object.getString("sex"));

                                arrayuser.add(u);
                                adapteUSER.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ProfileActivity.this, "Kết Nối Thất Bại", Toast.LENGTH_SHORT).show();

            }
        });
                Volley.newRequestQueue(this).add(request);

    }

    private void init(){
        toolbar = findViewById(R.id.toolbar);
        r = findViewById(R.id.recyprofile);
        arrayuser = new ArrayList<>();
        adapteUSER = new AdapteUSER(arrayuser, this);
        r.setLayoutManager(new LinearLayoutManager(this));
        r.setAdapter(adapteUSER);

    }
}