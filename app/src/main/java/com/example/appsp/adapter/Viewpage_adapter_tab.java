package com.example.appsp.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;


public class Viewpage_adapter_tab extends FragmentPagerAdapter {

    ArrayList<Fragment> fragmentslist = new ArrayList<>();
    ArrayList<String> fragmentTitle = new ArrayList<>();
    public Viewpage_adapter_tab(@NonNull FragmentManager fm, int behaviorResumeOnlyCurrentFragment) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int i) {
        return fragmentslist.get(i);
    }

    @Override
    public int getCount() {
        return fragmentslist.size();
    }

    public void addFragment(Fragment fragment, String title){
        fragmentslist.add(fragment);
        fragmentTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int i) {
        return fragmentTitle.get(i);
    }
}

