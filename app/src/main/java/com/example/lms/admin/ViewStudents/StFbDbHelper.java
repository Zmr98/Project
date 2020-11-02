package com.example.lms.admin.ViewStudents;

import androidx.annotation.NonNull;

import com.example.lms.user.UserProfile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StFbDbHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<UserProfile> profile = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<UserProfile> profile, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public StFbDbHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("User");
    }

    public void viewStudent(final StFbDbHelper.DataStatus dataStatus){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profile.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode: dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    UserProfile userProfile = keyNode.getValue(UserProfile.class);
                    profile.add(userProfile);
                }
                dataStatus.DataIsLoaded(profile,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addStu(UserProfile profile, final StFbDbHelper.DataStatus dataStatus ){

        String key = mReference.push().getKey();
        mReference.child(key).setValue(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }

    public void updateStu(String key, UserProfile profile, final StFbDbHelper.DataStatus dataStatus ){
        mReference.child(key).setValue(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsUpdated();
            }
        });
    }

    public void deleteStu(String key, final StFbDbHelper.DataStatus dataStatus ){
        mReference.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsDeleted();
            }
        });
    }

}
