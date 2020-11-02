package com.example.lms.adminPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lms.R;
import com.example.lms.admin.adminactivity.AdminProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewAdmin extends AppCompatActivity {

    private EditText adminName, adminPassword, adminEmail, adminDOB, adminRegistrationNumber;
    private Button adminUpdate, Backbtn;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_admin);

        getSupportActionBar().setTitle("Add Admin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();

        adminName = (EditText) findViewById(R.id.etAddAdminName);
        adminPassword = (EditText) findViewById(R.id.etAddAdminPassword);
        adminEmail = (EditText) findViewById(R.id.etAddAdminEmail);
        adminRegistrationNumber = (EditText) findViewById(R.id.etAddAdminRegNumber) ;
        adminUpdate = (Button) findViewById(R.id.btnAdminAdd);
        adminDOB = (EditText) findViewById(R.id.etAddAdminDOB);
        Backbtn = (Button) findViewById(R.id.btnAddBack);

        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AdminProfile adminProfile = dataSnapshot.getValue(AdminProfile.class);
                adminName.setText(adminProfile.getAdminName());
                adminDOB.setText(adminProfile.getAdminDOB());
                adminRegistrationNumber.setText(adminProfile.getAdminIdNumber());
                adminEmail.setText(adminProfile.getAdminEmail());
                adminPassword.setText(adminProfile.getAdminPassword());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(NewAdmin.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        adminUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child("Admin").child(adminRegistrationNumber.getText().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Toast.makeText(NewAdmin.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        String name = adminName.getText() .toString();
                        String dob = adminDOB.getText() .toString();
                        String regnumber = adminRegistrationNumber.getText() .toString();
                        String email = adminEmail.getText() .toString();
                        String password = adminPassword.getText() .toString();

                        AdminProfile adminProfile = new AdminProfile(dob, email, name, regnumber, password);
                        databaseReference.child("Admin").child(regnumber).setValue(adminProfile);

                        /*AdminProfile adminProfile = new AdminProfile();

                        adminProfile.setAdminName(adminName.getText() .toString().trim());
                        adminProfile.setAdminPassword(adminPassword.getText() .toString().trim());
                        adminProfile.setAdminEmail(adminEmail.getText() .toString().trim());
                        adminProfile.setAdminIdNumber(adminRegistrationNumber.getText() .toString().trim());
                        adminProfile.setAdminDOB(adminDOB.getText() .toString().trim());
                        new FbDbHelper().addAdmin(adminProfile, new FbDbHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<AdminProfile> profile, List<String> keys) {

                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });*/
                    }
                });

            }
        });
        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); return;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}