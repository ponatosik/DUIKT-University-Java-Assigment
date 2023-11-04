# Лабораторна робота №, 7

## Функціональність програми

Спрощена система бек-енду для платформи електронної комерції, використовуючи `Java Collections`.
Функції управлінні запасами, управлінні кошиком користувача та обробці замовлень.

## Технічне завдання
1. [x] Спроєктувати класи:
    - [x] `Product` (Товар): Поля `id`, `name`, `price` ,`stock`, та відповідні конструктори та гетери.
    - [x] `User` (Користувач): Поля `id`, `cart` та відповідні конструктори та гетери.
    - [x] `Order` (Замовлення): Поля `id`, `userId`, `orderDetails` та відповідні конструктори та гетери.
2. [x] Реалізація платформи електронної комерції:
    - [x] Розробити `ECommercePlatform` (Платформа Електронної Комерції): Поля `users`, `products`, `orders`,
      та методи для додавання користувачів, товарів, створення замовлення, переліку доступних товарів, 
      переліку користувачів, переліку замовлень та оновлення запасів товарів.
    - [x] Розробити `ECommerceDemo` - клас для демонстрації функціональності.
3. [x] Розширені Функції:
    - [x] Сортування та фільтрація : Реалізувати `Comparable` у класі `Product`, та клас `Comparator` для сортування Товарів.
    - [x] Рекомендації для Користувача: Реалізувати функцію для рекомендації товарів користувачам.

## Опис роботи

1. Розроблено класи, що представляють предметну область: [Product](Product.java), [User](User.java), [Order](Order.java)
2. Розроблено клас, що емулює платформу для здійснення онлайн-покупок: [EcommercePlatform](EcommercePlatform.java)
3. Розроблено винятки, що можуть виникати при використанні сервісу: [ECommersPlatformException](exceptions/ECommersPlatformException.java),
   [ECommersObjectRegistrationException](exceptions/ECommersObjectRegistrationException.java), [ECommersObjectRetrievingException](exceptions/ECommersObjectRetrievingException.java)
4. Розроблено клас для демонстрації функціональності програми: [ECommerceDemo](ECommerceDemo.java)
5. Розроблено модульні тести для тестування функціональності: [EcommercePlatformTest](../../../../../../test/java/com/bondarenko/universityAssigment/lab7/EcommercePlatformTest.java)