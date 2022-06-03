package com.example.expensly.database.models;

public class Account {

    private Integer id;
    private String type;
    private Integer userId;
    private Double balance;

    public Account() {}

    public Account(Integer id, String type, Integer userId, Double balance) {
        this.id = id;
        this.type = type;
        this.userId = userId;
        this.balance = balance;
    }

    public Account(String type, Integer userId, Double balance) {
        this.type = type;
        this.userId = userId;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
