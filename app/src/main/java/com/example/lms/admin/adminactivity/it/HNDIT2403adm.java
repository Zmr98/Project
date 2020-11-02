package com.example.lms.admin.adminactivity.it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lms.R;
import com.example.lms.fileupload.UploadFiles;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HNDIT2403adm extends AppCompatActivity {

    //Variables
    ListView myViewFiles;
    DatabaseReference databaseReference;
    List<String> file_title,file_url;
    ArrayAdapter<String> adapter;
    UploadFiles uploadFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_n_d_i_t2);

        //Displaying the toolbar
        getSupportActionBar().setTitle("Professional issues in IT");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Assigning all the Variables to ID
        myViewFiles = (ListView) findViewById(R.id.myViewFiles);
        databaseReference = FirebaseDatabase.getInstance().getReference("HNDIT").child("2nd Year 2nd Sem").child("Professional Issues in IT");
        uploadFiles = new UploadFiles();
        file_title = new ArrayList<>();
        file_url = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,R.layout.view_option,R.id.text_view1,file_title);
        final ArrayList<String> keyList = new ArrayList<>();

        //Getting Data to List View
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                file_title.clear();
                file_url.clear();

                //Set Files to List View
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    keyList.add(ds.getKey());
                    uploadFiles = ds.getValue(UploadFiles.class);
                    if (uploadFiles != null) {
                        file_title.add(uploadFiles.getTitle());
                    }
                    if (uploadFiles != null) {
                        file_url.add(uploadFiles.getUrl());
                    }
                }
                myViewFiles.setAdapter(adapter);


                //Opening PDF File in pdf viewer(Adobe)
                myViewFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(uploadFiles.getUrl()),"application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                });


                //Removing File from List View
                myViewFiles.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                        //Display Pop up Text to Delete
                        AlertDialog.Builder builder = new AlertDialog.Builder(HNDIT2403adm.this);
                        builder.setMessage("Are you sure you want to Delete?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        //To remove the file from Firebase Database
                                        String Key = keyList.get(position);
                                        databaseReference.child(Key).removeValue();
                                        adapter.notifyDataSetChanged();
                                    }
                                })

                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();


                        return false;
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}