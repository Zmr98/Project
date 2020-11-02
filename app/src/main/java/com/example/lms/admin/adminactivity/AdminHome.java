package com.example.lms.admin.adminactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.lms.admin.ViewStudents.ViewStudents;
import com.example.lms.admin.adminactivity.it.HNDIT1209adm;
import com.example.lms.admin.adminactivity.it.HNDIT1210adm;
import com.example.lms.admin.adminactivity.it.HNDIT1211adm;
import com.example.lms.admin.adminactivity.it.HNDIT1212adm;
import com.example.lms.admin.adminactivity.it.HNDIT1213adm;
import com.example.lms.admin.adminactivity.it.HNDIT1214adm;
import com.example.lms.admin.adminactivity.it.HNDIT1215adm;
import com.example.lms.admin.adminactivity.it.HNDIT1216adm;
import com.example.lms.admin.adminactivity.it.HNDIT2401adm;
import com.example.lms.admin.adminactivity.it.HNDIT2402adm;
import com.example.lms.admin.adminactivity.it.HNDIT2403adm;
import com.example.lms.admin.adminactivity.it.HNDIT2404adm;
import com.example.lms.admin.adminactivity.it.HNDIT2405adm;
import com.example.lms.admin.adminactivity.it.HNDIT2411adm;
import com.example.lms.admin.adminactivity.it.HNDIT2412adm;
import com.example.lms.admin.adminactivity.it.HNDIT2413adm;
import com.example.lms.admin.adminactivity.it.HNDIT2414adm;
import com.example.lms.admin.adminactivity.it.HNDIT2415adm;
import com.example.lms.admin.adminactivity.it.HNDIT2416adm;
import com.example.lms.admin.adminactivity.it.HNDIT2417adm;
import com.example.lms.admin.adminactivity.it.HNDIT2421adm;
import com.example.lms.admin.adminactivity.it.HNDIT2422adm;
import com.example.lms.admin.adminactivity.it.HNDIT2423adm;
import com.example.lms.admin.adminactivity.it.HNDIT2424adm;
import com.example.lms.adminPanel.AdminPanel;
import com.example.lms.common.Feedback;
import com.example.lms.common.MainAdapter;
import com.example.lms.R;
import com.example.lms.fileupload.UploadSelectSub;
import com.example.lms.atipastpapers.PastPapers;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.lms.R.layout.activity_admin_home;


