package com.example.lms.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.lms.user.MainActivity;
import com.example.lms.R;
import com.example.lms.admin.adminactivity.AdminLogin;

public class SelectLoginOption extends AppCompatActivity {

    Button btnAdmin, btnStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow() .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_login_option);
        getSupportActionBar().hide();

        btnAdmin = (Button) findViewById(R.id.btnAdmin);
        btnStudent = (Button) findViewById(R.id.btnStudent);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SelectLoginOption.this, AdminLogin.class));

            }
        });

        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SelectLoginOption.this, MainActivity.class));

            }
        });

    }

}