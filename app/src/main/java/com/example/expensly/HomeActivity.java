package com.example.expensly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.expensly.fragments.AddTransactionFragment;
import com.example.expensly.fragments.HomeFragment;
import com.example.expensly.fragments.InsightFragment;
import com.example.expensly.fragments.ProfileFragment;
import com.example.expensly.fragments.TransactionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Rendering UI
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setupUI();
    }

    //Setting up UI for Home screen
    private void setupUI() {

        //By default selecting home fragment
        Fragment selectedFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment1 = null;

            //Switch case to select which fragment to load
            switch (item.getItemId()) {
                case R.id.home:
                    selectedFragment1 = new HomeFragment();
                    break;
                case R.id.transaction:
                    selectedFragment1 = new TransactionFragment();
                    break;
                case R.id.profile:
                    selectedFragment1 = new ProfileFragment();
                    break;
                case R.id.insights:
                    selectedFragment1 = new InsightFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment1).commit();
            return true;
        });

        //Event listener for clicking floating button
        FloatingActionButton fab = findViewById(R.id.addTransBtn);
        fab.setOnClickListener(view -> {
            Fragment selectedFragment12 = new AddTransactionFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment12).commit();
        });
    }

}