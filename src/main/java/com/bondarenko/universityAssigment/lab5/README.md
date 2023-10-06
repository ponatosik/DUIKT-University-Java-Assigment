# Лабораторна робота №5

## Функціональність програми

Програма імітує спрощену банківську систему. 
Це включає створення рахунків, фінансові операції, та функції зведення рахунків.
Також програма має обробляти різні типи помилок.

## Вимоги

1. [x] Реалізуйте клас `BankAccount` з членами класу `accountNumber`, `accountName` і `balance`. 
2. [x] Реалізуйте методи `deposit(double amount)`, `withdraw(double amount)`, `getBalance()` та `getAccountSummary()`. 
3. [x] Створіть спеціалізовані класи винятків:
    - [x] `InsufficientFundsException` 
    - [x] `NegativeAmountException` 
    - [x] `AccountNotFoundException`
4. [x] Реалізуйте клас `Bank`, який зберігає колекцію об'єктів `BankAccount`. 
5. [x] У класі `Bank`, реалізуйте методи:
    - [x] `createAccount(String accountName, double initialDeposit)`
    - [x] `findAccount(int accountNumber)`
    - [x] `transferMoney(int fromAccountNumber, int toAccountNumber, double amount)`
6. [x] Обробляйте винятки відповідно в кожному методі. 
7. [ ] Створіть тестові класи, де ви моделюєте різні сценарії для тестування обробки виняткових ситуацій.