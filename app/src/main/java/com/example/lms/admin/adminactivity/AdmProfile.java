package com.example.lms.admin.adminactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lms.R;
import com.example.lms.common.UpdatePassword;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class AdmProfile extends AppCompatActivity {

    //Variables
    private ImageView profilePic;
    private TextView profileName, profileDOB, profileRegistrationNum, profileEmail;
    private Button profileEdit, changePassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    String adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_profile);

        //Toolbar Name and make visible
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Assigning all the Variables to ID
        profilePic = (ImageView) findViewById(R.id.ivAdmProfilePic);
        profileName = (TextView) findViewById(R.id.tvAdmProfileName);
        profileDOB = (TextView) findViewById(R.id.tvAdmProfileDOB);
        profileRegistrationNum = (TextView) findViewById(R.id.tvAdmProfileRegistrationNum);
        profileEmail = (TextView) findViewById(R.id.tvAdmProfileEmail);
        profileEdit = (Button) findViewById(R.id.btnAdmProfileEdit);
        //changePassword = (Button) findViewById(R.id.btnAdmChangePassword);


        //Connecting to Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        adminId = getIntent().getStringExtra("adminIdNumber");

        //Getting details from database
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Admin").child(adminId);

        //Getting profile image from storage
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Images/Admin Profile Pic").child(adminId).getDownloadUrl() .addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profilePic);
            }
        });

        //Getting details from database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AdminProfile adminProfile = dataSnapshot.getValue(AdminProfile.class);
                profileName.setText("Name: " + adminProfile.getAdminName());
                profileDOB.setText("Date of Birth: " + adminProfile.getAdminDOB());
                profileEmail.setText("Email: " + adminProfile.getAdminEmail());
                profileRegistrationNum.setText("Registration Number: " + adminProfile.getAdminIdNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdmProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        //On Click option and move to update page
        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AdmProfile.this, AdmUpdateProfile.class));
                Intent intent = new Intent(getApplicationContext(), AdmUpdateProfile.class);
                intent.putExtra("adminIdNumber", adminId);
                startActivity(intent);
            }
        });

        //On Click option and move to change password page
        /*changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdmProfile.this, UpdatePassword.class));
            }
        });*/
    }

    //Back button option
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
