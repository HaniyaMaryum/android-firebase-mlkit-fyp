package com.google.firebase.samples.apps.mlkit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        auth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (auth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(SplashScreen.this,signuppage.class));

                    finish();
                }
                else {
                    startActivity(new Intent(SplashScreen.this, loginpage.class));

                    finish();
                }
            }
        },5000);
    }
}
