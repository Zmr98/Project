package com.example.lms.atipastpapers.sem3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lms.R;

public class SelectYears extends AppCompatActivity {

    Button itBtn1, itBtn2, itBtn3, itBtn4, itBtn5;
    ImageView itbackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_years3);

        //Tool bar hide
        getSupportActionBar().hide();

        //hooks
        itbackBtn = (ImageView) findViewById(R.id.itback_pressed);
        itBtn1 = (Button) findViewById(R.id.itexped1);
        itBtn2 = (Button) findViewById(R.id.itexped2);
        itBtn3 = (Button) findViewById(R.id.itexped3);
        itBtn4 = (Button) findViewById(R.id.itexped4);
        itBtn5 = (Button) findViewById(R.id.itexped5);

        //going back to the previous activity
        itbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectYears.super.onBackPressed();
            }
        });

        //btn1
        itBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectYears.this, Year2015.class));
            }
        });

        itBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectYears.this, Year2016.class));
            }
        });

        itBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectYears.this, Year2017.class));
            }
        });

        itBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectYears.this, Year2018.class));
            }
        });

    }
}
