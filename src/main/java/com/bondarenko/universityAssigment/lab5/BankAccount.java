package com.bondarenko.universityAssigment.lab5;

import com.bondarenko.universityAssigment.lab5.exceptions.InsufficientFundsException;
import com.bondarenko.universityAssigment.lab5.exceptions.NegativeAmountException;

import java.math.BigDecimal;

public class BankAccount {
    private final int accountNumber;
    private String accountName;
    private BigDecimal balance;

    public BankAccount(int accountNumber, String accountName, double balance) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = new BigDecimal(balance);
    }

    public BankAccount(int accountNumber, String accountName) {
        this(accountNumber, accountName, 0);
    }

    public void deposit(double amount) throws NegativeAmountException {
        if (amount < 0) {
            throw new NegativeAmountException("Deposit amount cannot be negative");
        }

        balance = balance.add(new BigDecimal(amount));
    }

    public void withdraw(double amount) throws NegativeAmountException, InsufficientFundsException {
        if (amount < 0) {
            throw new NegativeAmountException("Withdraw amount cannot be negative");
        }
        if (amount > balance.doubleValue()) {
            throw new InsufficientFundsException("Insufficient funds to withdraw " + amount);
        }

        balance = balance.subtract(new BigDecimal(amount));
    }

    public double getBalance() {
        return balance.doubleValue();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountSummary() {
        return "Bank account " + accountName + " (" + accountNumber + "), balance: " + balance;
    }

    @Override
    public String toString() {
        return getAccountSummary();
    }
}
