# java-filmorate

![Screenshot_27](https://github.com/GordeevGleb/java-filmorate/assets/146061679/23a67825-1ba6-4d25-b647-5dae5e034c3d)



## [Cсылка на оригинал таблицы](https://dbdiagram.io/d/65e9f26ab1f3d4062c63b323)
### Примеры запросов
<details>
<summary>Cписок всех пользователей по id и логину</summary>
       
    SELECT id,
       login
    FROM user;      
</details>

<details>
<summary>Получение таблицы id и логина друзей пользователя с id N; сначала идут подтверждённые друзья пользователя, потом неподтверждённые</summary>
       
    SELECT u.user_id,
       u.login
    FROM user AS u
    LEFT JOIN friendship AS f ON u.user_id = f.friend_id
    WHERE f.user_id = N AND f.is_friend = 'true'
    UNION
    SELECT u.user_id,
       u.login
    FROM user AS u
    LEFT JOIN friendship AS f ON u.user_id = f.friend_id
    WHERE f.user_id = N AND f.is_friend = 'false';         
</details>

<details>
<summary>Таблица с id пользователей, являющихся общими подтверждёнными друзьями для юзеров с id=N и id=M</summary>
       
    SELECT u.user_id,
       u.login
    FROM user AS u
    LEFT JOIN friendship AS f1 ON u.user_id = f1.friend_id
    LEFT JOIN friendship AS f2 ON u.user_id = f2.friend_id
    WHERE (f1.user_id = N AND f1.is_friend = 'true')
       AND (f2.user_id = M and f2.is_friend = 'true');        
</details>

<details>
<summary>Таблица с названиями фильмов, которые понравились пользователю с id N</summary>
       
    SELECT f.title
    FROM film AS f
    LEFT JOIN like AS l ON f.film_id = l.film_id
    LEFT JOIN user AS u ON l.user_id = u.user_id
    WHERE u.user_id = N;
</details>

<details>
<summary>Список названий всех фильмов</summary>
       
    SELECT title
    FROM film;
</details>

<details>
<summary>Список названий всех фильмов жанра N</summary>
       
    SELECT f.title
    FROM film AS f
    INNER JOIN film_genre AS fg ON f.film_id = fg.film_id
    INNER JOIN genre AS g ON fg.genre_id = g.genre_id       
    WHERE g.name = N;
</details>

<details>
<summary>Список названий всех фильмов рейтинга N</summary>
       
    SELECT f.title
    FROM film AS f
    INNER JOIN rating AS r ON f.rating_id = r.rating_id
    WHERE r.name = N;
</details>

<details>
<summary>Топ 10 самых популярных фильмов</summary>
       
    SELECT f.title,
       COUNT(user_id) AS likes_posted
    FROM film AS f
    INNER JOIN like AS l ON f.film_id = l.film_id
    ORDER BY likes_posted DESC
    LIMIT 10;  
</details>
