package com.example.expensly.fragments.transaction;

import android.annotation.SuppressLint;
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
public class TransferFragment extends Fragment {

    //Declaring UI elements for expense fragment screen
    EditText amountVal;
    AutoCompleteTextView fromAccountVal;
    AutoCompleteTextView toAccountVal;
    EditText descriptionVal;
    EditText dateVal;
    ImageButton datePickerBtn;
    Button addTransactionBtn;

    public TransferFragment() {
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
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        setupUI(view);
        setupDatePicker();
        return view;
    }

    //Function for setting up UI elements: defining it and populating the data;
    private void setupUI(View view) {

        ArrayAdapter<String> accountAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, Constants.ACCOUNTS);

        dateVal = (EditText) view.findViewById(R.id.transferDateVal);
        datePickerBtn = (ImageButton) view.findViewById(R.id.transferDatePickerBtn);

        amountVal = (EditText) view.findViewById(R.id.transferAmountVal);
        toAccountVal = (AutoCompleteTextView) view.findViewById(R.id.transferToAccountVal);
        fromAccountVal = (AutoCompleteTextView) view.findViewById(R.id.transferFromAccountVal);
        descriptionVal = (EditText) view.findViewById(R.id.transferDescriptionVal);

        toAccountVal.setAdapter(accountAdapter);
        fromAccountVal.setAdapter(accountAdapter);

        //Event listener for submit button click
        addTransactionBtn = view.findViewById(R.id.addTransactionTransBtn);
        addTransactionBtn.setOnClickListener(v -> {
            Transaction transaction = new Transaction();
            View container = view.findViewById(R.id.container);

            //Checking if nothing in the form is empty
            if(descriptionVal.getText().toString().equals("") || toAccountVal.getText().toString().equals("") || fromAccountVal.getText().toString().equals("") || dateVal.getText().toString().equals("") || amountVal.getText().toString().equals("")){
                Snackbar.make(getActivity().findViewById(R.id.transContainer), R.string.form_values_required, Snackbar.LENGTH_LONG).show();
                return;
            }

            //If not, then make transaction type object and adding data
            transaction.setType(TransactionType.TRANSFER.name());
            transaction.setDescription(descriptionVal.getText().toString());
            transaction.setToAccount(toAccountVal.getText().toString());
            transaction.setFromAccount(fromAccountVal.getText().toString());
            transaction.setDate(dateVal.getText().toString());
            transaction.setAmount(Double.valueOf(amountVal.getText().toString()));
            transaction.setUserId(Constants.currentLoggedInUserId);

            //getting db instance
            DatabaseHelper db = DatabaseHelper.getInstance(getContext());
            transaction = db.createTransaction(transaction);

            //If successful: we got the transaction ID in return
            if (transaction.getId() != null) {
                System.out.println(transaction.getDate());
                Snackbar.make(getActivity().findViewById(R.id.transContainer), R.string.transfer_add_success, Snackbar.LENGTH_LONG).show();
                resetForm();
            } else {
                Snackbar.make(getActivity().findViewById(R.id.transContainer), R.string.transfer_add_failed, Snackbar.LENGTH_LONG).show();
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
        toAccountVal.setText("");
        fromAccountVal.setText("");
        dateVal.setText("");
        amountVal.setText("");
    }
}