package com.example.appsp.object;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appsp.MainActivity;
import com.example.appsp.R;
import com.example.appsp.adapter.Giohangadapter;
import com.example.appsp.fragment.OrderFragment;
import com.example.appsp.until.Checkconnection;

import java.text.DecimalFormat;

public class GioHang extends AppCompatActivity {

    ListView listView;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnthanhtoan, btntieptuc;
    Toolbar toolbar;
    Giohangadapter giohangadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        anhxa();
        if(Checkconnection.haveNetworkConnection(this)){
            Functiontoolbar();
            Checkdata();
            EventUtil();
            Order();
            Continueorder();
            Onitemclick();
        }else {
            Checkconnection.Show_toast(this, "Vui Lòng Kiểm Tra Lại Internet");
        }
    }

    private void Onitemclick() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Thông Báo").setMessage("Bạn Có Chắc Muốn Xóa Sản Phẩm Này ?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.arrayshopping.size() < 0) {
                            txtthongbao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.arrayshopping.remove(position);
                            giohangadapter.notifyDataSetChanged();
                            EventUtil();
                            if (MainActivity.arrayshopping.size() <= 0){
                                txtthongbao.setVisibility(View.VISIBLE);
                            }else {
                                txtthongbao.setVisibility(View.INVISIBLE);
                                giohangadapter.notifyDataSetChanged();
                                EventUtil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangadapter.notifyDataSetChanged();
                        EventUtil();
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    private void Order() {
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arrayshopping.size() > 0){
                    getSupportFragmentManager().beginTransaction().replace(R.id.giohang, new OrderFragment()).commit();
                }else {
                    Toast.makeText(GioHang.this, "Giỏ Hàng Đang Trống !!! Vui Lòng Quay Lại Sau", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Continueorder() {
        btntieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GioHang.this, MainActivity.class));
            }
        });
    }

    public static void EventUtil() {
        int tongtien = 0;
        for (int i = 0 ;i < MainActivity.arrayshopping.size(); i++){
            tongtien += MainActivity.arrayshopping.get(i).getGiasp();
        }
        txttongtien.setText(numbercurrenFormat(tongtien) + "đ");
    }
    public static String numbercurrenFormat(int number){
        DecimalFormat format = new DecimalFormat("###,###,###");
        return format.format((number));
    }
    private void Checkdata() {
        if (MainActivity.arrayshopping.size() <= 0){
            giohangadapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }else {
            giohangadapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    private void Functiontoolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        listView        = findViewById(R.id.listviewgiohang);
        txtthongbao     = findViewById(R.id.txtgiohangnull);
        txttongtien     = findViewById(R.id.txttongtiengh);
        btnthanhtoan    = findViewById(R.id.btnthanhtoan);
        btntieptuc      = findViewById(R.id.btncontinue);
        toolbar         = findViewById(R.id.toolbargiohang);

        btnthanhtoan.setText("Đặt Hàng");

        giohangadapter = new Giohangadapter(this, MainActivity.arrayshopping);
        listView.setAdapter(giohangadapter);
    }
}