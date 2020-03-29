package com.google.firebase.samples.apps.mlkit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class activity_test_result extends AppCompatActivity {
    final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        for (int j = 0; j == 80; j++) {
            System.out.println(globalVariable.righteye[j]);
        }
    }
}
