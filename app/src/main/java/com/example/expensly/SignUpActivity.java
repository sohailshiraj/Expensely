package com.example.expensly;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.expensly.database.helper.DatabaseHelper;
import com.example.expensly.database.models.User;
import com.google.android.material.snackbar.Snackbar;

public class SignUpActivity extends AppCompatActivity {

    EditText nameVal;
    EditText emailVal;
    EditText passwordVal;
    EditText confirmPasswordVal;
    Button signupBtn;

    TextView login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setupUI();
        // Rendering UI
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    // Method to go to previous screen
    public void backOnClick(View view) {
        //Declare and initialize a new intent object
        Intent myIntent = new Intent(SignUpActivity.this, MainActivity.class);
        //switch to another activity: MainActivity
        startActivity(myIntent);
    }

    // Setting up all UI components
    private void setupUI(){
        nameVal = findViewById(R.id.nameSignupVal);
        emailVal = findViewById(R.id.emailSignupVal);
        passwordVal = findViewById(R.id.passwordSignupVal);
        confirmPasswordVal = findViewById(R.id.confirmPasswordSignupVal);

        login_btn = findViewById(R.id.login_btn);

        // Attaching onClick listener to login button
        login_btn.setOnClickListener(view -> {
            Intent myIntent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(myIntent);
        });

        signupBtn = findViewById(R.id.signup_btn);

        // Attaching onClick listener to signup button
        signupBtn.setOnClickListener(view -> {
            DatabaseHelper db = DatabaseHelper.getInstance(this);
            View container = findViewById(R.id.container);

            // Validating field while submitting
            if(passwordVal.getText().toString().equals("") || nameVal.getText().toString().equals("") || passwordVal.getText().toString().equals("")){
                Snackbar.make(container, R.string.form_values_required, Snackbar.LENGTH_LONG).show();
            }else if(!passwordVal.getText().toString().equals(confirmPasswordVal.getText().toString())){
                Snackbar.make(container, R.string.password_not_matched, Snackbar.LENGTH_LONG).show();
            }else {
                User user = new User();
                user.setName(nameVal.getText().toString());
                user.setEmail(emailVal.getText().toString());
                user.setPassword(passwordVal.getText().toString());

                user = db.createUser(user);

                if(user.getId()!= null){
                    // Redirecting to Login screen
                    Snackbar.make(container, R.string.signup_success, Snackbar.LENGTH_LONG).show();
                    Intent myIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(myIntent);
                }else {
                    Snackbar.make(container, R.string.signup_failed, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}