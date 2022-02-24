package com.example.appsp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appsp.R;

public class DialogFragment extends Fragment {

    View view;
    TextView txthelp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dialog, container, false);
        init();
        return view;
    }

    private void init() {
        txthelp = view.findViewById(R.id.txthelp);
    }
}