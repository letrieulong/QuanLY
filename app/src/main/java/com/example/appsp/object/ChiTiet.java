package com.example.appsp.object;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appsp.MainActivity;
import com.example.appsp.R;
import com.example.appsp.model.Sanpham;
import com.example.appsp.model.Shopping;
import com.example.appsp.until.Checkconnection;

import java.text.DecimalFormat;

public class ChiTiet extends AppCompatActivity {
    TextView txtttensp, txtpricesp, txtcontent, txtfabric, txtorigin, txtsize;
    ImageView imgviewct;
    ImageButton btnshopping;
    Toolbar toolbar;
    Button btnmua;

    int id =0;
    String tenchitiet = "";
    int giachitiet = 0;
    String hinhchitiet = "";
    String motact= "";
    int idsp =0;

    Spinner spinner;
    Integer [] soluong = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);

        anhxa();
        if (Checkconnection.haveNetworkConnection(this)){
            Eventbutton();
            Actionbar();
            Datashow();
        }else {
            Checkconnection.Show_toast(this, "Vui Lòng Kiểm Tra Lại Internet");
        }
    }

    private void Datashow() {
        Intent i = getIntent();
        Bundle data = i.getExtras();
        Sanpham sp = (Sanpham) data.getSerializable("image");

        txtsize.setText(sp.getSize());
        txtfabric.setText(sp.getChatLieu());
        txtorigin.setText(sp.getXuatxu());
        txtcontent.setText(sp.getMota());
        txtttensp.setText(sp.getName());
        txtpricesp.setText(numbercurrenFormat(sp.getPrice()) + "đ");


        giachitiet = Integer.parseInt(sp.getPrice());
        tenchitiet = String.valueOf(sp.getName());

        id = Integer.valueOf(sp.getID());
        hinhchitiet = sp.getImage();
        Uri uri = Uri.parse(sp.getImage());
        Glide.with(this)
                .load(sp.getImage())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgviewct);
        imgviewct.setImageURI(uri);
    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    private String numbercurrenFormat(String number){
        DecimalFormat format = new DecimalFormat("###,###,###");
        return format.format(Double.parseDouble(number));
    }

    private void Eventbutton() {
        btnshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.chitiet, new AddFragment()).commit();
                    addnew();
//                dialog();
            }
        });

        btnmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnew();
                startActivity(new Intent(ChiTiet.this, GioHang.class));
            }
        });

    }
    private void addnew() {
        if(MainActivity.arrayshopping.size() > 0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.arrayshopping.size(); i++){
                        if(MainActivity.arrayshopping.get(i).getIdsp() == id){
                            MainActivity.arrayshopping.get(i).setSoluongsp(MainActivity.arrayshopping.get(i).getSoluongsp() + sl);
                            if(MainActivity.arrayshopping.get(i).getSoluongsp() >= 10){
                                MainActivity.arrayshopping.get(i).setSoluongsp(10);
                            }
                            MainActivity.arrayshopping.get(i).setGiasp(giachitiet * MainActivity.arrayshopping.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        int giamoi = soluong * giachitiet;

                        MainActivity.arrayshopping.add(new Shopping(id,tenchitiet, giamoi, hinhchitiet, soluong));
                    }
                }else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());

                    int giamoi = giachitiet * soluong;

                    MainActivity.arrayshopping.add(new Shopping(id,tenchitiet, giamoi, hinhchitiet, soluong));
                }
                Toast.makeText(ChiTiet.this, "Thêm Vào Giỏ Hàng Thành Công", Toast.LENGTH_LONG).show();
    }

//    private void dialog(){
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.fragment_add);
//
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//
//        dialog.getWindow().setAttributes(lp);
//        dialog.show();
//    }

    private void anhxa() {
        imgviewct   = findViewById(R.id.imgviewct);
        txtpricesp  = findViewById(R.id.txtprice_spct);
        txtttensp   = findViewById(R.id.txttenspct);
        btnshopping = findViewById(R.id.btnshopping);
        spinner     = findViewById(R.id.spinner);
        toolbar     = findViewById(R.id.toolbarct);
        txtcontent  = findViewById(R.id.txtcontent);
        txtfabric   = findViewById(R.id.txtfabric);
        txtorigin   = findViewById(R.id.txtorigin);
        txtsize     = findViewById(R.id.txtsize);
        btnmua      = findViewById(R.id.btnmua);

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, soluong);
        spinner.setAdapter(arrayAdapter);
    }
}