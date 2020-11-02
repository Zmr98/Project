package com.example.lms.testfiles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lms.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Upfiles extends AppCompatActivity {

    private static final int READ_PERMISSION_CODE = 1;
    private static final int PICK_IMAGE_REQUEST_CODE = 2;
    ImageView no_images;
    FloatingActionButton btnPickImages, btnUploadImages;
    RecyclerView recyclerView;
    List<CustomModel> imagesList;
    List<String> savedImagesUri;
    FilesAdapter adapter;
    CoreHelper coreHelper;
    FirebaseStorage storage;
    FirebaseDatabase database;
    DatabaseReference reference;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upfiles);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("HNDIT/1st Year 2nd Sem/OOP");
        //reference.child("title").setValue(Name);

        savedImagesUri = new ArrayList<>();

        no_images = findViewById(R.id.no_image);
        btnPickImages = findViewById(R.id.fabChooseImage);
        btnUploadImages = findViewById(R.id.fabUploadImage);
        imagesList = new ArrayList<>();
        coreHelper = new CoreHelper(this);
        //Code to show list of images start
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new FilesAdapter(this, imagesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (adapter.getItemCount() != 0) {
                    no_images.setVisibility(View.GONE);
                } else {
                    no_images.setVisibility(View.VISIBLE);
                }
            }
        });
        //Code to show list of images end
        btnPickImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyPermissionAndPickImage();
            }
        });
        btnUploadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImages(view);
            }
        });
    }


    private void uploadImages(View view) {

        if (imagesList.size() != 0) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("HNDIT/1st Year 2nd Sem/OOP"+imagesList.size());
            progressDialog.setCanceledOnTouchOutside(false); //Remove this line if you want your user to be able to cancel upload
            progressDialog.setCancelable(false);    //Remove this line if you want your user to be able to cancel upload
            progressDialog.show();
            final StorageReference storageReference = storage.getReference();
            for (int i = 0; i < imagesList.size(); i++) {
                final int finalI = i;
                storageReference.child("HNDIT/1st Year 2nd Sem/OOP").child(imagesList.get(i).getImageName()).putFile(imagesList.get(i).getImageURI()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()){
                            storageReference.child("HNDIT/1st Year 2nd Sem/OOP").child(imagesList.get(finalI).getImageName()).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    counter++;
                                    progressDialog.setMessage("Uploaded "+counter+"/"+imagesList.size());
                                    if (task.isSuccessful()){
                                        savedImagesUri.add(task.getResult().toString());
                                    }else{
                                        storageReference.child("HNDIT/1st Year 2nd Sem/OOP").child(imagesList.get(finalI).getImageName()).delete();
                                        Toast.makeText(Upfiles.this, "Couldn't save "+imagesList.get(finalI).getImageName(), Toast.LENGTH_SHORT).show();
                                    }
                                    if (counter == imagesList.size()){
                                        saveFileDataToDatabase(progressDialog);
                                    }
                                }
                            });
                        }else{
                            progressDialog.setMessage("Uploaded "+counter+"/"+imagesList.size());
                            counter++;
                            Toast.makeText(Upfiles.this, "Couldn't upload "+imagesList.get(finalI).getImageName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } else {
            coreHelper.createSnackBar(view, "Please add some images first.", "", null, Snackbar.LENGTH_SHORT);
        }

    }

    private void saveFileDataToDatabase(final ProgressDialog progressDialog) {
        progressDialog.setMessage("Saving uploaded images...");
        Map<String, String> dataMap = new HashMap<>();
        for (int i=0; i<savedImagesUri.size(); i++){
            dataMap.put("title", savedImagesUri.get(i));

        }

        reference.setValue(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                coreHelper.createAlert("Success", "Images uploaded and saved successfully!", "OK", "", null, null, null);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                coreHelper.createAlert("Error", "Images uploaded but we couldn't save them to database.", "OK", "", null, null, null);
                Log.e("Upfiles:SaveData", e.getMessage());
            }
        });
    }

    private void verifyPermissionAndPickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //If permission is granted
                pickImage();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION_CODE);
            }
        } else {
            //no need to check permissions in android versions lower then marshmallow
            pickImage();
        }
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImage();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    ClipData clipData = data.getClipData();
                    if (clipData != null) {
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri uri = clipData.getItemAt(i).getUri();
                            imagesList.add(new CustomModel(coreHelper.getFileNameFromUri(uri), uri));
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Uri uri = data.getData();
                        imagesList.add(new CustomModel(coreHelper.getFileNameFromUri(uri), uri));
                        adapter.notifyDataSetChanged();
                    }
                }
        }
    }
}