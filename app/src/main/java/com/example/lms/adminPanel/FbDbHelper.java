package com.example.lms.adminPanel;

import androidx.annotation.NonNull;

import com.example.lms.admin.adminactivity.AdminProfile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FbDbHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<AdminProfile> profile = new ArrayList<>();


    public interface DataStatus{
        void DataIsLoaded(List<AdminProfile> profile, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FbDbHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Admin");
    }

    public void viewAdmins(final DataStatus dataStatus){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profile.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode: dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    AdminProfile adminProfile = keyNode.getValue(AdminProfile.class);
                    profile.add(adminProfile);
                }
                dataStatus.DataIsLoaded(profile,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addAdmin(AdminProfile profile, final DataStatus dataStatus ){

        String key = mReference.push().getKey();
        mReference.child(key).setValue(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }

    public void updateAdmin(String key, AdminProfile profile, final DataStatus dataStatus ){
        mReference.child(key).setValue(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteAdmin(String key, final DataStatus dataStatus ){
        mReference.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();
            }
        });
    }

}
