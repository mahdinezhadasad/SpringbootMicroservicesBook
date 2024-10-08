
CREATE TABLE IF NOT EXISTS book
(
                                     id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255),
    isbn VARCHAR(13),
    author VARCHAR(255),
    genre VARCHAR(50),
    quantity_on_hand INT
    );

INSERT INTO book(id, title, isbn, author, genre, quantity_on_hand)
VALUES
    ('b01e30bc-5c3e-4e9d-8e91-dabb9b0f69d9', '1984', '9780451524935', 'George Orwell', 'FICTION', 100),
    ('9fa73244-36ee-4ed4-9a45-9393ab138726', 'Brave New World', '9780060850524', 'Aldous Huxley', 'FICTION', 75),
    ('93df4be2-c6ad-4e2c-bbae-4acbc41b6278', 'Sapiens: A Brief History of Humankind', '9780062316097', 'Yuval Noah Harari', 'HISTORY', 50),
    ('c872c8e2-d97c-4b76-9a1b-07e5d343f5d3', 'The Selfish Gene', '9780199291151', 'Richard Dawkins', 'SCIENCE', 60),
    ('1d5e2f67-5171-49e7-a2b6-9316f5d96345', 'To Kill a Mockingbird', '9780060935467', 'Harper Lee', 'FICTION', 120);