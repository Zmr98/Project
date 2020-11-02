package com.example.lms.admin.adminactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lms.R;
import com.example.lms.common.StartupScreen;
import com.example.lms.user.Registration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {

    //Variables
    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView Info;
    private int counter = 5;
    private ProgressDialog progressDialog;
    private DatabaseReference ref;

    SharedPreferences sharedPreferences;

    public static final String fileName = "login";
    public static final String Username1 = "adminName";
    public static final String Password1 = "adminPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getSupportActionBar().hide();

        //Assigning all the Variables to ID
        Name = (EditText) findViewById(R.id.etAdmName);
        Password = (EditText) findViewById(R.id.etAdmPassword);
        Login = (Button) findViewById(R.id.btnAdmLogin);
        Info = (TextView) findViewById(R.id.tvAdmInfo);

        Info.setText("No of attempts remaining: 5");

        ref = FirebaseDatabase.getInstance().getReference().child("Admin");

        //Keep admin login
        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Username1)){
            Intent intent = new Intent(AdminLogin.this, AdminHome.class);
            intent.putExtra("adminIdNumber", sharedPreferences.getString(Username1,fileName));
            startActivity(intent);
            finish();
        }

        progressDialog = new ProgressDialog(this);

        //Checking the details when the login button clicks
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Name.getText().toString().equals("")){
                    Name.setError("Please Enter Email");
                }else if (Password.getText().toString().equals("")){
                    Password.setError("Please Enter Password");
                }else {

                    final String adminId = Name.getText().toString();
                    final String password = Password.getText().toString();

                    progressDialog.setMessage("Please wait till you are Login!");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    ref.child(adminId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                AdminProfile adminProfile = dataSnapshot.getValue(AdminProfile.class);

                                if (password.equals(adminProfile.getAdminPassword())) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(Username1,adminId);
                                    editor.putString(Password1,password);
                                    editor.commit();
                                    Toast.makeText(AdminLogin.this, "Login Successful...", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(AdminLogin.this, AdminHome.class);
                                    intent.putExtra("adminIdNumber", adminId);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(AdminLogin.this, "Enter Correct Password...", Toast.LENGTH_SHORT).show();
                                    counter--;
                                    Info.setText("No of attempts remaining: " + counter);
                                    progressDialog.dismiss();
                                    if (counter == 0) {
                                        Login.setEnabled(false);
                                    }
                                }
                            } else {
                                Toast.makeText(AdminLogin.this, "Admin doesn't Exist...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    //Back button option
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(AdminLogin.this, StartupScreen.class));
                onBackPressed();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
