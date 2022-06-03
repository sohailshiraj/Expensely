package com.example.expensly.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expensly.R;
import com.example.expensly.database.helper.DatabaseHelper;
import com.example.expensly.utility.Constants;
import com.example.expensly.utility.TransactionType;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class InsightFragment extends Fragment {

    //Declaring all UI elements
    TextView totalMonthlyExpense;
    TextView groceryVal;
    TextView foodDrinkVal;
    TextView homeVal;
    TextView utilityVal;
    TextView transportVal;
    TextView otherVal;

    TextView totalMonthlyIncome;
    TextView salaryVal;
    TextView commVal;
    TextView bonusVal;
    TextView freelanceVal;
    TextView investVal;
    TextView otherIncVal;

    public InsightFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insight, container, false);

        //Getting reference of UI elements
        totalMonthlyExpense = (TextView) view.findViewById(R.id.totalMonthlyExpense);
        groceryVal = (TextView) view.findViewById(R.id.groceryVal);
        foodDrinkVal = (TextView) view.findViewById(R.id.foodDrinkVal);
        homeVal = (TextView) view.findViewById(R.id.homeVal);
        utilityVal = (TextView) view.findViewById(R.id.utilityVal);
        transportVal = (TextView) view.findViewById(R.id.transportVal);
        otherVal = (TextView) view.findViewById(R.id.otherVal);

        //Getting reference of UI elements
        totalMonthlyIncome = (TextView) view.findViewById(R.id.totalMonthlyIncome);
        salaryVal = (TextView) view.findViewById(R.id.salaryVal);
        commVal = (TextView) view.findViewById(R.id.commVal);
        bonusVal = (TextView) view.findViewById(R.id.bonusVal);
        freelanceVal = (TextView) view.findViewById(R.id.freelanceVal);
        investVal = (TextView) view.findViewById(R.id.investVal);
        otherIncVal = (TextView) view.findViewById(R.id.otherIncVal);

        //Getting Reference of database singleton class.
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());

        //Getting Data and storing
        Map<String, Double> ExpenseMap = db.getTotalAmountByUserMonthYearTypeCategory(Constants.currentLoggedInUserId, TransactionType.EXPENSE.name(), "04", "2022");
        Map<String, Double> IncomeMap = db.getTotalAmountByUserMonthYearTypeCategory(Constants.currentLoggedInUserId, TransactionType.INCOME.name(), "04", "2022");

        //Getting Data and storing
        Double expense = db.getTotalAmountByUserMonthYearType(Constants.currentLoggedInUserId, TransactionType.EXPENSE.name(), "04", "2022");
        Double income = db.getTotalAmountByUserMonthYearType(Constants.currentLoggedInUserId, TransactionType.INCOME.name(), "04", "2022");

        //Storing the data from maps to TextView
        groceryVal.setText(String.valueOf(getValFromMap("Grocery", ExpenseMap)));
        foodDrinkVal.setText(String.valueOf(getValFromMap("Food & Drink", ExpenseMap)));
        homeVal.setText(String.valueOf(getValFromMap("Personal", ExpenseMap)));
        utilityVal.setText(String.valueOf(getValFromMap("Bills & Utility", ExpenseMap)));
        transportVal.setText(String.valueOf(getValFromMap("Transport", ExpenseMap)));
        otherVal.setText(String.valueOf(0d));
        totalMonthlyExpense.setText(expense != null? expense.toString(): "0.0");

        salaryVal.setText(String.valueOf(getValFromMap("Salary", IncomeMap)));
        commVal.setText(String.valueOf(getValFromMap("Commission", IncomeMap)));
        bonusVal.setText(String.valueOf(getValFromMap("Bonus", IncomeMap)));
        freelanceVal.setText(String.valueOf(getValFromMap("Freelance", IncomeMap)));
        investVal.setText(String.valueOf(getValFromMap("Investment", IncomeMap)));
        otherIncVal.setText(String.valueOf(0d));
        totalMonthlyIncome.setText(income != null? income.toString(): "0.0");

        return view;
    }

    //Helper function to replace getOrDefault
    private double getValFromMap(String str, Map<String, Double> map){
        return (map.get(str)) == null? 0d: map.get(str);
    }
}