CREATE TABLE Users (
	user_id smallint primary key auto_increment,
    username varchar(255),
    email varchar(255),
    password text
);

CREATE TABLE BookGenres (
	genre_id smallint primary key auto_increment,
    genre_name varchar(255)
);

CREATE TABLE Authors (
	author_id smallint primary key auto_increment,
    name varchar(255)
);

CREATE TABLE Books (
	book_id smallint primary key auto_increment,
    title varchar(255),
    author smallint,
    published_date Date,
    genre smallint,
    description text,
    cover_image_url text,
    FOREIGN KEY (genre) REFERENCES BookGenres(genre_id),
    FOREIGN KEY (author) REFERENCES Authors(author_id)
);

CREATE TABLE UserBooks (
	user_id smallint,
    book_id smallint,
    read_status varchar(25),
    rating smallint,
    date_read Date,
	FOREIGN KEY (user_id) REFERENCES Users(user_id),
	FOREIGN KEY (book_id) REFERENCES Books(book_id)
);

CREATE TABLE Comments (
	comment_id smallint primary key auto_increment,
    user_id smallint,
    book_id smallint,
    comment_text text,
    date_posted Date,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
	FOREIGN KEY (book_id) REFERENCES Books(book_id)
);

CREATE TABLE ReadBooks (
	user_id smallint,
    book_id smallint,
    read_status varchar(25),
    rating smallint,
    date_read Date,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
	FOREIGN KEY (book_id) REFERENCES Books(book_id)
);



