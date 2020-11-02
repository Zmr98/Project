package com.example.lms.it;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lms.R;
import com.example.lms.fileupload.UploadFiles;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HNDIT1211 extends AppCompatActivity {

    ListView myViewFiles;
    DatabaseReference databaseReference;
    List<String>file_title,file_url;
    ArrayAdapter<String> adapter;
    UploadFiles uploadFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_n_d_i_t1211);

        //Displaying the toolbar
        getSupportActionBar().setTitle("Data Structures and Algorithms");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Assigning all the Variables to ID
        myViewFiles = (ListView) findViewById(R.id.myViewFiles);
        databaseReference = FirebaseDatabase.getInstance().getReference("HNDIT").child("1st Year 2nd Sem").child("DSA");
        uploadFiles = new UploadFiles();
        file_title = new ArrayList<>();
        file_url = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,R.layout.view_option,R.id.text_view1,file_title);

        //Getting Data to List View
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                file_title.clear();
                file_url.clear();

                //Set Files to List View
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    uploadFiles = ds.getValue(UploadFiles.class);
                    if (uploadFiles != null) {
                        file_title.add(uploadFiles.getTitle());
                    }
                    if (uploadFiles != null) {
                        file_url.add(uploadFiles.getUrl());
                    }
                }
                myViewFiles.setAdapter(adapter);

                //Opening PDF File in pdf viewer
                myViewFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(uploadFiles.getUrl()),"application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
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