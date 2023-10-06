package com.bondarenko.universityAssigment.lab5;

import java.math.BigDecimal;

public class BankAccount {
    // Реалізуйте клас `BankAccount` з членами класу `accountNumber`, `accountName` і `balance`.
    private final int accountNumber;
    private String accountName;
    private BigDecimal balance;

    public BankAccount(int accountNumber, String accountName, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
    }

    public BankAccount(int accountNumber, String accountName) {
        this(accountNumber, accountName, new BigDecimal(0));
    }

    public void deposit(double amount) {
        balance = balance.add(new BigDecimal(amount));
    }

    public void withdraw(double amount) {
        balance = balance.subtract(new BigDecimal(amount));
    }

    public double getBalance() {
        return balance.doubleValue();
    }

    public String getAccountSummary() {
        return "Bank account " +  accountName + " (" + accountNumber + "), balance: " + balance;
    }

    @Override
    public String toString() {
        return getAccountSummary();
    }
}
