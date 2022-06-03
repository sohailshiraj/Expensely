package com.example.expensly.fragments.transaction;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.expensly.R;
import com.example.expensly.database.helper.DatabaseHelper;
import com.example.expensly.database.models.Transaction;
import com.example.expensly.utility.Constants;
import com.example.expensly.utility.TransactionType;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {
    //Declaring UI elements for expense fragment screen
    EditText amountVal;
    AutoCompleteTextView accountVal;
    AutoCompleteTextView categoryVal;
    EditText descriptionVal;
    EditText dateVal;
    ImageButton datePickerBtn;

    Button addTransactionBtn;

    public IncomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        setupUI(view);
        setupDatePicker();

        return view;
    }

    //Function for setting up UI elements: defining it and populating the data;
    private void setupUI(View view) {
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, Constants.INCOME_CATEGORIES);

        ArrayAdapter<String> accountAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, Constants.ACCOUNTS);

        dateVal = (EditText) view.findViewById(R.id.incDateVal);
        datePickerBtn = (ImageButton) view.findViewById(R.id.incDatePickerBtn);

        amountVal = (EditText) view.findViewById(R.id.incAmountVal);
        accountVal = (AutoCompleteTextView) view.findViewById(R.id.incAccountVal);
        categoryVal = (AutoCompleteTextView) view.findViewById(R.id.incCategoryVal);
        descriptionVal = (EditText) view.findViewById(R.id.incDescriptionVal);

        categoryVal.setAdapter(categoryAdapter);
        accountVal.setAdapter(accountAdapter);

        //Event listener for submit button click
        addTransactionBtn = view.findViewById(R.id.addTransactionIncBtn);
        addTransactionBtn.setOnClickListener(v -> {
            Transaction transaction = new Transaction();

            //Checking if nothing in the form is empty
            if(descriptionVal.getText().toString().equals("") || categoryVal.getText().toString().equals("")
                    || accountVal.getText().toString().equals("") || dateVal.getText().toString().equals("")
                    || amountVal.getText().toString().equals("")){
                Snackbar.make(getActivity().findViewById(R.id.incContainer), R.string.form_values_required, Snackbar.LENGTH_LONG).show();
                return;
            }

            //If not, then make transaction type object and adding data
            transaction.setType(TransactionType.INCOME.name());
            transaction.setDescription(descriptionVal.getText().toString());
            transaction.setCategory(categoryVal.getText().toString());
            transaction.setToAccount(accountVal.getText().toString());
            transaction.setDate(dateVal.getText().toString());
            transaction.setAmount(Double.valueOf(amountVal.getText().toString()));
            transaction.setUserId(Constants.currentLoggedInUserId);

            //getting db instance
            DatabaseHelper db = DatabaseHelper.getInstance(getContext());
            transaction = db.createTransaction(transaction);

            //If successful: we got the transaction ID in return
            if (transaction.getId() != null) {
                Snackbar.make(getActivity().findViewById(R.id.incContainer), R.string.income_add_success, Snackbar.LENGTH_LONG).show();
                resetForm();
            } else {
                Snackbar.make(getActivity().findViewById(R.id.incContainer), R.string.income_add_failed, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    //helper function to setup datepicker material
    private void setupDatePicker(){
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();

        // now define the properties of the
        // materialDateBuilder that is title text as SELECT A DATE
        materialDateBuilder.setTitleText("SELECT A DATE");

        // now create the instance of the material date
        // picker
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        // handle select date button which opens the
        // material design date picker
        datePickerBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // getSupportFragmentManager() to
                        // interact with the fragments
                        // associated with the material design
                        // date picker tag is to get any error
                        // in logcat
                        materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });

        // now handle the positive button click from the
        // material design date picker
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                // Get the offset from our timezone and UTC.
                TimeZone timeZoneUTC = TimeZone.getDefault();
                // It will be negative, so that's the -1
                int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
                // Create a date format, then a date object with our offset
                SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date date = new Date(selection + offsetFromUTC);
                dateVal.setText(simpleFormat.format(date));
            }
        });
    }

    //Helper function to reset the form
    private void resetForm() {
        descriptionVal.setText("");
        categoryVal.setText("");
        accountVal.setText("");
        dateVal.setText("");
        amountVal.setText("");
    }
}