package com.example.lms.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.lms.adminPanel.AdminPanel;
import com.example.lms.common.MainAdapter;
import com.example.lms.R;
import com.example.lms.atipastpapers.PastPapers;
import com.example.lms.common.Feedback;
import com.example.lms.common.ProfileActivity;
import com.example.lms.common.ViewFiles;
import com.example.lms.it.HNDIT1209;
import com.example.lms.it.HNDIT1210;
import com.example.lms.it.HNDIT1211;
import com.example.lms.it.HNDIT1212;
import com.example.lms.it.HNDIT1213;
import com.example.lms.it.HNDIT1214;
import com.example.lms.it.HNDIT1215;
import com.example.lms.it.HNDIT1216;
import com.example.lms.it.HNDIT2401;
import com.example.lms.it.HNDIT2402;
import com.example.lms.it.HNDIT2403;
import com.example.lms.it.HNDIT2404;
import com.example.lms.it.HNDIT2405;
import com.example.lms.it.HNDIT2411;
import com.example.lms.it.HNDIT2412;
import com.example.lms.it.HNDIT2413;
import com.example.lms.it.HNDIT2414;
import com.example.lms.it.HNDIT2415;
import com.example.lms.it.HNDIT2416;
import com.example.lms.it.HNDIT2417;
import com.example.lms.it.HNDIT2421;
import com.example.lms.it.HNDIT2422;
import com.example.lms.it.HNDIT2423;
import com.example.lms.it.HNDIT2424;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //menu hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        //Navigation Bar Display and Button Options
        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);


        firebaseAuth = FirebaseAuth.getInstance();

        //Setting List view to adapter
        expandableListView = findViewById(R.id.expendableListView);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(this,listGroup,listItem);
        expandableListView.setAdapter(adapter);
        initListData();

        //The List view which dispaly the courses and subjects
        expandableListView =findViewById(R.id.expendableListView);
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

                        intent = new Intent (Home.this, HNDIT1209.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1210 Graphics and Multimedia":
                        intent = new Intent (Home.this, HNDIT1210.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1211 Data Structures and Algorithms":
                        intent = new Intent (Home.this, HNDIT1211.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1212 Systems Analysis and Design":
                        intent = new Intent (Home.this, HNDIT1212.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1213 Data Communications and Networks":
                        intent = new Intent (Home.this, HNDIT1213.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1214 Statistics for IT":
                        intent = new Intent (Home.this, HNDIT1214.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1215 English for Technology II":
                        intent = new Intent (Home.this, HNDIT1215.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1216 Human Values and Professional Ethics":
                        intent = new Intent (Home.this, HNDIT1216.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2401 Computer Architecture":
                        intent = new Intent (Home.this, HNDIT2401.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2402 Open Source Systems":
                        intent = new Intent (Home.this, HNDIT2402.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2403 Profesional issues in IT":
                        intent = new Intent (Home.this, HNDIT2403.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2404 Project(Individual)":
                        intent = new Intent (Home.this, HNDIT2404.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2405 English for Technology IV":
                        intent = new Intent (Home.this, HNDIT2405.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2411 Enterprise Architecture":
                        intent = new Intent (Home.this, HNDIT2411.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2412 Software Configuration Management":
                        intent = new Intent (Home.this, HNDIT2412.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2413 Web Programming":
                        intent = new Intent (Home.this, HNDIT2413.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2414 Computer Graphics and Animation":
                        intent = new Intent (Home.this, HNDIT2414.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2415 Digital Image Processing":
                        intent = new Intent (Home.this, HNDIT2415.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2416 Digital Video and Audio":
                        intent = new Intent (Home.this, HNDIT2416.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2417 Mobile Application Development":
                        intent = new Intent (Home.this, HNDIT2417.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2421 Server Administration":
                        intent = new Intent (Home.this, HNDIT2421.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2422 Network & Data Centre Operations":
                        intent = new Intent (Home.this, HNDIT2422.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2423 Disaster Recovery & Business Continuity Planning":
                        intent = new Intent (Home.this, HNDIT2423.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2424 Database Administration":
                        intent = new Intent (Home.this, HNDIT2424.class);
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
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(Home.this, MainActivity.class));
    }

    //getting drawer menu items to the page/activity
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawermenu, menu);
        return true;
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
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to Exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /*finish();
                            Home.super.onBackPressed();*/
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
            case R.id.profileMenu: {
                startActivity(new Intent(Home.this, ProfileActivity.class));
                break;
            }

            /*case R.id.home: {
                startActivity(new Intent(Home.this, Home.class));
                break;
            }*/

            case R.id.passpaper:{
                startActivity(new Intent(Home.this, PastPapers.class));
                break;
            }

            case R.id.feedback:{
                startActivity(new Intent(Home.this, Feedback.class));
                break;
            }

            case R.id.logout:{
                firebaseAuth.signOut();
                Logout();
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
