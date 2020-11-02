package com.example.lms.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lms.R;
import com.example.lms.user.UserProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class UpdateProfile extends AppCompatActivity {

    //Variables
    private EditText newUserName, newUserDOB, newUserRegNumber, newUserEmail;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView updateProfilePic;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;

    //getting image from storage and converting
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                updateProfilePic.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        //Assigning all the Variables to ID
        newUserName = (EditText) findViewById(R.id.etNameUpdate);
        newUserDOB = (EditText) findViewById(R.id.etDOBUpdate);
        newUserRegNumber = (EditText) findViewById(R.id.etRegistrationNumUpdate);
        newUserEmail = (EditText) findViewById(R.id.etEmailUpdate);
        save = (Button) findViewById(R.id.btnSave);
        updateProfilePic = (ImageView) findViewById(R.id.ivProfileUpdate);

        //Toolbar Name and make visible
        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting details from firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //getting file location from database
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("User").child(firebaseAuth.getUid());

        //changing the details which in current database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                newUserName.setText(userProfile.getUserName());
                newUserDOB.setText(userProfile.getUserDOB());
                newUserEmail.setText(userProfile.getUserEmail());
                newUserRegNumber.setText(userProfile.getUserRegistrationNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        //assigning picasso to load image
        final StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Images/Profile Pic").child(firebaseAuth.getUid()).getDownloadUrl() .addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(updateProfilePic);
            }
        });

        //Saving Image to  Database Storage and Saving Details in Firebase Daatbase
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = newUserName.getText() .toString();
                String dob = newUserDOB.getText() .toString();
                String regnumber = newUserRegNumber.getText() .toString();
                String email = newUserEmail.getText() .toString();

                String password = null;
                UserProfile userProfile = new UserProfile(dob, email, name, regnumber, password);

                databaseReference.setValue(userProfile);

                try {
                    //Uploading image to firebase storage
                    StorageReference imageReference = storageReference.child("Images").child("Profile Pic").child(firebaseAuth.getUid());
                    UploadTask uploadTask = imageReference.putFile(imagePath);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateProfile.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UpdateProfile.this, "Upload Successful!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }

                finish();
            }
        });

        //when the image icon click it wil gose to storage
        updateProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
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
