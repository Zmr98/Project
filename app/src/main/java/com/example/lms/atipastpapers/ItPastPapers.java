package com.example.lms.atipastpapers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lms.R;
import com.example.lms.atipastpapers.sem1.SelectYears;

public class ItPastPapers extends AppCompatActivity {

    Button itBtn1, itBtn2, itBtn3, itBtn4;
    ImageView itbackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_it_past_papers);

        //Tool bar hide
        getSupportActionBar().hide();

        //hooks
        itbackBtn = (ImageView) findViewById(R.id.itback_pressed);
        itBtn1 = (Button) findViewById(R.id.itexped1);
        itBtn2 = (Button) findViewById(R.id.itexped2);
        itBtn3 = (Button) findViewById(R.id.itexped3);
        itBtn4 = (Button) findViewById(R.id.itexped4);

        //going back to the previous activity
        itbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItPastPapers.super.onBackPressed();
            }
        });

        //btn1
        itBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItPastPapers.this, SelectYears.class));
            }
        });

        itBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItPastPapers.this, com.example.lms.atipastpapers.sem2.SelectYears.class));
            }
        });

        itBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItPastPapers.this, com.example.lms.atipastpapers.sem3.SelectYears.class));
            }
        });

        itBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ItPastPapers.this, "Upload Soon", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
