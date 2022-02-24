package com.example.appsp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.resource.gif.GifBitmapProvider;
import com.example.appsp.MainActivity;
import com.example.appsp.R;
import com.example.appsp.adapter.Adapter_ListSP;
import com.example.appsp.model.Sanpham;
import com.example.appsp.object.Upload;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListSPFragment extends Fragment {
    RecyclerView recycler_listsp;
    Adapter_ListSP adapter_sp;
    List<Sanpham> list_sp = new ArrayList<>();
    View view;
    public ListSPFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_s_p, container, false);
        anhxa();

        adapter_sp = new Adapter_ListSP(getContext(), list_sp);
        GridLayoutManager layoutManager = new GridLayoutManager( getActivity(),3, GridLayoutManager.VERTICAL, false);
        recycler_listsp.setLayoutManager(layoutManager);
        recycler_listsp.setAdapter(adapter_sp);

        GetData();
        registerForContextMenu(recycler_listsp);
        return view;
    }

    public void anhxa(){
        recycler_listsp = view.findViewById(R.id.recycler_listsp);
    }

    public void GetData() {
        String url = "http://192.168.1.8/sanpham/outlist.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                Sanpham sp = new Sanpham();

                                sp.setID(object.getInt("ID"));
                                sp.setImage(object.getString("Urlimg"));
                                sp.setName(object.getString("NameSP"));
                                sp.setPrice(object.getString("Price"));

                                list_sp.add(sp);
                                adapter_sp.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Main", error.toString());
                    }
                });
        Volley.newRequestQueue(getActivity()).add(request);
    }
}

