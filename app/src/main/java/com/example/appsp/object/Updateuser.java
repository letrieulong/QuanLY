package com.example.appsp.object;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appsp.R;
import com.example.appsp.UrlServer;
import com.example.appsp.fragment.InforFragment;
import com.example.appsp.model.User;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Updateuser extends AppCompatActivity {
    EditText txtname, txtnamelog, txtbirtday, txtemail, txtphone, edtpass;
    Button btncancle, btnupdate, btnedit;
    RadioButton radnam, radnu;
    int Request_Code_iamge  = 1;
    Bitmap bitmap;
    CircleImageView imguserup;
    CardView cardviewimg;
    RadioGroup rd;
    String value;


    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuser);

        init();
        event();
    }

    private void event() {
        imguserup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectimg();
            }
        });

        cardviewimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectimg();
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkra = rd.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(checkra);
                StringRequest request = new StringRequest(Request.Method.POST, UrlServer.updateprofile,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Updateuser.this, response, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Updateuser.this, ProfileActivity.class));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Updateuser.this, "Kết nói thất bại", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();


                        int nameimg = 0;
                        int sonn = 9999999;
                        Random rd = new Random();
                        nameimg = rd.nextInt(sonn);
                        String image = getStringimage(bitmap);

                        data.put("TEN", txtnamelog.getText().toString().trim());
                        data.put("ID", String.valueOf(id));
                        data.put("HINH",image);
                        data.put("TENANH",nameimg + "");
                        data.put("MATKHAU",edtpass.getText().toString().trim());
                        data.put("SINHNHAT",txtbirtday.getText().toString().trim());
                        data.put("GIOTINH",radioButton.getText().toString().trim());
                        data.put("EMAIL",txtemail.getText().toString().trim());
                        data.put("SODIENTHOAI",txtphone.getText().toString().trim());

                        return data;
                    }
                };
                Volley.newRequestQueue(Updateuser.this).add(request);
            }
        });
    }

    public void onClick(View v) {
        // get selected radio button from radioGroup
        int selectedId = rd.getCheckedRadioButtonId();
        RadioButton radioButton;
        // find the radiobutton by returned id
        radioButton = findViewById(selectedId);
        value = radioButton.getText().toString();
    }

    public String getStringimage(Bitmap bm){
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, ba);
        byte[] imagebutye = ba.toByteArray();
        String encode = Base64.encodeToString(imagebutye, Base64.DEFAULT);
        return encode;
    }

    private void selectimg(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);// Action_pick
        startActivityForResult(intent, Request_Code_iamge);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Request_Code_iamge && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imguserup.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init(){

        txtname       = findViewById(R.id.edtfullname);
        txtnamelog    = findViewById(R.id.edtusername);
        txtbirtday    = findViewById(R.id.edtbirtday);
        txtemail      = findViewById(R.id.edtemail);
        txtphone      = findViewById(R.id.edtphone);
        btncancle     = findViewById(R.id.btncancle);
        btnupdate     = findViewById(R.id.btnupdate);
        btnedit       = findViewById(R.id.btnedit);
        radnam        = findViewById(R.id.radnam);
        radnu         = findViewById(R.id.radnu);
        imguserup     = findViewById(R.id.imguserup);
        rd            = findViewById(R.id.radgroup);
        cardviewimg   = findViewById(R.id.cardviewimg);
        edtpass       = findViewById(R.id.edtpass);

        Intent intent = getIntent();
        User u = (User) intent.getSerializableExtra("key");
        id = u.getID();

        txtname.setText(u.getUsername());
        txtnamelog.setText(u.getFullname());
        txtbirtday.setText(u.getBirthday());
        txtemail.setText(u.getEmail());
        txtphone.setText(u.getPhone());
        edtpass.setText(u.getPassword());
        Glide.with(this)
                .load(u.getUrlimg())
                .placeholder(R.drawable.noimage)
                .into(imguserup);
    }

}