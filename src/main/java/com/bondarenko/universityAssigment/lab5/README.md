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
7. [x] Створіть тестові класи, де ви моделюєте різні сценарії для тестування обробки виняткових ситуацій.

# Опис роботи

1. Реалізовано клас [BankAccount.java](BankAccount.java) з полями `accountNumber`, `accountName` і `balance` та методами доступу до них. 
   Розроблено методи для виконання дій над балансом `deposit(double amount)`, `withdraw(double amount)`
2. Реалізовано клас [Bank.java](Bank.java) для створення акаунтів, та проведення банківських операцій
3. Розроблені помилки що показують, що операція не пройшла успішно.
   - [AccountNotFoundException.java](exceptions/AccountNotFoundException.java) - означає що вказаний акаунт не існує.
   - [InsufficientFundsException.java](exceptions/InsufficientFundsException.java) - означає ща на балансі недостатньо коштів для проведення операції.
   - [NegativeAmountException.java](exceptions/NegativeAmountException.java) - означає що операція не підтримує від'ємні значення.
