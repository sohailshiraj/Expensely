package com.example.expensly;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.expensly.database.helper.DatabaseHelper;
import com.example.expensly.database.models.User;
import com.example.expensly.utility.Constants;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    //Declaring UI elements
    private EditText emailVal;
    private EditText passwordVal;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUI();
    }

    //event handler for back click function
    public void backOnClick(View view) {
        //Declare and initialize a new intent object
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        //switch to another activity: MainActivity
        startActivity(myIntent);
    }

    //Function to setup UI
    public void setupUI() {
        // Rendering UI
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //Getting reference of UI elements
        emailVal = findViewById(R.id.emailLoginVal);
        passwordVal = findViewById(R.id.passwordLoginVal);
        loginButton = findViewById(R.id.loginBtn);
        View container = findViewById(R.id.container);

        //event listener for submit btn
        loginButton.setOnClickListener(view -> {
            //DB Reference
            DatabaseHelper db = DatabaseHelper.getInstance(this);

            String email = emailVal.getText().toString();
            String password = passwordVal.getText().toString();

            //Checking if form is empty or not
            if(email.equals("") || password.equals("")){
                Snackbar.make(container, R.string.form_values_required, Snackbar.LENGTH_LONG);
            }else {
                User user = db.getUserByEmailAndPassword(email, password);

                if (user != null) {
                    Constants.currentLoggedInUserId = user.getId();
                    Constants.currentLoggedInUserEmail = user.getEmail();
                    Constants.currentLoggedInUserName = user.getName();

                    Snackbar.make(container, R.string.login_success, Snackbar.LENGTH_LONG);
                    Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(myIntent);
                } else {
                    Snackbar.make(container, R.string.login_failed, Snackbar.LENGTH_LONG);
                }
            }
        });
    }
}
