package com.example.expensly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensly.R;
import com.example.expensly.database.models.Transaction;
import com.example.expensly.utility.TransactionType;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Transaction> transactions;

    //Constructor
    public ListAdapter(List<Transaction> list, Context context){
        super();
        transactions = list;
    }

    /*
        ViewHolder class which will hold layout of each items in the list
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView category;
        private TextView account;
        private TextView description;
        private TextView amount;
        private TextView date;

        //Constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initializing all components in layout
            icon = itemView.findViewById(R.id.transIcon);
            category = itemView.findViewById(R.id.transCategory);
            account = itemView.findViewById(R.id.transAccount);
            description = itemView.findViewById(R.id.transDescription);
            amount = itemView.findViewById(R.id.transAmount);
            date = itemView.findViewById(R.id.transDate);
        }
    }

    /*
        Overriding function for onCreate to bind layout to View holder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
        // Inflating our record layout into view and so that it can be displayed in recycler view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_record, parent, false);
        return new ViewHolder(v);
    }

    /*
        Overriding function to bind UI elements to View holder class
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Binding data to our view holder to show dynamic data on runtime
        Transaction itemAdapter = transactions.get(position);

        String account = "";
        if (itemAdapter.getType().equals(TransactionType.EXPENSE.name())) {
            account = itemAdapter.getFromAccount();
            ((ViewHolder) holder).icon.setImageResource(R.drawable.ic_baseline_trending_down_24);

        } else if (itemAdapter.getType().equals(TransactionType.INCOME.name())) {
            account = itemAdapter.getToAccount();
            ((ViewHolder) holder).icon.setImageResource(R.drawable.ic_baseline_trending_up_24);

        } else if (itemAdapter.getType().equals(TransactionType.TRANSFER.name())) {
            account = itemAdapter.getFromAccount() + " > " + itemAdapter.getToAccount();
            ((ViewHolder) holder).icon.setImageResource(R.drawable.ic_baseline_compare_arrows_24);
        }

        // Setting values of all required fields
        ((ViewHolder) holder).account.setText(account);
        ((ViewHolder) holder).category.setText(itemAdapter.getCategory());
        ((ViewHolder) holder).description.setText(itemAdapter.getDescription());
        ((ViewHolder) holder).amount.setText("CAD " + itemAdapter.getAmount());
        ((ViewHolder) holder).date.setText(itemAdapter.getDate());
    }

    /*
        Method to get size of our list
     */
    @Override
    public int getItemCount() { return transactions.size(); }

}
