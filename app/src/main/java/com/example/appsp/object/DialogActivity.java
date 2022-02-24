package com.example.appsp.object;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appsp.R;

import java.text.DecimalFormat;

public class DialogActivity extends Dialog {

    ImageView imageView;
    TextView txtgia;

    Spinner spinner;
    Button btnadd;

    Integer [] soluong = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public DialogActivity(@NonNull Context context) {
        super(context);
    }

    public DialogActivity(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        anhxa();
    }

    private String numbercurrenFormat(String number){
        DecimalFormat format = new DecimalFormat("###,###,###");
        return format.format(Double.parseDouble(number));
    }

    private void anhxa() {
        spinner     = findViewById(R.id.spinner);
        imageView   = findViewById(R.id.imgaddview);
        btnadd      = findViewById(R.id.btnaddnew);
        txtgia      = findViewById(R.id.txtviewgia);

    }
}