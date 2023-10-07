package com.bondarenko.universityAssigment.lab5;

import com.bondarenko.universityAssigment.lab5.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    Bank bank;
    BankAccount bankAccount;
    BankAccount otherBankAccount;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bankAccount = bank.createAccount("Test account", 100);
        otherBankAccount = bank.createAccount("Test account2", 100);
    }

    @Test
    void FindAccount_ExistingAccount_ShouldReturnAccount() {
        var accountNumber = bankAccount.getAccountNumber();

        var actual = bank.findAccount(accountNumber);

        assertTrue(actual.isPresent());
    }

    @Test
    void TransferMoney_NegativeAmount_ShouldThrowNegativeAmountException() {
        var fromAccountNumber = bankAccount.getAccountNumber();
        var toAccountNumber = otherBankAccount.getAccountNumber();
        var amount = -1;


        assertThrows(NegativeAmountException.class, () ->
                bank.transferMoney(fromAccountNumber, toAccountNumber, amount));
    }

    @Test
    void TransferMoney_AmountBiggerThanBalance_ShouldThrowInsufficientFundsException() {
        var fromAccountNumber = bankAccount.getAccountNumber();
        var toAccountNumber = otherBankAccount.getAccountNumber();
        var amount = bankAccount.getBalance() + 1;


        assertThrows(InsufficientFundsException.class, () ->
                bank.transferMoney(fromAccountNumber, toAccountNumber, amount));
    }

    @Test
    void TransferMoney_NotExistingAccount_ShouldThrowAccountNotFoundException() {
        var notExistingAccountNumber1 = -99999;
        var notExistingAccountNumber2 = -99998;
        var amount = 1;


        assertThrows(AccountNotFoundException.class, () ->
                bank.transferMoney(notExistingAccountNumber1, notExistingAccountNumber2, amount));
    }
}