package com.example.expensly.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.example.expensly.R;
import com.example.expensly.adapter.ListAdapter;
import com.example.expensly.database.helper.DatabaseHelper;
import com.example.expensly.database.models.Transaction;
import com.example.expensly.utility.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment {

    private AutoCompleteTextView monthVal;
    private AutoCompleteTextView yearVal;

    // Reference for array list
    private List<Transaction> transactions = new ArrayList<>();

    private RecyclerView transactionsRV;

    // Reference for ListAdapter class
    private ListAdapter adapter;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        setupUI(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void setupUI(View view) {
        //Reference of List Container
        transactionsRV = view.findViewById(R.id.transactionListContainer);

        //Getting reference of UI elements
        monthVal = view.findViewById(R.id.monthVal);
        monthVal.setText("April");
        yearVal = view.findViewById(R.id.yearVal);
        yearVal.setText("2022");

        //Instance of DB
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());

        //Getting Transaction by user month for first time
        transactions = db.getTransactionsByUserMonthYear(
                Constants.currentLoggedInUserId,
                String.format("%02d", getMonth()),
                getYear().toString()
        );
        bindAdapter();

        //Event listener for click listener for selecting month
        monthVal.setOnItemClickListener((adapterView, view12, i, l) -> {
            DatabaseHelper db12 = DatabaseHelper.getInstance(getContext());
            transactions = db12.getTransactionsByUserMonthYear(
                    Constants.currentLoggedInUserId,
                    String.format("%02d", getMonth()),
                    getYear().toString()
            );
            System.out.println(transactions.size());
            bindAdapter();
        });

        //Event listener for click listener for selecting year
        yearVal.setOnItemClickListener((adapterView, view1, i, l) -> {
            DatabaseHelper db1 = DatabaseHelper.getInstance(getContext());
            System.out.println("year1, ");
            transactions = db1.getTransactionsByUserMonthYear(
                    Constants.currentLoggedInUserId,
                    String.format("%02d", getMonth()),
                    getYear().toString()
            );
            bindAdapter();
        });

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, Constants.MONTHS);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, Constants.YEARS);

        //Setting dropdown values to both auto complete fields
        monthVal.setAdapter(monthAdapter);
        yearVal.setAdapter(yearAdapter);
    }

    // Binding Adapter to recycler view
    private void bindAdapter() {
        // Setting layout of recycler view to be a linear layout
        transactionsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        // Assigning list to adapter
        adapter = new ListAdapter(transactions, getContext());

        // Binding adapter to recycler view
        transactionsRV.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    //Helper function to get months
    private Integer getMonth() {
        Date date;
        Calendar cal = Calendar.getInstance();
        try {
            date = new SimpleDateFormat("MMMM", Locale.ENGLISH).parse(monthVal.getText().toString());
            cal.setTime(date);
        } catch (ParseException e) {
            Log.d("ERROR", e.getMessage());
        }
        return cal.get(Calendar.MONTH) + 1;
    }
    //Helper function to get years
    private Integer getYear() {
        Date date;
        Calendar cal = Calendar.getInstance();
        try {
            date = new SimpleDateFormat("yy", Locale.ENGLISH).parse(yearVal.getText().toString());
            cal.setTime(date);
        } catch (ParseException e) {
            Log.d("ERROR", e.getMessage());
        }
        return cal.get(Calendar.YEAR);
    }
}
