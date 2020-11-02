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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AdminDetails extends AppCompatActivity {

    private EditText adminUpName, adminUpPassword, adminUpEmail, adminUpDOB, adminUpRegistrationNumber;
    private Button adminUpUpdate, UpDeletebtn, UpBackbtn;
    private String key, Name, Email, Id, Dob;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_details);

        getSupportActionBar().setTitle("Admin Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();

        key = getIntent().getStringExtra("key");
        Name = getIntent().getStringExtra("adminName");
        Email = getIntent().getStringExtra("adminEmail");
        Id = getIntent().getStringExtra("adminIdNumber");
        Dob = getIntent().getStringExtra("adminDOB");

        adminUpName = (EditText) findViewById(R.id.etUpAdminName);
        adminUpName.setText(Name);
        //adminUpPassword = (EditText) findViewById(R.id.etUpAdminPassword);
        adminUpEmail = (EditText) findViewById(R.id.etUpAdminEmail);
        adminUpEmail.setText(Email);
        adminUpRegistrationNumber = (EditText) findViewById(R.id.etUpAdminRegNumber) ;
        adminUpRegistrationNumber.setText(Id);
        adminUpDOB = (EditText) findViewById(R.id.etUpAdminDOB);
        adminUpDOB.setText(Dob);
        adminUpUpdate = (Button) findViewById(R.id.btnAdminUpdate);
        UpDeletebtn = (Button) findViewById(R.id.btnAdminDelete);
        UpBackbtn = (Button) findViewById(R.id.btnUPBack);

        final DatabaseReference databaseReference = firebaseDatabase.getReference();



        adminUpUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = adminUpName.getText() .toString();
                String dob = adminUpDOB.getText() .toString();
                String regnumber = adminUpRegistrationNumber.getText() .toString();
                String email = adminUpEmail.getText() .toString();


                String password = null;
                AdminProfile adminProfile = new AdminProfile(dob, email, name, regnumber, password);
                databaseReference.child("Admin").child(regnumber).setValue(adminProfile);
                Toast.makeText(AdminDetails.this, "Profile Updated Successfully...", Toast.LENGTH_SHORT).show();
                finish();
                /*AdminProfile adminProfile = new AdminProfile();
                adminProfile.setAdminName(adminUpName.getText().toString());
                adminProfile.setAdminPassword(adminUpPassword.getText().toString());
                adminProfile.setAdminEmail(adminUpEmail.getText().toString());
                adminProfile.setAdminIdNumber(adminUpRegistrationNumber.getText().toString());
                adminProfile.setAdminDOB(adminUpDOB.getText().toString());

                new FbDbHelper().updateAdmin(key, adminProfile, new FbDbHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<AdminProfile> profile, List<String> keys) {
                        
                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(AdminDetails.this, "Admin details has been updated successfully!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });*/
            }
        });

        UpDeletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regnumber = adminUpRegistrationNumber.getText() .toString();
                databaseReference.child("Admin").child(regnumber).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminDetails.this, "Admin details has been deleted successfully!", Toast.LENGTH_SHORT).show();
                        finish(); return;
                    }
                });
                /*new FbDbHelper().deleteAdmin(key, new FbDbHelper.DataStatus() {
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
                        Toast.makeText(AdminDetails.this, "Admin details has been deleted successfully!", Toast.LENGTH_SHORT).show();
                        finish(); return;
                    }
                });*/
            }
        });

        UpBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); return;
            }
        });
    }

    //Back Button option (gose to previous page)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}