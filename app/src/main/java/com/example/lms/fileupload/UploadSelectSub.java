package com.example.lms.fileupload;

/*List view which is show courses and subjects*/

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.lms.common.MainAdapter;
import com.example.lms.R;
import com.example.lms.fileupload.it.UplHNDIT1209;
import com.example.lms.fileupload.it.UplHNDIT1210;
import com.example.lms.fileupload.it.UplHNDIT1211;
import com.example.lms.fileupload.it.UplHNDIT1212;
import com.example.lms.fileupload.it.UplHNDIT1213;
import com.example.lms.fileupload.it.UplHNDIT1214;
import com.example.lms.fileupload.it.UplHNDIT1215;
import com.example.lms.fileupload.it.UplHNDIT1216;
import com.example.lms.fileupload.it.UplHNDIT2401;
import com.example.lms.fileupload.it.UplHNDIT2402;
import com.example.lms.fileupload.it.UplHNDIT2403;
import com.example.lms.fileupload.it.UplHNDIT2404;
import com.example.lms.fileupload.it.UplHNDIT2405;
import com.example.lms.fileupload.it.UplHNDIT2411;
import com.example.lms.fileupload.it.UplHNDIT2412;
import com.example.lms.fileupload.it.UplHNDIT2413;
import com.example.lms.fileupload.it.UplHNDIT2414;
import com.example.lms.fileupload.it.UplHNDIT2415;
import com.example.lms.fileupload.it.UplHNDIT2416;
import com.example.lms.fileupload.it.UplHNDIT2417;
import com.example.lms.fileupload.it.UplHNDIT2421;
import com.example.lms.fileupload.it.UplHNDIT2422;
import com.example.lms.fileupload.it.UplHNDIT2423;
import com.example.lms.fileupload.it.UplHNDIT2424;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UploadSelectSub extends AppCompatActivity {

    //Variables
    private FirebaseAuth firebaseAuth;
    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String,List<String>> listItem;
    MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_select_sub);

        //Display toolbar and tool bar name
        getSupportActionBar().setTitle("Upload Files");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //
        firebaseAuth = FirebaseAuth.getInstance();

        expandableListView = findViewById(R.id.adminuploadexpendableListView);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(this,listGroup,listItem);
        expandableListView.setAdapter(adapter);
        initListData();

        //The List view which dispaly the courses and subjects
        expandableListView =findViewById(R.id.adminuploadexpendableListView);
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

                        intent = new Intent (UploadSelectSub.this, UplHNDIT1209.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1210 Graphics and Multimedia":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT1210.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1211 Data Structures and Algorithms":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT1211.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1212 Systems Analysis and Design":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT1212.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1213 Data Communications and Networks":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT1213.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1214 Statistics for IT":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT1214.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1215 English for Technology II":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT1215.class);
                        startActivity(intent);
                        break;

                    case "HNDIT1216 Human Values and Professional Ethics":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT1216.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2401 Computer Architecture":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2401.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2402 Open Source Systems":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2402.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2403 Profesional issues in IT":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2403.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2404 Project(Individual)":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2404.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2405 English for Technology IV":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2405.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2411 Enterprise Architecture":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2411.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2412 Software Configuration Management":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2412.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2413 Web Programming":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2413.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2414 Computer Graphics and Animation":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2414.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2415 Digital Image Processing":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2415.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2416 Digital Video and Audio":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2416.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2417 Mobile Application Development":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2417.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2421 Server Administration":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2421.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2422 Network & Data Centre Operations":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2422.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2423 Disaster Recovery & Business Continuity Planning":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2423.class);
                        startActivity(intent);
                        break;

                    case "HNDIT2424 Database Administration":
                        intent = new Intent (UploadSelectSub.this, UplHNDIT2424.class);
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

    //options for When back button is pressed by user
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    
}