public class AdminHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    private FirebaseAuth firebaseAuth;
    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String,List<String>> listItem;
    MainAdapter adapter;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle ;

    String adminId;

    SharedPreferences sharedPreferences;
    public static final String fileName = "login";
    public static final String Username1 = "adminName";
    public static final String Password1 = "adminPassword";
    TextView Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_admin_home);

        adminId = getIntent().getStringExtra("adminIdNumber");
        sharedPreferences = getSharedPreferences(fileName, Context.MODE_PRIVATE);

        //menu hooks
        drawerLayout = findViewById(R.id.admdrawer_layout);
        navigationView = findViewById(R.id.admnavigation_view);

        //Navigation Bar Display and Button Options
        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);


        firebaseAuth = FirebaseAuth.getInstance();

        expandableListView = findViewById(R.id.adminexpendableListView);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(this,listGroup,listItem);
        expandableListView.setAdapter(adapter);
        initListData();

        //The List view which dispaly the courses and subjects
        expandableListView =findViewById(R.id.adminexpendableListView);
        final ExpandableListAdapter expandableListAdapter = new MainAdapter(this, listGroup, listItem);
        expandableListView.setAdapter(expandableListAdapter);

        //On click object for child(Subjects)
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                final String selected = (String) expandableListAdapter.getChild(groupPosition, childPosition);

                Intent intent;

                switch (selected){
                    case "HNDIT1209 Object Oriented Programming":

                        intent = new Intent (AdminHome.this, HNDIT1209adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1210 Graphics and Multimedia":
                        intent = new Intent (AdminHome.this, HNDIT1210adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1211 Data Structures and Algorithms":
                        intent = new Intent (AdminHome.this, HNDIT1211adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1212 Systems Analysis and Design":
                        intent = new Intent (AdminHome.this, HNDIT1212adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1213 Data Communications and Networks":
                        intent = new Intent (AdminHome.this, HNDIT1213adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1214 Statistics for IT":
                        intent = new Intent (AdminHome.this, HNDIT1214adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1215 English for Technology II":
                        intent = new Intent (AdminHome.this, HNDIT1215adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1216 Human Values and Professional Ethics":
                        intent = new Intent (AdminHome.this, HNDIT1216adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2401 Computer Architecture":
                        intent = new Intent (AdminHome.this, HNDIT2401adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2402 Open Source Systems":
                        intent = new Intent (AdminHome.this, HNDIT2402adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2403 Profesional issues in IT":
                        intent = new Intent (AdminHome.this, HNDIT2403adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2404 Project(Individual)":
                        intent = new Intent (AdminHome.this, HNDIT2404adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2405 English for Technology IV":
                        intent = new Intent (AdminHome.this, HNDIT2405adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2411 Enterprise Architecture":
                        intent = new Intent (AdminHome.this, HNDIT2411adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2412 Software Configuration Management":
                        intent = new Intent (AdminHome.this, HNDIT2412adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2413 Web Programming":
                        intent = new Intent (AdminHome.this, HNDIT2413adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2414 Computer Graphics and Animation":
                        intent = new Intent (AdminHome.this, HNDIT2414adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2415 Digital Image Processing":
                        intent = new Intent (AdminHome.this, HNDIT2415adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2416 Digital Video and Audio":
                        intent = new Intent (AdminHome.this, HNDIT2416adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2417 Mobile Application Development":
                        intent = new Intent (AdminHome.this, HNDIT2417adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2421 Server Administration":
                        intent = new Intent (AdminHome.this, HNDIT2421adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2422 Network & Data Centre Operations":
                        intent = new Intent (AdminHome.this, HNDIT2422adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2423 Disaster Recovery & Business Continuity Planning":
                        intent = new Intent (AdminHome.this, HNDIT2423adm.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2424 Database Administration":
                        intent = new Intent (AdminHome.this, HNDIT2424adm.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

    }

    //Displaying List view getting using Adapter
    private void initListData() {
        listGroup.add(getString(R.string.group1));
        listGroup.add(getString(R.string.group2));
        listGroup.add(getString(R.string.group3));
        listGroup.add(getString(R.string.group4));
        listGroup.add(getString(R.string.group5));
        listGroup.add(getString(R.string.group6));
        listGroup.add(getString(R.string.group7));

        String[] array;

        List<String> list1 = new ArrayList<>();
        array = getResources() .getStringArray(R.array.group1);
        for (String item : array){
            list1.add(item);
        }

        List<String> list2 = new ArrayList<>();
        array = getResources() .getStringArray(R.array.group2);
        for (String item : array){
            list2.add(item);
        }

        List<String> list3 = new ArrayList<>();
        array = getResources() .getStringArray(R.array.group3);
        for (String item : array){
            list3.add(item);
        }

        List<String> list4 = new ArrayList<>();
        array = getResources() .getStringArray(R.array.group4);
        for (String item : array){
            list4.add(item);
        }

        List<String> list5 = new ArrayList<>();
        array = getResources() .getStringArray(R.array.group5);
        for (String item : array){
            list5.add(item);
        }

        List<String> list6 = new ArrayList<>();
        array = getResources() .getStringArray(R.array.group6);
        for (String item : array){
            list6.add(item);
        }

        List<String> list7 = new ArrayList<>();
        array = getResources() .getStringArray(R.array.group7);
        for (String item : array){
            list7.add(item);
        }

        listItem.put(listGroup.get(0),list1);
        listItem.put(listGroup.get(1),list2);
        listItem.put(listGroup.get(2),list3);
        listItem.put(listGroup.get(3),list4);
        listItem.put(listGroup.get(4),list5);
        listItem.put(listGroup.get(5),list6);
        listItem.put(listGroup.get(6),list7);
        adapter.notifyDataSetChanged();

    }

    //Logout ption which in Drawer Menu
    /*private void Logout(){

    }*/

    //getting drawer menu items to the page/activity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //options for When back button is pressed by user
    @Override
    public void onBackPressed() {
        //open and back drawer layout
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to Exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /*finish();
                            AdminHome.super.onBackPressed();*/
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
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
        }
    }

    //Giving options to navigation list
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.admprofileMenu: {
                //startActivity(new Intent(AdminHome.this, AdmProfile.class));
                Intent intent = new Intent(getApplicationContext(), AdmProfile.class);
                intent.putExtra("adminIdNumber", adminId);
                startActivity(intent);
                break;
            }

            /*case R.id.admhome:{
                startActivity(new Intent(AdminHome.this, HNDIT1216.class));
                break;
            }*/

            case R.id.admupload:{
                startActivity(new Intent(AdminHome.this, UploadSelectSub.class));
                break;
            }

            case R.id.admpasspaper:{
                startActivity(new Intent(AdminHome.this, PastPapers.class));
                break;
            }

            case R.id.admAddAdmin:{
                startActivity(new Intent(AdminHome.this, AdminPanel.class));
                break;
            }

            case R.id.admStAdmin:{
                startActivity(new Intent(AdminHome.this, ViewStudents.class));
                break;
            }

            case R.id.admfeedback:{
                startActivity(new Intent(AdminHome.this, Feedback.class));
                break;
            }

            case R.id.admlogout:{
                /*firebaseAuth.signOut();
                finishAffinity();
                startActivity(new Intent(AdminHome.this, AdminLogin.class));*/
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(Username1);
                editor.remove(Password1);
                editor.commit();
                Intent intent = new Intent(AdminHome.this, AdminLogin.class);
                startActivity(intent);
                finishAffinity();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
