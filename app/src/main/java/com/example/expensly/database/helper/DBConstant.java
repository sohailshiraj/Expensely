package com.example.expensly.database.helper;

public class DBConstant {

    private DBConstant() {}

    // Database common properties
    public static final Integer DB_VERSION = 1;
    public static final String DB_NAME = "db.expensly";

    // User table properties
    public static final String USER_TABLE = "user";
    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";

    // Create user table query
    public static final String CREATE_USER_TABLE =
            "CREATE TABLE " + USER_TABLE + " ( " +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_NAME + " TEXT NOT NULL," +
                    USER_EMAIL + " TEXT NOT NULL," +
                    USER_PASSWORD + " TEXT NOT NULL);";

    // Query user by email and password
    public static final String GET_USER_BY_EMAIL_PASSWORD =
            "SELECT * FROM " + USER_TABLE + " WHERE " +
                    USER_EMAIL + " = '%s' AND " +
                    USER_PASSWORD + " = '%s';";

    // Drop user table query
    public static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE;

    // Account table properties
    public static final String ACCOUNT_TABLE = "account";
    public static final String ACCOUNT_ID = "id";
    public static final String ACCOUNT_TYPE = "type";
    public static final String ACCOUNT_USER_ID = "userId";
    public static final String ACCOUNT_BALANCE = "balance";

    // Create account table query
    public static final String CREATE_ACCOUNT_TABLE =
            "CREATE TABLE " + ACCOUNT_TABLE + " ( " +
                    ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ACCOUNT_TYPE + " TEXT NOT NULL," +
                    ACCOUNT_USER_ID + " INTEGER NOT NULL," +
                    ACCOUNT_BALANCE + " REAL NOT NULL);";

    // Drop account table query
    public static final String DROP_ACCOUNT_TABLE = "DROP TABLE IF EXISTS " + ACCOUNT_TABLE;

    // Query account by user and type
    public static final String GET_ACCOUNT_BY_USER_AND_TYPE =
            "SELECT * FROM " + ACCOUNT_TABLE + " WHERE " +
                    ACCOUNT_USER_ID + " = %s AND " +
                    ACCOUNT_TYPE + " = '%s';";

    // Update account balance by user and type
    public static final String UPDATE_ACCOUNT_BALANCE_BY_USER_AND_TYPE =
            "UPDATE " + ACCOUNT_TABLE + " SET " +
                    ACCOUNT_BALANCE + " = %s WHERE " +
                    ACCOUNT_USER_ID + " = %s" +
                    ACCOUNT_TYPE + " = '%s';";

    // Transaction table properties
    public static final String TRANSACTION_TABLE = "transactions";
    public static final String TRANSACTION_ID = "id";
    public static final String TRANSACTION_USER_ID = "userId";
    public static final String TRANSACTION_AMOUNT = "amount";
    public static final String TRANSACTION_TYPE = "type";
    public static final String TRANSACTION_TO_ACCOUNT = "fromAccount";
    public static final String TRANSACTION_FROM_ACCOUNT = "toAccount";
    public static final String TRANSACTION_CATEGORY = "category";
    public static final String TRANSACTION_DESCRIPTION = "description";
    public static final String TRANSACTION_DATE = "date";

    // Create transaction table query
    public static final String CREATE_TRANSACTION_TABLE =
            "CREATE TABLE " + TRANSACTION_TABLE + " ( " +
                    TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TRANSACTION_USER_ID + " INTEGER NOT NULL," +
                    TRANSACTION_AMOUNT + " REAL NOT NULL," +
                    TRANSACTION_TYPE + " TEXT NOT NULL," +
                    TRANSACTION_TO_ACCOUNT + " TEXT," +
                    TRANSACTION_FROM_ACCOUNT + " TEXT," +
                    TRANSACTION_CATEGORY + " TEXT," +
                    TRANSACTION_DESCRIPTION + " TEXT NOT NULL," +
                    TRANSACTION_DATE + " TEXT NOT NULL);";

    // Drop transaction table query
    public static final String DROP_TRANSACTION_TABLE = "DROP TABLE IF EXISTS " + TRANSACTION_TABLE;

    // Query all transactions by user, month and year
    public static final String GET_TRANSACTIONS_BY_USER_MONTH_YEAR =
            "SELECT * FROM " + TRANSACTION_TABLE + " WHERE " +
                    TRANSACTION_USER_ID + " = %s AND " +
                    "STRFTIME('%%m', DATE(" + TRANSACTION_DATE + ")) = '%s' AND " +
                    "STRFTIME('%%Y', DATE(" + TRANSACTION_DATE + ")) = '%s';";

    // Query total amount by user, month and year
    public static final String GET_TOTAL_AMOUNT_BY_USER_MONTH_YEAR =
            "SELECT SUM(amount) AS " + TRANSACTION_AMOUNT + " FROM " + TRANSACTION_TABLE + " WHERE " +
                    TRANSACTION_USER_ID + " = %s AND " +
                    TRANSACTION_TYPE + " = '%s' AND " +
                    "STRFTIME('%%m', DATE(" + TRANSACTION_DATE + ")) = '%s' AND " +
                    "STRFTIME('%%Y', DATE(" + TRANSACTION_DATE + ")) = '%s';";

    // Query total amount by user, month, year group by category
    public static final String GET_TOTAL_AMOUNT_BY_USER_MONTH_YEAR_CATEGORY =
            "SELECT " + TRANSACTION_CATEGORY + ", SUM(" + TRANSACTION_AMOUNT + ") AS " + TRANSACTION_AMOUNT +
                    " FROM " + TRANSACTION_TABLE + " WHERE " +
                    TRANSACTION_USER_ID + " = %s AND " +
                    TRANSACTION_TYPE + " = '%s' AND " +
                    "STRFTIME('%%m', DATE(" + TRANSACTION_DATE + ")) = '%s' AND " +
                    "STRFTIME('%%Y', DATE(" + TRANSACTION_DATE + ")) = '%s' " +
                    "GROUP BY " + TRANSACTION_CATEGORY;

}
