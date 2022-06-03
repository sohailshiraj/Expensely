package com.example.expensly.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expensly.R;
import com.example.expensly.database.helper.DatabaseHelper;
import com.example.expensly.database.models.Account;
import com.example.expensly.utility.AccountType;
import com.example.expensly.utility.Constants;
import com.example.expensly.utility.TransactionType;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupUI(view);

        return view;
    }

    //Setting up UI and its elements
    private void setupUI(View view) {
        //Declaring all UI elements and defining
        TextView allAccountAmnt = view.findViewById(R.id.allAccountAmnt);
        TextView chequingAmnt = view.findViewById(R.id.chequingAmnt);
        TextView savingAmnt = view.findViewById(R.id.savingAmnt);
        TextView cashAmnt = view.findViewById(R.id.cashAmnt);
        TextView totalAmnt = view.findViewById(R.id.totalAmnt);
        TextView totalExpense = view.findViewById(R.id.totalExpense);
        TextView totalIncome = view.findViewById(R.id.totalIncome);

        //Getting instance of database helper classs
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());

        //getting total monthly expenses
        Double totalMonthlyExpense = db.getTotalAmountByUserMonthYearType(
                Constants.currentLoggedInUserId,
                TransactionType.EXPENSE.name(),
                "04",
                "2022"
        );

        //getting total monthly income
        Double totalMonthlyIncome = db.getTotalAmountByUserMonthYearType(
                Constants.currentLoggedInUserId,
                TransactionType.INCOME.name(),
                "04",
                "2022"
        );

        //getting Saving account details
        Account savingAccount = db.getAccountByUserAndType(
                Constants.currentLoggedInUserId,
                AccountType.SAVING.name()
        );

        //getting Chequing account details
        Account chequingAccount = db.getAccountByUserAndType(
                Constants.currentLoggedInUserId,
                AccountType.CHEQUING.name()
        );

        //getting cash account details
        Account cashAccount = db.getAccountByUserAndType(
                Constants.currentLoggedInUserId,
                AccountType.CASH.name()
        );

        System.out.println(savingAccount.getBalance());
        System.out.println(chequingAccount.getBalance());
        System.out.println(cashAccount.getBalance());

        //Calculating Total Amount
        Double totalAmount = savingAccount.getBalance() +
                        chequingAccount.getBalance() +
                        cashAccount.getBalance();

        //Setting all the values from DB to UI elements
        totalExpense.setText(String.valueOf(totalMonthlyExpense));
        totalIncome.setText(String.valueOf(totalMonthlyIncome));
        savingAmnt.setText(String.valueOf(savingAccount.getBalance()));
        chequingAmnt.setText(String.valueOf(chequingAccount.getBalance()));
        cashAmnt.setText(String.valueOf(cashAccount.getBalance()));
        allAccountAmnt.setText(String.valueOf(totalAmount));
        totalAmnt.setText(String.valueOf(totalAmount));
    }
}