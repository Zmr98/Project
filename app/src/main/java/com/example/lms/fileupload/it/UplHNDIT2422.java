package com.example.lms.fileupload.it;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lms.R;
import com.example.lms.fileupload.UploadFiles;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UplHNDIT2422 extends AppCompatActivity {

    //Variables
    EditText editFileName;
    Button btnUpload;

    //Import Firebase Database and Storage
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upl_h_n_d_i_t2422);

        getSupportActionBar().setTitle("Network & Data Centre Operations");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Assigning Variables id
        editFileName = (EditText) findViewById(R.id.editFileName);
        btnUpload = (Button) findViewById(R.id.btnUpload);

        //Getting reference from storage and database
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("HNDIT/2nd Year 2nd Sem/NDCO");

        //Giving an activity or Click option to button
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile();
            }
        });

    }

    //This class is used to identify file type and get it
    private void selectFile() {

        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Your File"),1);

    }

    //Checking the file is selecting or not
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            uploadTheFile(data.getData());
        }
    }

    //uploading the file to firebase database and storage
    private void uploadTheFile(Uri data) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference reference = storageReference.child("HNDIT/2nd Year 2nd Sem/NDCO/"+editFileName.getText().toString()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uri =  taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete());
                Uri url= uri.getResult();

                UploadFiles uploadFiles = new UploadFiles(editFileName.getText().toString(),url.toString());
                databaseReference.child(databaseReference.push().getKey()).setValue(uploadFiles);
                Toast.makeText(UplHNDIT2422.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                progressDialog.setMessage("Uploaded: "+(int)progress+"%");

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
