package com.example.lms.common;

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
import com.example.lms.user.UserProfile;
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

public class ProfileActivity extends AppCompatActivity {

    //Variables
    private ImageView profilePic;
    private TextView profileName, profileDOB, profileRegistrationNum, profileEmail;
    private Button profileUpdate, changePassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Toolbar Name and make visible
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Assigning all the Variables to ID
        profilePic = (ImageView) findViewById(R.id.ivProfilePic);
        profileName = (TextView) findViewById(R.id.tvProfileName);
        profileDOB = (TextView) findViewById(R.id.tvProfileDOB);
        profileRegistrationNum = (TextView) findViewById(R.id.tvProfileRegistrationNum);
        profileEmail = (TextView) findViewById(R.id.tvProfileEmail);
        profileUpdate = (Button) findViewById(R.id.btnProfileUpdate);
        changePassword = (Button) findViewById(R.id.btnChangePassword);

        //Connecting to Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //Getting details from database
        DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(firebaseAuth.getUid());

        //Getting profile image from firebase storage
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Images/Profile Pic").child(firebaseAuth.getUid()).getDownloadUrl() .addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profilePic);
            }
        });

        //Getting details from database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                profileName.setText("Name: " + userProfile.getUserName());
                profileDOB.setText("Date of Birth: " + userProfile.getUserDOB());
                profileEmail.setText("Email: " + userProfile.getUserEmail());
                profileRegistrationNum.setText("Registration Number: " + userProfile.getUserRegistrationNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        //On Click option and move to update page
         profileUpdate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(ProfileActivity.this, UpdateProfile.class));
             }
         });

        //On Click option and move to change password page
         changePassword.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(ProfileActivity.this, UpdatePassword.class));
             }
         });
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
