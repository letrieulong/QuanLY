package com.example.appsp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appsp.adapter.Adapternews;
import com.example.appsp.adapter.LoaiAdapter;
import com.example.appsp.fragment.InforFragment;
import com.example.appsp.model.Loaisp;
import com.example.appsp.model.Sanpham;
import com.example.appsp.model.Shopping;
import com.example.appsp.object.All_moreActivity;
import com.example.appsp.object.AoActivity;
import com.example.appsp.object.GioHang;
import com.example.appsp.object.Login;
import com.example.appsp.object.QuanActivity;
import com.example.appsp.until.Checkconnection;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtspnews, txtviewmore;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    Toolbar toolbar;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    UrlServer urlServer = new UrlServer();
    ArrayList<Loaisp> mangloaisp;
    ArrayList<Sanpham> mangsp;
    LoaiAdapter loaiAdapter;
    Adapternews adapternews;
    BottomNavigationView bottomNavigationView;
    public static ArrayList<Shopping> arrayshopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhxa();
        if(Checkconnection.haveNetworkConnection(this)){
            Actionbar();
            Acviewflipper();
            getDataloaisp();
            getbottomnavi();
            animi();
            getDatanews();
            customitemlistview();
            event();


        }else {
            Checkconnection.Show_toast(this,"Vui Lòng Kiểm Tra Lại Kết Nối Internet");
            finish();
        }
    }

    private void event() {
        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txtviewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, All_moreActivity.class));
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

    private void customitemlistview() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                switch (i){
                    case 0:
                        if(Checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            Checkconnection.Show_toast(getApplicationContext(), "Vui Lòng Kiểm Tra Lại Kết Nối Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case 1:
                        if (Checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, QuanActivity.class);
                            intent.putExtra("idloaisp", mangloaisp.get(i).getID());
                            startActivity(intent);
                        }else {
                            Checkconnection.Show_toast(getApplicationContext(), "Vui Lòng Kiểm Tra Lại Kết Nối Internet");
                        }
                        drawerLayout.openDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (Checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, AoActivity.class);
                            intent.putExtra("idloaisp", mangloaisp.get(i).getID());
                            startActivity(intent);
                        }else {
                            Checkconnection.Show_toast(getApplicationContext(), "Vui Lòng Kiểm Tra Lại Kết Nối Internet");
                        }
                        drawerLayout.openDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (Checkconnection.haveNetworkConnection(getApplicationContext())){
                            String phone = "0375753469";
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + phone));
                            startActivity(intent);
                        }else {
                            Checkconnection.Show_toast(getApplicationContext(), "Vui Lòng Kiểm Tra Lại Kết Nối Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void getbottomnavi() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemuer:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main, new InforFragment()).commit();

                        overridePendingTransition(0,0);
                        break;
                    case R.id.itemhome:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        overridePendingTransition(0,0);// cho phép các activi hiển thị trong main và vẫn xuất hiện bottomnavi
                        break;
                    default:break;
                }
                return true;
            }
        });
    }

    private void animi() {
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slidemain);
//        txtspnews.startAnimation(animation);
    }

    private void Acviewflipper() {
        Sanpham sp = new Sanpham();
        ArrayList<String> quangcao = new ArrayList<>();
        quangcao.add("https://vn-test-11.slatic.net/p/201f41bb6d3fb65f9f12bed2e6d6eaed.jpg_400x400q90.jpg_.webp");
        quangcao.add("https://vn-test-11.slatic.net/p/041b2bae03ffdf26f981d5b20b98bb84.jpg_400x400q90.jpg_.webp");
        quangcao.add("https://vn-test-11.slatic.net/p/7115e98628652b0b393bf3d766a9c435.jpg_400x400q90.jpg_.webp");
        quangcao.add("https://vn-test-11.slatic.net/p/a32ea99c0d85e970208c7b985dd8b61f.jpg_400x400q90.jpg_.webp");
        for (int i = 0 ; i < quangcao.size(); i++){
            ImageView imageView = new ImageView(this);
            Glide.with(this).load(quangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY); // sét hình ảnh hiển thị full;
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(this,R.anim.slide_right_in);
        Animation animation_slide_out = AnimationUtils.loadAnimation(this,R.anim.slide_right_out);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void anhxa() {
        viewFlipper     = findViewById(R.id.viewflipper);
        recyclerView    = findViewById(R.id.recymain);
        toolbar         = findViewById(R.id.toolbarchinh);
        navigationView  = findViewById(R.id.navigationview);
        listView        = findViewById(R.id.listview);
        drawerLayout    = findViewById(R.id.drawerlayout);
        bottomNavigationView = findViewById(R.id.bottomnavi);
        txtspnews       = findViewById(R.id.txtspnews);
        txtviewmore     = findViewById(R.id.txtmore);

        mangloaisp = new ArrayList<>();
        mangloaisp.add(0, new Loaisp(0, "Trang Chính", ""));
        loaiAdapter = new LoaiAdapter(mangloaisp, this);
        listView.setAdapter(loaiAdapter);

        mangsp = new ArrayList<>();
        adapternews = new Adapternews(mangsp, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapternews);

        if(arrayshopping != null){

        }else{
            arrayshopping = new ArrayList<>();
        }
    }

    public void getDatanews(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, UrlServer.newsp, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
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
                                adapternews.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Kết Nối Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                });
        Volley.newRequestQueue(this).add(request);
    }

    public void getDataloaisp(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlServer.loaisanpham, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);

                                Loaisp loaisp = new Loaisp();

                                loaisp.setID(object.getInt("id"));
                                loaisp.setHinhLoai(object.getString("hinhloaisp"));
                                loaisp.setLoaisp(object.getString("tenloaisp"));

                                mangloaisp.add(loaisp);
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
}