-- Роботу виконав студент групи ПД-31 Бондаренко Олександр.
-- Роботу виконано в MySQL.

-- Створення таблиць

CREATE TABLE Animals (
	animal_id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(255),
	species VARCHAR(255),
	date_of_birth DATE,
	habitat VARCHAR(255)
);

CREATE TABLE Caretakers (
	caretaker_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    specialization VARCHAR(255)
);

CREATE TABLE FeedSchedules (
	feed_id INT PRIMARY KEY AUTO_INCREMENT,
  	animal_id INT,
  	caretaker_id INT,
  	feed_time DATETIME,
  	feed_type VARCHAR(255),
  	FOREIGN KEY (animal_id) REFERENCES Animals(animal_id),
  	FOREIGN KEY (caretaker_id) REFERENCES Caretakers(caretaker_id)
);

CREATE TABLE Visitors (
  	visitor_id INT PRIMARY KEY AUTO_INCREMENT,
  	name VARCHAR(255),
  	visit_date DATE
);

CREATE TABLE Visits (
  visit_id INT PRIMARY KEY AUTO_INCREMENT,
  visitor_id INT,
  animal_id INT,
  FOREIGN KEY (visitor_id) REFERENCES Visitors(visitor_id),
  FOREIGN KEY (animal_id) REFERENCES Animals(animal_id)
);

-- Вставка даних в таблиці (дані згенеровано за допомогою ChatGPT)

INSERT INTO Animals (name, species, date_of_birth, habitat)
VALUES
	('Leo', 'Lion', '2015-03-10', 'Grassland'),
	('Ellie', 'Elephant', '2010-07-22', 'Savannah'),
	('Gigi', 'Giraffe', '2018-01-05', 'Woodland'),
	('Pippin', 'Penguin', '2017-06-15', 'Antarctic'),
	('Kip', 'Kangaroo', '2016-11-30', 'Bushland'),
	('Dolly', 'Dolphin', '2014-04-12', 'Ocean'),
	('Tasha', 'Lion', '2013-09-18', 'Jungle'),
	('Pao Pao', 'Panda', '2019-02-25', 'Bamboo Forest'),
	('Crunch', 'Crocodile', '2012-08-07', 'Swamp'),
	('Koda', 'Koala', '2015-12-14', 'Eucalyptus Forest');

INSERT INTO Caretakers (name, specialization)
VALUES
	('John Smith', 'Mammals'),
	('Alice Johnson', 'Avian'),
	('David Rodriguez', 'Reptiles'),
	('Emily White', 'Marine Animals'),
	('Brian Taylor', 'Primates'),
	('Olivia Brown', 'Amphibians'),
	('Daniel Lee', 'Insects'),
	('Sophia Martinez', 'Big Cats'),
	('Michael Davis', 'Aquatic Animals'),
	('Emma Harris', 'Endangered Species');

INSERT INTO FeedSchedules (animal_id, caretaker_id,  feed_time, feed_type)
VALUES 
	(1, 1, '0000-00-00 08:00', 'Meat'), 
	(2, 2, '0000-00-00 09:30', 'Hay'),
	(3, 3, '0000-00-00 10:45', 'Leaves'),
	(4, 4, '0000-00-00 11:15', 'Fish'),
	(5, 5, '0000-00-00 12:00', 'Grass'),
	(6, 6, '0000-00-00 13:30', 'Squid'),
	(7, 7, '0000-00-00 14:45', 'Meat'),
	(8, 8, '0000-00-00 15:30', 'Bamboo'),
	(9, 9, '0000-00-00 16:15', 'Fish'),
	(10, 1, '0000-00-00 17:00', 'Leaves');

INSERT INTO Visitors (name, visit_date)
VALUES
	('Alex Johnson', '2023-01-05'),
	('Sarah Miller', '2023-02-12'),
	('Robert Davis', '2023-03-20'),
	('Jessica White', '2023-04-08'),
	('Christopher Taylor', '2023-05-15'),
	('Emily Brown', '2023-06-22'),
	('Michael Harris', '2023-07-10'),
	('Olivia Lee', '2023-09-25'),
	('Daniel Smith', '2023-09-25'),
	('Sophia Martinez', '2023-10-03');


INSERT INTO Visits (visitor_id, animal_id)
VALUES
	(1, 1), 
	(2, 2), 
	(3, 3), 
	(4, 4), 
	(5, 5), 
	(6, 6), 
	(7, 7), 
	(8, 8), 
	(9, 9), 
	(10, 10);

-- Виконання запитів 

-- a. Отримати список всіх тварин та їх відповідних доглядачів.
SELECT DISTINCT a.name as 'animal name', c.name as 'caretaker' FROM Animals a 
INNER JOIN FeedSchedules fs ON (a.animal_id = fs.animal_id)
INNER JOIN Caretakers c ON (fs.caretaker_id = c.caretaker_id);


-- b. Визначити, який вид тварин найчастіше відвідується відвідувачами.
SELECT a.species as 'most visited specie' FROM Animals a
INNER JOIN Visits v ON (a.animal_id = v.animal_id)
GROUP BY (a.species)
ORDER BY COUNT(a.species) DESC
LIMIT 1;

-- c. Визначити, який доглядач має найбільш різноманітну спеціалізацію, тобто він доглядає за найбільшою кількістю видів.
SELECT c.name as 'caretaker', c.specialization as 'specialization', GROUP_CONCAT(DISTINCT  a.species) as 'species' FROM Caretakers c
INNER JOIN FeedSchedules fs ON (fs.caretaker_id = c.caretaker_id)
INNER JOIN Animals a ON (a.animal_id = fs.animal_id)
GROUP BY c.caretaker_id 
ORDER BY COUNT(DISTINCT a.species) DESC
LIMIT 1;

-- d. Виявити дні, коли зоопарк відвідали більше ніж 10 відвідувачів.
SELECT visit_date as 'Days with more that 10 visitors' FROM Visitors
GROUP BY visit_date
HAVING COUNT(visitor_id) > 10;

-- e. Створити звіт про графік годівлі, який включає ім'я тварини, ім'я доглядача, час годівлі та тип корму, відсортовані за часом годівлі.
SELECT DISTINCT a.name as 'animal name', c.name as 'caretaker', DATE_FORMAT(fs.feed_time ,'%H:%i') as 'feed time', fs.feed_type as 'food' FROM  FeedSchedules fs
INNER JOIN Animals a ON (a.animal_id = fs.animal_id)
INNER JOIN Caretakers c ON (fs.caretaker_id = c.caretaker_id);



