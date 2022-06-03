package com.example.expensly.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

    private Constants() {}

    public static final String[] CATEGORIES = new String[] {
            "Grocery", "Transport", "Food & Drink", "Bills & Utility", "Personal"
    };

    public static final String[] INCOME_CATEGORIES = new String[] {
            "Salary", "Commission", "Bonus", "Freelance", "Investment"
    };


    public static final String[] ACCOUNTS = new String[] {
            "CHEQUING", "SAVING", "CASH"
    };

    public static final String[] MONTHS = new String[] {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    public static final String[] YEARS = new String[]{
            "2022",
            "2021"
    };

    public static Integer currentLoggedInUserId = -1;
    public static String currentLoggedInUserName = "";
    public static String currentLoggedInUserEmail = "";

}
