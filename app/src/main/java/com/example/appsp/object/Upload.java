package com.example.appsp.object;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appsp.MainActivity;
import com.example.appsp.R;
import com.example.appsp.UrlServer;
import com.example.appsp.adapter.Adapter_ListSP;
import com.example.appsp.adapter.LoaiAdapter;
import com.example.appsp.fragment.ListSPFragment;
import com.example.appsp.model.Loaisp;
import com.example.appsp.model.Sanpham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Upload extends AppCompatActivity implements View.OnClickListener {

    Button btnupdate, btncancle;
    EditText edttensp, edtpricesp, edtchatlieu, edtxuatxu, edtnoidung, edt_size;
    public static TextView txttilte;
    ImageView imgchose, img_ct;
    List<Sanpham> listdata = new ArrayList<>();
    Adapter_ListSP adapter_listSP = new Adapter_ListSP(this, listdata);
    int Request_Code_iamge = 1;
    Bitmap bitmap;
    Spinner spinner;
    ArrayList<Loaisp> arrloaisp;
    LoaiAdapter loaiAdapter;
    UrlServer urlServer;
    String test = "";
    Sanpham sp = new Sanpham();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        anhxa();
        imgchose.setOnClickListener(this);
        btnupdate.setOnClickListener(this);
        btncancle.setOnClickListener(this);
    }

    private void anhxa() {
        adapter_listSP.notifyDataSetChanged();

        btnupdate      = findViewById(R.id.btn_update);
        btncancle   = findViewById(R.id.btn_cancle);

        edttensp    = findViewById(R.id.edttensp);
        edtpricesp  = findViewById(R.id.edtpricesp);
        edtchatlieu = findViewById(R.id.edtmaterial);
        edtxuatxu   = findViewById(R.id.edtorigin);
        edtnoidung  = findViewById(R.id.edtcontent);
        edt_size    = findViewById(R.id.edt_size);
        txttilte    = findViewById(R.id.txttilte);
        imgchose    = findViewById(R.id.choeseimg);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        sp = (Sanpham) b.getSerializable("data");

        edttensp.setText(sp.getName());
        edtpricesp.setText(sp.getPrice());
        edtchatlieu.setText(sp.getChatLieu());
        edtnoidung.setText(sp.getMota());
        edtxuatxu.setText(sp.getXuatxu());
        edt_size.setText(sp.getSize());
        Glide.with(this).load(sp.getImage()).into(imgchose);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                test = String.valueOf(spinner.getItemAtPosition(position).toString().charAt(0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] items = new String[]{"1 - Áo", "2 - Quần"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choeseimg:
                selectimg();
                break;
            case R.id.btn_update:
                update();
                adapter_listSP.notifyDataSetChanged();
                imgchose.setImageDrawable(getDrawable(R.drawable.noimage));
                break;
            case R.id.spinner:
                Listloaisp();
                break;
            case R.id.btn_cancle:
                dialog();
                break;
            default:
                break;
        }
    }

    private void dialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Thông báo").setMessage("Bạn có chắc muốn hủy!!!");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Upload.this, SanPhamActivity.class));
            }
        });
        alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void Listloaisp(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlServer.loaisanpham, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);

                                Loaisp loaisp = new Loaisp();

                                loaisp.setID(object.getInt("id"));

                                arrloaisp.add(loaisp);
                                loaiAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(request);
    }

    private void update() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlServer.update,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Upload.this, response, Toast.LENGTH_SHORT).show();
                        adapter_listSP.notifyDataSetChanged();
                        startActivity(new Intent(Upload.this, SanPhamActivity.class));
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
                int nameimg  = 0;
                int nameimg1 = 0;
                int sonn = 999999999;
                String namesp = edttensp.getText().toString().trim();
                String image  = getStringimage(bitmap);

                String price  = edtpricesp.getText().toString().trim();

                String origin   = edtxuatxu.getText().toString().trim();
                String Material = edtchatlieu.getText().toString().trim();
                String Content  = edtnoidung.getText().toString().trim();
                String Size  = edt_size.getText().toString().trim();

                Random rd = new Random();
                nameimg = (rd.nextInt(sonn));
                Map<String, String> param = new HashMap<String, String>();
                //bảng sản phẩm
                param.put("TENSP", namesp);
                param.put("TEN", nameimg + "");
                param.put("HINH", image);
                param.put("GIA", price);
                param.put("MOTA", Content);
                param.put("IDSP", test);
                ///bảng chi tiết
                param.put("XUATXU", origin);
                param.put("CHATLIEU", Material);
                param.put("KICHTHUOC", Size);
                param.put("ID", String.valueOf(sp.getID()));

                return param;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

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
                imgchose.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}