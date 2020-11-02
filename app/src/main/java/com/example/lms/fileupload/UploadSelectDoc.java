package com.example.lms.fileupload;

/*Example file(Test File)*/

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lms.R;
import com.example.lms.fileupload.UploadListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadSelectDoc extends AppCompatActivity {

    Uri fileUri;
    String fileName;

    private Button mUploadBtn;
    private RecyclerView mUploadList;
    private static int PICK_FILES = 1;


    private List<String> fileNameList;
    private List<String> fileDoneList;
    private UploadListAdapter uploadListAdapter;

    ArrayList<Uri> FileList = new ArrayList<Uri>();

    private StorageReference mStorage;
    FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_select_doc);

        getSupportActionBar().setTitle("Upload Files");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mStorage= FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        mUploadBtn = (Button) findViewById(R.id.btnUploadFiles);
        //mSelectBtn = (Button) findViewById(R.id.btnSelectFiles);
        mUploadList = (RecyclerView) findViewById(R.id.rvUploadDoc);

        fileNameList = new ArrayList<>();
        fileDoneList = new ArrayList<>();

        uploadListAdapter = new UploadListAdapter(fileNameList, fileDoneList);

        //RecyclerView
        mUploadList.setLayoutManager(new LinearLayoutManager(this));
        mUploadList.setHasFixedSize(true);
        mUploadList.setAdapter(uploadListAdapter);

        //if the upload button clicks select the files from device
        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Your Files"), PICK_FILES);
            }
        });

    }


    //uploading files to firebase storage and databse
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Checking the file count and start to upload to storage and fire base
        if(requestCode == PICK_FILES && resultCode == RESULT_OK){
            if (data.getClipData() != null){

                int totalItemsSelected = data.getClipData().getItemCount();

                for (int i = 0 ;  i < totalItemsSelected; i++){

                    fileUri= data.getClipData().getItemAt(i).getUri();

                    fileName = getFileName(fileUri);

                    fileNameList.add(fileName);
                    fileDoneList.add("uploading");
                    uploadListAdapter.notifyDataSetChanged();

                    final StorageReference fileToUpload = mStorage.child("HNDIT/1st Year 2nd Sem/OOP").child(fileName);
                    final int finalI = i;
                    fileToUpload.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            StorageMetadata snapshotMetadata = taskSnapshot.getMetadata();
                            Task<Uri> downloadUrl = fileToUpload.getDownloadUrl();

                            /*downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String fileReference = uri.toString();
                                    databaseReference.child("url").setValue(fileReference);
                                }
                            });*/
                            downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String fileReference = uri.toString();

                                    Map<String,String> map = new HashMap<>();
                                    map.put("url" , fileReference);
                                    map.put("title" , fileName);

                                    databaseReference = FirebaseDatabase.getInstance().getReference("HNDIT/1st Year 2nd Sem/OOP");
                                    databaseReference.push().setValue(map);

                                }
                            });

                            fileDoneList.remove(finalI);
                            fileDoneList.add(finalI, "done");

                            uploadListAdapter.notifyDataSetChanged();
                        }
                    });

                }

            }else if(data.getData() != null){

                if(requestCode == PICK_FILES && resultCode == RESULT_OK){

                    fileUri = data.getData();

                    fileName = getFileName(fileUri);

                    fileNameList.add(fileName);
                    fileDoneList.add("uploading");
                    uploadListAdapter.notifyDataSetChanged();

                    databaseReference = FirebaseDatabase.getInstance().getReference("HNDIT/1st Year 2nd Sem/OOP").push();
                    databaseReference.child("title").setValue(fileName);

                    final StorageReference fileToUpload = mStorage.child("HNDIT/1st Year 2nd Sem/OOP").child(fileName);
                    fileToUpload.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            StorageMetadata snapshotMetadata = taskSnapshot.getMetadata();
                            Task<Uri> downloadUrl = fileToUpload.getDownloadUrl();
                            downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String fileReference = uri.toString();
                                    databaseReference.child("url").setValue(fileReference);

                                }
                            });

                            int i = 0;
                            fileDoneList.remove(i);
                            fileDoneList.add(i, "done");
                            uploadListAdapter.notifyDataSetChanged();

                        }
                    });
                }
            }
        }
    }

    //getting file name to view in uploading files list view
    public String getFileName (Uri uri){
        String result = null;
        if (uri.getScheme().equals("content")){
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            try {
                if (cursor != null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if (result == null){
            result =uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1){
                result = result.substring(cut + 1);
            }
        }
        return result;

    }

    //Back Button For Go Back
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



}