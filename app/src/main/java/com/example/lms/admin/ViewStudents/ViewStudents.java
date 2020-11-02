package com.example.lms.admin.ViewStudents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.lms.R;
import com.example.lms.user.UserProfile;

import java.util.List;

public class ViewStudents extends AppCompatActivity {

    private RecyclerView mStRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        getSupportActionBar().setTitle("Students Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mStRecyclerView = (RecyclerView) findViewById(R.id.rvStudents);

        new StFbDbHelper().viewStudent(new StFbDbHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<UserProfile> profile, List<String> keys) {
                new stPanelRecyclerView().setConfig(mStRecyclerView,ViewStudents.this,profile,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        /*new StFbDbHelper().viewStudents(new StFbDbHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<UserProfile> profile, List<String> keys) {
                new stPanelRecyclerView().setConfig(mStRecyclerView, ViewStudents.this, profile,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });*/
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