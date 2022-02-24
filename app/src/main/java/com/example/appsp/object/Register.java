package com.example.appsp.object;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appsp.R;
import com.example.appsp.UrlServer;
import com.example.appsp.until.Checkconnection;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity {

    EditText edtfullname, edtacount, edtpass, edtrepass;
    Button btnregister;
    TextView txtlogin;
    CircleImageView img_register;
    Bitmap bitmap;
    int Request_Code_iamge  = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        if(Checkconnection.haveNetworkConnection(this)){
            setOnclickregister();
            setOnclicklogin();
            setchosesimg();
        }else {
            Checkconnection.Show_toast(this, "Vui Lòng Kiểm Tra Lại Kết Nối Internet");
        }
    }

    private void setchosesimg() {
        img_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectimg();
            }
        });
    }

    private void setOnclicklogin() {
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    private void setOnclickregister() {
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Request_Code_iamge && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img_register.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getData(){
        String fullname = edtfullname.getText().toString().trim();
        String acount = edtacount.getText().toString().trim();
        String pass = edtpass.getText().toString().trim();
        String repass = edtrepass.getText().toString().trim();
        if (!pass.equals(repass)){
            Toast.makeText(this, "Mật Khẩu Không Trùng Khớp", Toast.LENGTH_SHORT).show();
        }else
            if(!fullname.equals("") && !acount.equals("") && !pass.equals("") && !repass.equals("")){
                StringRequest request = new StringRequest(Request.Method.POST, UrlServer.register, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                        clearbox();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this, "Kết Nối Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        int nameimg  = 0;
                        int sonn = 999999999;
                        Random rd = new Random();
                        nameimg = rd.nextInt(sonn);
                        String image = getStringimage(bitmap);
                        Map<String,String> data = new HashMap<String, String>();
                        data.put("FULLNAME",fullname);
                        data.put("USERNAME",acount);
                        data.put("PASSWORD",pass);
                        data.put("NAMEIMG",nameimg + "");
                        data.put("HINH",image);
                        return data;
                    }
                };
                Volley.newRequestQueue(this).add(request);
            }
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

    public void init(){
        edtacount     = findViewById(R.id.edtacount);
        edtfullname   = findViewById(R.id.edtfullname);
        edtpass       = findViewById(R.id.edtpass);
        edtrepass     = findViewById(R.id.edtrepass);
        btnregister   = findViewById(R.id.btnregister);
        img_register  = findViewById(R.id.profile_image);
        txtlogin      = findViewById(R.id.txtformlogin);
    }

    private void clearbox(){
        edtacount.setText(" ");
        edtfullname.setText(" ");
        edtpass.setText(" ");
        edtrepass.setText(" ");
        img_register.setImageResource(R.drawable.noimage);
    }
}