# Лабораторна робота №5

## Функціональність програми

Програма імітує спрощену банківську систему. 
Це включає створення рахунків, фінансові операції, та функції зведення рахунків.
Також програма має обробляти різні типи помилок.

## Вимоги

1. [x] Реалізуйте клас `BankAccount` з членами класу `accountNumber`, `accountName` і `balance`. 
2. [x] Реалізуйте методи `deposit(double amount)`, `withdraw(double amount)`, `getBalance()` та `getAccountSummary()`. 
3. [ ] Створіть спеціалізовані класи винятків:
    - [ ] `InsufficientFundsException` 
    - [ ] `NegativeAmountException` 
    - [ ] `AccountNotFoundException`
4. [ ] Реалізуйте клас Bank, який зберігає колекцію об'єктів `BankAccount`. 
5. [ ] У класі `Bank`, реалізуйте методи:
    - [ ] `createAccount(String accountName, double initialDeposit)`
    - [ ] `findAccount(int accountNumber)`
    - [ ] `transferMoney(int fromAccountNumber, int toAccountNumber, double amount)`
6. [ ] Обробляйте винятки відповідно в кожному методі. 
7. [ ] Створіть тестові класи, де ви моделюєте різні сценарії для тестування обробки виняткових ситуацій.