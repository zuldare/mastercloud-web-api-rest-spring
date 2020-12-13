INSERT INTO books.books(id, title, author, summary, publishing_house, publish_year) VALUES
(1, 'UML Distilled','Martin Fowler', 'Learn how to use UML', 'Addison', 2003),
(2, 'Learn design patterns','Erich Gamma', 'Learn design patterns', 'Addison', 2002);

INSERT INTO books.users(id, nick, email) VALUES
(1,'Jaime', 'jaime@google.com'),
(2,'JohnDoe', 'johnDoe@unknown.com');

INSERT INTO books.comments(id, commentary, score, book_id, user_id) VALUES
(1, 'Es un libro indispensable', 5, 1,1),
(2, 'Es demasiado denso', 2, 1,2),
(3, 'Muy buen libro', 4, 2, 1);



