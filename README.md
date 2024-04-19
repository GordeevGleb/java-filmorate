# java-filmorate
Simple REST application, written on Java using Spring Boot.
Allows the user to add, retrieve and delete users and films from database using POST, PUT, GET and DELETE requests.

## Database structure


![Database structure in the form of an ER diagram](filmorate.png)

## Реализованные эндпоинты
1. ***Режиссёр***   
   `GET /directors`  Список всех режиссёров  

   `GET /directors/{id}`  Получение режиссёра по id  

   `POST /directors`  Создание режиссёра  

   `PUT /directors`  Изменение режиссёра  
   `DELETE /directors/{id}` - Удаление режиссёра  
2. ***Вывод самых популярных фильмов по жанру и годам***   
   `GET /films/popular?count={limit}&genreId={genreId}&year={year}`  
   Возвращает список самых популярных фильмов указанного жанра за нужный год.
3. ***Лента событий***  
   `GET /users/{id}/feed`   Возвращает ленту событий пользователя.
4. ***Удаление фильмов и пользователей***  
      `DELETE /users/{userId}`Удаляет пользователя по идентификатору.

    `DELETE /films/{filmId}`
Удаляет фильм по идентификатору.
5. ***Рекомендации***  
   `GET /users/{id}/recommendations`
Возвращает рекомендации по фильмам для просмотра.
6. ***Поиск***  
   `GET /fimls/search`
Возвращает список фильмов, отсортированных по популярности.
Параметры строки запроса
```query``` — текст для поиска
```by``` — может принимать значения director (поиск по режиссёру), title (поиск по названию),
либо оба значения через запятую при поиске одновременно и по режиссеру и по названию.
7. ***Отзывы***  
    `POST /reviews`
Добавление нового отзыва.

    `PUT /reviews`
Редактирование уже имеющегося отзыва.

    `DELETE /reviews/{id}`
Удаление уже имеющегося отзыва.

    `GET /reviews/{id}`
Получение отзыва по идентификатору.

    `GET /reviews?filmId={filmId}&count={count}`
Получение всех отзывов по идентификатору фильма, если фильм не указан то все. Если кол-во не указано то 10.

    `PUT /reviews/{id}/like/{userId}` — пользователь ставит лайк отзыву.  
    `PUT /reviews/{id}/dislike/{userId}` — пользователь ставит дизлайк отзыву.  
    `DELETE /reviews/{id}/like/{userId}` — пользователь удаляет лайк/дизлайк отзыву.  
    `DELETE /reviews/{id}/dislike/{userId}` — пользователь удаляет дизлайк отзыву.
8. ***Общие фильмы***  
   `GET /films/common?userId={userId}&friendId={friendId}`
   Возвращает список фильмов, отсортированных по популярности; 
```userId``` — идентификатор пользователя, запрашивающего информацию;
```friendId``` — идентификатор пользователя, с которым необходимо сравнить список фильмов.

The image can be changed on the website https://dbdiagram.io.
Code to change the schema:

```
Table users {
  id bigint [primary key]
  email varchar [not null]
  login varchar [not null]
  name varchar
  birthdate date
}

Table friend {
  user_id bigint [primary key]
  friend_id bigint [primary key]
}

Ref: users.id < friend.user_id

Ref: users.id < friend.friend_id


Table film {
  id bigint [primary key]
  name varchar [not null]
  description varchar 
  release_date date
  duration integer
  rating_id integer
  director_id bigint
}

Table film_likes {
  user_id bigint [primary key]
  film_id bigint [primary key]
}

Ref: users.id < film_likes.user_id
Ref: film.id < film_likes.film_id

Table genre {
  id integer [primary key]
  name varchar [not null]
}

Table film_genre {
  id bigint [primary key]
  film_id bigint
  genre_id integer
}

Ref: genre.id < film_genre.genre_id
Ref: film.id < film_genre.film_id

Table rating {
  id integer [primary key]
  name varchar [not null]
}

Ref: rating.id < film.rating_id

Table director {
  id bigint [primary key]
  name varchar [not null]
}

Table film_director {
  id bigint [primary key]
  film_id bigint
  director_id bigint
}

Ref: director.id < film_director.director_id
Ref: film.id < film_director.film_id

Table event_type {
  id bigint [primary key]
  name varchar [not null, unique]
}

Table operation {
  id bigint [primary key]
  name varchar [not null, unique]
}

Table feed {
  event_id bigint [primary key]
  timestamp bigint [not null]
  user_id bigint
  event_type int
  operation int
  entity_id bigint [not null]
}

Ref: feed.user_id < users.id
Ref: feed.event_type < event_type.id
Ref: feed.operation < operation.id
```

