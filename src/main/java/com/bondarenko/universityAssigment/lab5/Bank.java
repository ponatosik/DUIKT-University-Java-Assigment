package com.bondarenko.universityAssigment.lab5;

import com.bondarenko.universityAssigment.lab5.exceptions.AccountNotFoundException;
import com.bondarenko.universityAssigment.lab5.exceptions.InsufficientFundsException;
import com.bondarenko.universityAssigment.lab5.exceptions.NegativeAmountException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Bank {
    private final List<BankAccount> accountList;
    private int accountIdCounter = 0;

    public Bank(List<BankAccount> accountList) {
        this.accountList = new ArrayList<>(accountList);
    }

    public Bank() {
        this.accountList = new ArrayList<>();
    }

    public BankAccount createAccount(String accountName, double initialDeposit) {
        BankAccount newAccount = new BankAccount(accountIdCounter++, accountName, initialDeposit);
        accountList.add(newAccount);
        return newAccount;
    }

    public Optional<BankAccount> findAccount(int accountNumber) {
        return accountList.stream().filter(account -> account.getAccountNumber() == accountNumber).findAny();
    }

    public void transferMoney(int fromAccountNumber, int toAccountNumber, double amount)
            throws AccountNotFoundException, InsufficientFundsException, NegativeAmountException {

        if (amount < 0) {
            throw new NegativeAmountException("Transfer amount cannot be negative");
        }

        BankAccount from = findAccount(fromAccountNumber).orElseThrow(() ->
                new AccountNotFoundException("Account with number " + fromAccountNumber + " not found"));

        BankAccount to = findAccount(toAccountNumber).orElseThrow(() ->
                new AccountNotFoundException("Account with number " + toAccountNumber + " not found"));

        if (from.getBalance() < amount) {
            throw new InsufficientFundsException("Account with number " + fromAccountNumber +
                    " has not enough funds to transfer " + amount);
        }

        from.withdraw(amount);
        to.deposit(amount);
    }
}
