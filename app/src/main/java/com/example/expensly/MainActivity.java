package com.example.expensly;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Rendering UI
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void loginOnClick(View view) {
        //Declare and initialize a new intent object
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        //switch to another activity: LoginActivity
        startActivity(myIntent);
    }

    public void signUpOnClick(View view) {
        //Declare and initialize a new intent object
        Intent myIntent = new Intent(MainActivity.this, SignUpActivity.class);
        //switch to another activity: SignUpActivity
        startActivity(myIntent);
    }

}