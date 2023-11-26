# Лабораторна робота №9

## Функціональність програми

Програма для збереження даних обраного API в форматі Excel.


## Технічне завдання

1. [x] Використовуючи зазначене API https://fakeapi.platzi.com/en/rest/products/ отримати дані зі всіх ендпоїнтів. 
2. [x] Зберегти отримані дані у форматі Excel. 
3. [x] Покрити код jUnit/Mockito тестами.


## Опис роботи

### 1. Опис основних моделей

- Роблено клас [Category](entities/Category.java) з полям: `id`, `name`, `image`.
- Роблено клас [User](entities/User.java) з полями: `id`, `email`, `password`, `name`, `role`, `avatar`.
- Роблено клас [Product](entities/Product.java) з полям: `id`, `title`, `price`, `description`, `image`, `category`.

### 2. Взаємодія з PlatziFakeStoreAPI

- Розроблено клас [PlatziFakeStoreAPI](fakeStoreAPI/PlatziFakeStoreAPI.java) для відправки GET запитів до API:
  `getCategories`, `getUsers`, `getProducts`.
- Розроблено клас [PlatziFakeStoreJSONMapper](fakeStoreAPI/PlatziFakeStoreJSONMapper.java) для парсингу JSON файлів.
  Цей клас використовує бібліотеку `Gson` для парсингу JSON.

### 3. Збереження даних в Excel таблицю

- Імпортовано бібліотеку `Apache POI` для створення Excel таблиць.
- Розроблено клас [ExcelFileFormatter](excelFormating/ExcelFileFormatter.java) для легкого створення та збереження
  Excel файлів
- Розроблено клас [ExcelSheetFormatter](excelFormating/ExcelSheetFormatter.java) для створення однотипних Excel сторінок
  з узагальнених класів.

### 4. Тестування

- Програма тестує парсинг JSON файлів в класі для тестування
  [PlatziFakeStoreJSONMapperTest](../../../../../../test/java/com/bondarenko/universityAssigment/lab9/fakeStoreAPI/PlatziFakeStoreJSONMapperTest.java)


