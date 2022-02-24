package com.example.appsp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appsp.R;
import com.example.appsp.adapter.Viewpage_adapter_tab;
import com.example.appsp.model.User;
import com.example.appsp.object.Login;
import com.example.appsp.object.Updateuser;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class InforFragment extends Fragment{
    TabLayout tabLayout;
    ViewPager viewPager;
    public TextView txtfaginfor;
    ImageButton imgfraggh;
    Button btn1, btn2;
    TextView txtname;
    Viewpage_adapter_tab adapter_tab;
    View view;
    public InforFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_infor, container, false);

        anhxaa();
        addfragment();

        return view;
    }

    public void addfragment(){
        adapter_tab = new Viewpage_adapter_tab(getParentFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter_tab.addFragment(new AllinforFragment(), "Thông Tin");
        adapter_tab.addFragment(new Tap_1_Fragment(), "Mua Lại");
        viewPager.setAdapter(adapter_tab);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void anhxaa(){
        tabLayout       = view.findViewById(R.id.tablayoutfrag);
        viewPager       = view.findViewById(R.id.viewpagefrag);
        txtname     = view.findViewById(R.id.txtfaginfor);

        String name = Login.edtacount.getText().toString();
        txtname.setText(name);
    }


}