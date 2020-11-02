package com.example.lms.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.lms.R;
import com.example.lms.user.Home;

public class SplashScreen extends AppCompatActivity {

    private int SLEEP_TIMER = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow() .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        //calling logo launcher method
        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }

    //The logo launcher class
    private class LogoLauncher  extends Thread{

        //adding a try catch method and setting a time to display
        public void run(){
            try {
                sleep(1000 * SLEEP_TIMER);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

            //Calling next activity
            Intent intent = new Intent(SplashScreen.this, StartupScreen.class);
            startActivity(intent);
            SplashScreen.this.finish();
        }
    }
}
