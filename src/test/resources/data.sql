INSERT INTO person(name, age) values ('Vladimir Putin', 71);
INSERT INTO person(name, age) values ('Vladimir Zhirinovskii', 76);
INSERT INTO person(name, age) values ('Alla Pugachova', 74);
INSERT INTO person(name, age) values ('Ivan', 34);

UPDATE books SET personId = 2 WHERE books.bookId=2;

INSERT INTO books(title, author, yearProduction, personId) values ('Quiet Don', 'Michail Sholohov', 1925, 1);
INSERT INTO books(title, author, yearProduction) values ('Taras Bulba', 'Nikolai Gogol', 1835);
INSERT INTO books(title, author, yearProduction) values ('Anna Karenina', 'Lev Tolstoy', 1873);

INSERT INTO passport(personId, number, address) VALUES (2, 1234, 'Moscow');
INSERT INTO passport(personId, number, address) VALUES (3, 5678, 'Krasnodar');