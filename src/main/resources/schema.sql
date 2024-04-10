CREATE TABLE IF NOT EXISTS users (
    id bigint generated by default as identity primary key,
    email varchar(256) NOT NULL,
    login varchar(256) NOT NULL,
    name varchar(256),
    birthday date
);

CREATE TABLE IF NOT EXISTS friend (
    user_id bigint REFERENCES users(id) ON DELETE CASCADE,
    friend_id bigint REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT friend_self CHECK (user_id <> friend_id),
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS rating (
    id integer generated by default as identity primary key,
    name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS film (
    id bigint generated by default as identity primary key,
    name varchar(256) NOT NULL,
    description varchar(200),
    release_date date,
    duration integer,
    rating_id integer,
    CONSTRAINT film_duration_min CHECK (duration > 0),
    CONSTRAINT film_date_start CHECK (release_date > '1895-12-28'),
    CONSTRAINT fk_film_rating FOREIGN KEY(rating_id) REFERENCES rating(id)
);

CREATE TABLE IF NOT EXISTS director (
    id bigint generated by default as identity primary key,
    name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS film_director (
    id bigint generated by default as identity primary key,
    film_id bigint,
    director_id bigint,
    CONSTRAINT fk_film_director_film FOREIGN KEY(film_id) REFERENCES film(id) ON DELETE CASCADE,
    CONSTRAINT fk_film_director_director FOREIGN KEY(director_id) REFERENCES director(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS film_likes (
    film_id bigint REFERENCES film(id) ON DELETE CASCADE,
    user_id bigint REFERENCES users(id) ON DELETE CASCADE,
    PRIMARY KEY (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS genre (
    id integer generated by default as identity primary key,
    name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS film_genre (
    id bigint generated by default as identity primary key,
    film_id bigint,
    genre_id integer,
    CONSTRAINT fk_film_genre_film FOREIGN KEY(film_id) REFERENCES film(id) ON DELETE CASCADE,
    CONSTRAINT fk_film_genre_genre FOREIGN KEY(genre_id) REFERENCES genre(id)
);

CREATE TABLE IF NOT EXISTS review (
                                      id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                      content VARCHAR(500) NOT NULL,
    is_positive BOOL NOT NULL,
    user_id bigint NOT NULL,
    film_id bigint NOT NULL,
    useful INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (film_id) REFERENCES film(id) ON DELETE CASCADE
    );