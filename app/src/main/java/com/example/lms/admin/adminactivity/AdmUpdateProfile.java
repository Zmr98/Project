package com.example.lms.admin.adminactivity;

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

public class AdmUpdateProfile extends AppCompatActivity {

    //Variables
    private EditText newUserName, newUserDOB, newUserRegNumber, newUserEmail, newUserPassword;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView updateProfilePic;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;

    String adminId;

    //getting image from storage
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
        setContentView(R.layout.activity_adm_update_profile);

        //Assigning all the Variables to ID
        newUserName = (EditText) findViewById(R.id.etAdmNameUpdate);
        newUserDOB = (EditText) findViewById(R.id.etAdmDOBUpdate);
        newUserRegNumber = (EditText) findViewById(R.id.etAdmRegistrationNumUpdate);
        newUserEmail = (EditText) findViewById(R.id.etAdmEmailUpdate);
        save = (Button) findViewById(R.id.btnAdmSave);
        updateProfilePic = (ImageView) findViewById(R.id.ivAdmProfileUpdate);
        newUserPassword = (EditText) findViewById(R.id.etAdmPasswordUpdate);

        //Toolbar Name and make visible
        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting details from firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        adminId = getIntent().getStringExtra("adminIdNumber");

        //getting file location from database
        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("Admin").child(adminId);

        //changing the details which currenty in database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AdminProfile userProfile = dataSnapshot.getValue(AdminProfile.class);
                newUserName.setText(userProfile.getAdminName());
                newUserDOB.setText(userProfile.getAdminDOB());
                newUserEmail.setText(userProfile.getAdminEmail());
                newUserRegNumber.setText(userProfile.getAdminIdNumber());
                newUserPassword.setText(userProfile.getAdminPassword());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdmUpdateProfile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        //assigning picasso to load image
        final StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("Images/Admin Profile Pic").child(adminId).getDownloadUrl() .addOnSuccessListener(new OnSuccessListener<Uri>() {
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
                String password = newUserPassword.getText().toString();

                AdminProfile adminProfile = new AdminProfile(dob, email, name, regnumber, password);

                databaseReference.setValue(adminProfile);

                try{

                    StorageReference imageReference = storageReference.child("Images").child("Admin Profile Pic").child(adminId);
                    UploadTask uploadTask = imageReference.putFile(imagePath);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdmUpdateProfile.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AdmUpdateProfile.this, "Upload Successful!", Toast.LENGTH_SHORT).show();
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
