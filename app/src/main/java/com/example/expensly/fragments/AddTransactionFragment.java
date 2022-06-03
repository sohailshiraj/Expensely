package com.example.expensly.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.expensly.R;
import com.example.expensly.fragments.transaction.ExpenseFragment;
import com.example.expensly.fragments.transaction.IncomeFragment;
import com.example.expensly.fragments.transaction.TransferFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTransactionFragment extends Fragment {

    private Button expenseBtn;
    private Button incomeBtn;
    private Button transferBtn;

    public AddTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_transaction, container, false);
        setupUI(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void setupUI(View view) {
        //Getting References of UI elements
        expenseBtn = view.findViewById(R.id.expenseBtn);
        incomeBtn = view.findViewById(R.id.incomeBtn);
        transferBtn = view.findViewById(R.id.transferBtn);
        expenseBtn.setBackgroundColor(getResources().getColor(R.color.second_color));

        //By default loading Expense fragment on page load
        Fragment selectedFragment1 = new ExpenseFragment();
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.transaction_fragment_container, selectedFragment1)
                .commit();

        //listener for the viewing expense form
        expenseBtn.setOnClickListener(v -> {
            expenseBtn.setBackgroundColor(getResources().getColor(R.color.second_color));
            incomeBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
            transferBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
            Fragment selectedFragment = new ExpenseFragment();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.transaction_fragment_container, selectedFragment)
                    .commit();
        });

        //listener for the viewing Income form
        incomeBtn.setOnClickListener(v -> {
            expenseBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
            incomeBtn.setBackgroundColor(getResources().getColor(R.color.second_color));
            transferBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
            Fragment selectedFragment = new IncomeFragment();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.transaction_fragment_container, selectedFragment)
                    .commit();
        });

        //listener for the viewing transfer form
        transferBtn.setOnClickListener(v -> {
            expenseBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
            incomeBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
            transferBtn.setBackgroundColor(getResources().getColor(R.color.second_color));
            Fragment selectedFragment = new TransferFragment();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.transaction_fragment_container, selectedFragment)
                    .commit();
        });
    }
}
