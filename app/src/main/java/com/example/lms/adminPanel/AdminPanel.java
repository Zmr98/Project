package com.example.lms.adminPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lms.R;
import com.example.lms.admin.adminactivity.AdminProfile;

import java.util.List;

public class AdminPanel extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        getSupportActionBar().setTitle("Admin Panel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvAdmin);
        new FbDbHelper().viewAdmins(new FbDbHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<AdminProfile> profile, List<String> keys) {
                new PanelRecyclerView().setConfig(mRecyclerView, AdminPanel.this, profile,keys);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addadmin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_admin: startActivity(new Intent(this, NewAdmin.class));
            return true;

            case android.R.id.home:
                onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }

}
