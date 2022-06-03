package com.example.expensly.database.models;

import java.util.Date;

public class Transaction {

    private Integer id;
    private Integer userId;
    private Double amount;
    private String type;
    private String description;
    private String category;
    private String fromAccount;
    private String toAccount;
    private String date;

    public Transaction() {}

    public Transaction(Integer id, Integer userId, Double amount, String type, String description,
                       String category, String fromAccount, String toAccount, String date) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.category = category;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
