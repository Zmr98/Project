package com.example.lms.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lms.R;
import com.example.lms.common.Base;
import com.example.lms.common.StartupScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Registration extends AppCompatActivity {

    //variables
    private EditText userName, userPassword, userEmail, userDOB, userRegistrationNumber;
    private Button regButton;
    private TextView userLogin;
    public FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String email, name, DOB, password, regnumber;
    private Object CharSequence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        //progress bar
        progressDialog = new ProgressDialog(this);

        //On click method for registration button
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (validate()){
                   //upload data to the database
                   String user_email = userEmail.getText().toString().trim();
                   String user_password = userPassword.getText().toString().trim();

                   Log.e("LOGIN DETAILS",userEmail.getText().toString() + ">>>" + userPassword.getText().toString()  );

                   //creating the account in firebase auth and adding details to database
                   firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {

                           if(task.isSuccessful()){
                               sendEmailVerification();
//                               sendUserData();
//                               firebaseAuth.signOut();
                               Toast.makeText(Registration.this, "Successfully Registered, Upload complete!", Toast.LENGTH_SHORT).show();
//                               finish();
//                               startActivity(new Intent(Registration.this, MainActivity.class));
                               /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                               startActivity(intent);
                               finishAffinity();*/
                           }else{
                               Toast.makeText(Registration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                           }

                       }
                   });
               }
            }
        });

        //On click method for Login Activity Button
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this, MainActivity.class));
            }
        });

    }

    //Assigning all the Variables to ID
    private void setupUIViews(){
        userName = (EditText) findViewById(R.id.etUserName);
        userPassword = (EditText) findViewById(R.id.etUserPassword);
        userEmail = (EditText) findViewById(R.id.etUserEmail);
        regButton = (Button) findViewById(R.id.btnRegister);
        userLogin = (TextView) findViewById(R.id.tvUserLogin);
        userDOB = (EditText) findViewById(R.id.etUserDOB);
        userRegistrationNumber = (EditText) findViewById(R.id.etRegNumber);
    }

    //Validating the details is there empty fields or not
    private Boolean validate(){
        Boolean result = false;

        name = userName.getText().toString().trim();
        password = userPassword.getText().toString().trim();
        email = userEmail.getText().toString().trim();
        isValidEmail((java.lang.CharSequence) CharSequence);
        DOB = userDOB.getText().toString().trim();
        regnumber = userRegistrationNumber.getText().toString().trim();

        //checking empty fields
        if(name.isEmpty() || password.isEmpty() || email.isEmpty() || DOB.isEmpty() || regnumber.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
            return result;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    //Sending Email to verify to check and update the user info(if user didn't approve details won't update in database)
    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(Registration.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }else{
                        Toast.makeText(Registration.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    //Sending User Database to Firebase Database
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("User").child(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(DOB, email, name, regnumber,password);
        myRef.setValue(userProfile);
    }

    //Back button option
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(Registration.this, StartupScreen.class));
                onBackPressed();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
