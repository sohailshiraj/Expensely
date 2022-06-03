package com.example.expensly.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.expensly.MainActivity;
import com.example.expensly.R;
import com.example.expensly.utility.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //Getting reference for UI elements
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView email = (TextView) view.findViewById(R.id.email);

        //Setting constants
        name.setText(Constants.currentLoggedInUserName);
        email.setText(Constants.currentLoggedInUserEmail);

        //Event listener for logout button
        Button logoutBtn = (Button) view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Resetting constants
                Constants.currentLoggedInUserId = -1;
                Constants.currentLoggedInUserName = "";
                Constants.currentLoggedInUserEmail = "";
                //Declare and initialize a new intent object
                Intent myIntent = new Intent(getActivity(), MainActivity.class);
                //switch to another activity: SignUpActivity
                startActivity(myIntent);
            }
        });

        return view;
    }
}