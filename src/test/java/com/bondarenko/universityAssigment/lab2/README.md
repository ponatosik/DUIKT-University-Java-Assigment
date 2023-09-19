# Модульні тести до [лабораторної роботи №2](../../../../../../main/java/com/bondarenko/universityAssigment/lab2/README.md)

### Опис тестів:
- `Add_ValidBook_ShouldChangeSize` - Для перевірки, що додавання книги в бібліотеку змінює її розмір.
- `GetBooks_NotEmptyLibrary_ShouldReturnBooks` - Для перевірки, що метод getBook насправді повертає книги в середині бібліотеки.
- `FindByName_ExistingBook_ShouldReturnBook` - Для перевірки, що пошук книги, що знаходиться в бібліотеці, повертає цю книгу.
- `FindByName_NotExistingBook_ShouldReturnEmptyOptional` - Для перевірки, що пошук книги, що не знаходиться в бібліотеці, значення, що означає, що такої книги немає.
- `RemoveByIsbn_ExistingBook_ShouldReturnTrue` - Для перевірки, що видалення книги, що знаходиться в бібліотеці, повертає значення, що означає, що книга успішно видалена.
- `RemoveByIsbn_NotExistingBook_ShouldReturnFalse` - Для перевірки, що видалення книги, що не знаходиться в бібліотеці, повертає значення, що означає, що операція видалення не виконана.
- `LendItem_ValidPatronAndItem_ShouldMakeItemBorrowed` - Для перевірки, що метод `lendItem` видає предмет патрону та помічає її як позичену.
- `LendItem_UnregisteredPatron_ShouldThrowUnknownPatronException` - Для перевірки, що метод `lendItem` викидає виняток `UnknownPatronException`, якщо патрон не зареєстрований.
- `LendItem_BorrowedItem_ShouldThrowItemCannotBeBorrowedException` - Для перевірки що метод `lendItem` викидає виняток, якщо книга вже позичена.
- `ReturnItem_ValidPatronAndItem_ShouldMakeItemNotBorrowed` - Для перевірки, що метод `returnItem` повертає книгу та встановлює її як непозичену.
- `ListAvailable_ValidPatronAndItem_ShouldReturnNotBorrowedItems` - Для перевірки, що метод `listAvailable` повертає правильний список доступних предметів.
- `ListBorrowed_ValidPatronAndItem_ShouldReturnBorrowedItems` - Для перевірки, що метод `listBorrowed` повертає правильний список позичених предметів.
- `GetItemBorrower_BorrowedItem_ReturnsPatronThatBorrowedItem` - Для перевірки, що метод `getItemBorrower`  повертає правильного клієнта, який позичив книгу.