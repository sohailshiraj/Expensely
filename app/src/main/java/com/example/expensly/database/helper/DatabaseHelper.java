package com.example.expensly.database.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.expensly.database.models.Account;
import com.example.expensly.database.models.Transaction;
import com.example.expensly.database.models.User;
import com.example.expensly.utility.AccountType;
import com.example.expensly.utility.TransactionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Instance of dbHelper
    private static DatabaseHelper sInstance;

    // Method to make class singleton, so that only one instance exists at a time
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    // DatabaseHelper constructor
    private DatabaseHelper(Context context) {
        super(context, DBConstant.DB_NAME, null, DBConstant.DB_VERSION);
    }

    // Overridden method to create all required tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConstant.CREATE_USER_TABLE);
        db.execSQL(DBConstant.CREATE_ACCOUNT_TABLE);
        db.execSQL(DBConstant.CREATE_TRANSACTION_TABLE);
    }

    // Overridden method to drop and create all required tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(DBConstant.DROP_TRANSACTION_TABLE);
            db.execSQL(DBConstant.DROP_ACCOUNT_TABLE);
            db.execSQL(DBConstant.DROP_USER_TABLE);
            onCreate(db);
        }
    }

    // Method to create a user, while signing up
    public User createUser(User user) {
        // Fetching database instance
        SQLiteDatabase db = this.getWritableDatabase();

        // Starting transaction
        db.beginTransaction();
        try {
            // Setting column values in table
            ContentValues values = new ContentValues();
            values.put(DBConstant.USER_NAME, user.getName());
            values.put(DBConstant.USER_EMAIL, user.getEmail());
            values.put(DBConstant.USER_PASSWORD, user.getPassword());

            // Executing query, which will create a user and return its id
            long userId = db.insertOrThrow(DBConstant.USER_TABLE, null, values);

            // Setting userId in user model
            user.setId((int) userId);

            // Setting up all accounts of user
            setupAccount((int) userId);

            // Marking transaction as successful
            db.setTransactionSuccessful();

        } catch (Exception ex) {
            Log.d("ERROR", ex.getMessage());
        } finally {
            // Ending the transaction to commit all the changes
            db.endTransaction();
        }
        return user;
    }

    // Method to get user by email and password, while login
    @SuppressLint("Range")
    public User getUserByEmailAndPassword(String email, String password) {
        // Fetching database instance
        SQLiteDatabase db = this.getReadableDatabase();

        // Creating cursor to prepare a query to fetch user by email and password for login
        Cursor cursor = db.rawQuery(
                String.format(
                        DBConstant.GET_USER_BY_EMAIL_PASSWORD,
                        email,
                        password), null);

        User user = null;
        try {
            // Iterating over cursor to get user details and setting values in model
            if (cursor.moveToFirst()) {
                do {
                    user = new User();
                    user.setId(cursor.getInt(cursor.getColumnIndex(DBConstant.USER_ID)));
                    user.setName(cursor.getString(cursor.getColumnIndex(DBConstant.USER_NAME)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(DBConstant.USER_EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(DBConstant.USER_PASSWORD)));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        } finally {
            // Closing the cursor
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return user;
    }

    // Method to setup all accounts for user
    public void setupAccount(Integer userId) {
        final Double balance = 0d;

        // Creating objects of all type of accounts
        Account chequingAccount = new Account(AccountType.CHEQUING.name(), userId, balance);
        Account savingAccount = new Account(AccountType.SAVING.name(), userId, balance);
        Account cashAccount = new Account(AccountType.CASH.name(), userId, balance);

        // Calling method to create accounts entry in database
        createAccount(chequingAccount);
        createAccount(savingAccount);
        createAccount(cashAccount);
    }

    // Method to create an account for user
    public Account createAccount(Account account) {
        // Fetching database instance
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Setting column values in table
            ContentValues values = new ContentValues();
            values.put(DBConstant.ACCOUNT_TYPE, account.getType());
            values.put(DBConstant.ACCOUNT_USER_ID, account.getUserId());
            values.put(DBConstant.ACCOUNT_BALANCE, account.getBalance());

            // Executing query, which will create an account and return its id
            long accountId = db.insertOrThrow(DBConstant.ACCOUNT_TABLE, null, values);

            System.out.println(account.getBalance());
            // Setting accountId in account model
            account.setId((int) accountId);

        } catch (Exception ex) {
            Log.d("ERROR", ex.getMessage());
        }
        return account;
    }

    // Method to get account of user by userId and account type
    @SuppressLint("Range")
    public Account getAccountByUserAndType(Integer userId, String type) {
        // Fetching database instance
        SQLiteDatabase db = this.getReadableDatabase();

        // Creating cursor to prepare a query to fetch account by userId and account type
        Cursor cursor = db.rawQuery(
                String.format(
                        DBConstant.GET_ACCOUNT_BY_USER_AND_TYPE,
                        userId,
                        type), null);

        System.out.println(String.format(
                DBConstant.GET_ACCOUNT_BY_USER_AND_TYPE,
                userId,
                type));

        Account account = new Account();
        try {
            // Iterating over cursor to get account details and setting values in model
            if (cursor.moveToFirst()) {
                do {
                    account.setId(cursor.getInt(cursor.getColumnIndex(DBConstant.ACCOUNT_ID)));
                    account.setType(cursor.getString(cursor.getColumnIndex(DBConstant.ACCOUNT_TYPE)));
                    account.setUserId(cursor.getInt(cursor.getColumnIndex(DBConstant.ACCOUNT_USER_ID)));
                    account.setBalance(cursor.getDouble(cursor.getColumnIndex(DBConstant.ACCOUNT_BALANCE)));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        } finally {
            // Closing the cursor
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return account;
    }

    // Method to create transactions
    public Transaction createTransaction(Transaction transaction) {
        // Fetching database instance
        SQLiteDatabase db = this.getWritableDatabase();

        // Starting transaction
        db.beginTransaction();
        try {
            // Setting column values in table
            ContentValues values = new ContentValues();
            values.put(DBConstant.TRANSACTION_TYPE, transaction.getType());
            values.put(DBConstant.TRANSACTION_CATEGORY, transaction.getCategory());
            values.put(DBConstant.TRANSACTION_USER_ID, transaction.getUserId());
            values.put(DBConstant.TRANSACTION_AMOUNT, transaction.getAmount());
            values.put(DBConstant.TRANSACTION_DESCRIPTION, transaction.getDescription());
            values.put(DBConstant.TRANSACTION_DATE, transaction.getDate());

            if (transaction.getFromAccount() != null) {
                values.put(DBConstant.TRANSACTION_FROM_ACCOUNT, transaction.getFromAccount());
            }
            if (transaction.getToAccount() != null) {
                values.put(DBConstant.TRANSACTION_TO_ACCOUNT, transaction.getToAccount());
            }

            // Executing query, which will create a transaction and return its id
            long transactionId = db.insertOrThrow(DBConstant.TRANSACTION_TABLE, null, values);

            // Setting transactionId in transaction model
            transaction.setId((int) transactionId);

            // Update balance of account based on transaction
            updateAccountBalance(transaction);

            // Marking transaction as successful
            db.setTransactionSuccessful();

        } catch (Exception ex) {
            Log.d("ERROR", ex.getMessage());
        } finally {
            // Ending the transaction to commit all the changes
            db.endTransaction();
        }
        return transaction;
    }

    // Method to update balance of account using transaction
    private void updateAccountBalance(Transaction transaction) {
        // If amount is less than or equal to zero, terminate operation
        if (transaction.getAmount() <= 0) {
            return;
        }

        // Calculate negative amount
        Double negativeAmount = transaction.getAmount() * (-1);

        System.out.println("SS");

        if (transaction.getType().equals(TransactionType.EXPENSE.name())) {
            // Deducting balance from account, against expense
            updateAccountBalance(
                    transaction.getUserId(),
                    transaction.getFromAccount(),
                    negativeAmount
            );
        } else if (transaction.getType().equals(TransactionType.INCOME.name())) {
            // Adding balance to account, against income
            updateAccountBalance(
                    transaction.getUserId(),
                    transaction.getToAccount(),
                    transaction.getAmount()
            );
        } else if (transaction.getType().equals(TransactionType.TRANSFER.name())) {
            // Deducting balance from one account
            updateAccountBalance(
                    transaction.getUserId(),
                    transaction.getFromAccount(),
                    negativeAmount
            );
            // Adding balance to another account
            updateAccountBalance(
                    transaction.getUserId(),
                    transaction.getFromAccount(),
                    transaction.getAmount()
            );
        }
    }

    // Method to update account balance based on user and account type
    private void updateAccountBalance(Integer userId, String accountType, Double amount) {
        // Fetching database instance
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Fetching user account
            Account account = getAccountByUserAndType(userId, accountType);
            System.out.println("SS");
            System.out.println(account.getBalance());
            // Updating balance with new amount
            Double updatedBalance = account.getBalance() + amount;

            // Setting column values in table
            ContentValues values = new ContentValues();
            values.put(DBConstant.ACCOUNT_BALANCE, updatedBalance);

            // Prepare where conditions
            String whereClause =
                    DBConstant.ACCOUNT_USER_ID + " = ? AND " +
                    DBConstant.ACCOUNT_TYPE + " = ?";

            // Setting values for fields in where clause
            String[] whereClauseValues = new String[] {
                    String.valueOf(userId),
                    accountType
            };

            // Executing query, which will update account balance with new value
            db.update(DBConstant.ACCOUNT_TABLE, values, whereClause, whereClauseValues);

        } catch (Exception ex) {
            Log.d("ERROR", ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Method to get monthly transactions by userId
    @SuppressLint("Range")
    public List<Transaction> getTransactionsByUserMonthYear(Integer userId, String month, String year) {
        // Fetching database instance
        SQLiteDatabase db = this.getReadableDatabase();

        // Creating cursor to prepare a query to fetch transactions
        Cursor cursor = db.rawQuery(
                String.format(
                        DBConstant.GET_TRANSACTIONS_BY_USER_MONTH_YEAR,
                        userId,
                        month,
                        year), null);

        List<Transaction> transactions = new ArrayList<>();
        try {
            // Iterating over cursor to get transaction details and setting values in model
            if (cursor.moveToFirst()) {
                do {
                    Transaction transaction = new Transaction();

                    transaction.setId(cursor.getInt(cursor.getColumnIndex(DBConstant.TRANSACTION_ID)));
                    transaction.setType(cursor.getString(cursor.getColumnIndex(DBConstant.TRANSACTION_TYPE)));
                    transaction.setAmount(cursor.getDouble(cursor.getColumnIndex(DBConstant.TRANSACTION_AMOUNT)));
                    transaction.setCategory(cursor.getString(cursor.getColumnIndex(DBConstant.TRANSACTION_CATEGORY)));
                    transaction.setDescription(cursor.getString(cursor.getColumnIndex(DBConstant.TRANSACTION_DESCRIPTION)));
                    transaction.setFromAccount(cursor.getString(cursor.getColumnIndex(DBConstant.TRANSACTION_FROM_ACCOUNT)));
                    transaction.setToAccount(cursor.getString(cursor.getColumnIndex(DBConstant.TRANSACTION_TO_ACCOUNT)));
                    transaction.setDate(cursor.getString(cursor.getColumnIndex(DBConstant.TRANSACTION_DATE)));

                    transactions.add(transaction);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        } finally {
            // Closing the cursor
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return transactions;
    }

    // Method to get total amount of transactions based on user, month, year and type of transaction
    @SuppressLint("Range")
    public Double getTotalAmountByUserMonthYearType(Integer userId, String type, String month, String year) {
        // Fetching database instance
        SQLiteDatabase db = this.getReadableDatabase();

        double totalAmount = 0d;

        // Creating cursor to prepare a query to fetch transactions amount
        Cursor cursor = db.rawQuery(
                String.format(
                        DBConstant.GET_TOTAL_AMOUNT_BY_USER_MONTH_YEAR,
                        userId,
                        type,
                        month,
                        year), null);

        try {
            // Iterating over cursor to get total transaction amount
            if (cursor.moveToFirst()) {
                do {
                    totalAmount = cursor.getDouble(cursor.getColumnIndex(DBConstant.TRANSACTION_AMOUNT));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        } finally {
            // Closing the cursor
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return totalAmount;
    }

    // Method to get total amount of transactions based on user, month, year, and type group by category
    @SuppressLint("Range")
    public Map<String, Double> getTotalAmountByUserMonthYearTypeCategory(
            Integer userId, String type, String month, String year) {

        // Fetching database instance
        SQLiteDatabase db = this.getReadableDatabase();

        // Creating cursor to prepare a query to fetch transactions amount group by category
        Cursor cursor = db.rawQuery(
                String.format(
                        DBConstant.GET_TOTAL_AMOUNT_BY_USER_MONTH_YEAR_CATEGORY,
                        userId,
                        type,
                        month,
                        year), null);

        Map<String, Double> amountByCategory = new HashMap<>();
        try {
            // Iterating over cursor to get total transaction amount for each category
            if (cursor.moveToFirst()) {
                do {
                    amountByCategory.put(
                            cursor.getString(cursor.getColumnIndex(DBConstant.TRANSACTION_CATEGORY)),
                            cursor.getDouble(cursor.getColumnIndex(DBConstant.TRANSACTION_AMOUNT))
                    );
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        } finally {
            // Closing the cursor
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return amountByCategory;
    }

}
