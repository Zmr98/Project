package com.example.lms.atipastpapers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lms.R;

public class PastPapers extends AppCompatActivity {

    ImageView backBtn;
    Button hndaBtn, hndbaBtn, hndbfBtn, hndeBtn, hnditBtn, hndthmBtn, hndmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_papers);

        //Tool bar hide
        getSupportActionBar().hide();

        //hooks
        backBtn = findViewById(R.id.back_pressed);
        hndaBtn = (Button) findViewById(R.id.hndabtn);
        hndbaBtn = (Button) findViewById(R.id.hndbabtn);
        hndbfBtn = (Button) findViewById(R.id.hndbfbtn);
        hndeBtn = (Button) findViewById(R.id.hndebtn);
        hnditBtn = (Button) findViewById(R.id.hnditbtn);
        hndthmBtn = (Button) findViewById(R.id.hndthmbtn);
        hndmBtn = (Button) findViewById(R.id.hndmbtn);

        //going back to the previous activity
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PastPapers.super.onBackPressed();
            }
        });

        hndaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PastPapers.this, "Uploading Soon...", Toast.LENGTH_SHORT).show();
            }
        });

        hndbaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PastPapers.this, "Uploading Soon...", Toast.LENGTH_SHORT).show();
            }
        });

        hndbfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PastPapers.this, "Uploading Soon...", Toast.LENGTH_SHORT).show();
            }
        });

        hndeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PastPapers.this, "Uploading Soon...", Toast.LENGTH_SHORT).show();
            }
        });

        hnditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PastPapers.this, ItPastPapers.class));
            }
        });

        hndthmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PastPapers.this, "Uploading Soon...", Toast.LENGTH_SHORT).show();
            }
        });

        hndmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PastPapers.this, "Uploading Soon...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
