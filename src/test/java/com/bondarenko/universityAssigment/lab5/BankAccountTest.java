package com.bondarenko.universityAssigment.lab5;
import com.bondarenko.universityAssigment.lab5.exceptions.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    BankAccount bankAccount;
    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount(1, "Test account", 100);
    }

    @Test
    void Deposit_PositiveAmount_ShouldIncreaseAccountBalance() {
        var depositAmount = 50;
        var balanceBeforeDeposit = bankAccount.getBalance();

        bankAccount.deposit(depositAmount);

        assertEquals(depositAmount, bankAccount.getBalance() - balanceBeforeDeposit);
    }

    @Test
    void Deposit_NegativeAmount_ShouldThrowNegativeAmountException() {
        var negativeAmount = -50;


        assertThrows(NegativeAmountException.class, () -> bankAccount.deposit(negativeAmount));
    }

    @Test
    void Withdraw_PositiveAmount_ShouldIncreaseAccountBalance() {
        var withdrawAmount = 50;
        var balanceBeforeWithdraw = bankAccount.getBalance();

        bankAccount.withdraw(withdrawAmount);

        assertEquals(balanceBeforeWithdraw - withdrawAmount, bankAccount.getBalance());
    }

    @Test
    void Withdraw_NegativeAmount_ShouldThrowNegativeAmountException() {
        var negativeAmount = -50;


        assertThrows(NegativeAmountException.class, () -> bankAccount.withdraw(negativeAmount));
    }

    @Test
    void Withdraw_AmountBiggerThanBalance_ShouldThrowInsufficientFundsException() {
        var bigAmount = 99999999;


        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(bigAmount));
    }
}