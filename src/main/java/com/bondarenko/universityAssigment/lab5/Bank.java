package com.bondarenko.universityAssigment.lab5;

import com.bondarenko.universityAssigment.lab5.exceptions.AccountNotFoundException;

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
    public void transferMoney(int fromAccountNumber, int toAccountNumber, double amount) {
        BankAccount from = findAccount(fromAccountNumber).orElseThrow(() ->
                new AccountNotFoundException("Account with number " + fromAccountNumber + " not found"));

        BankAccount to = findAccount(toAccountNumber).orElseThrow(() ->
                new AccountNotFoundException("Account with number " + toAccountNumber + " not found"));

        from.withdraw(amount);
        to.deposit(amount);
    }
}
