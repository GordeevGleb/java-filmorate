# java-filmorate
Template repository for Filmorate project.
![Screenshot_25](https://github.com/GordeevGleb/java-filmorate/assets/146061679/9843be43-b093-46dd-9eaa-42a4beec956a)


## [Cсылка на оригинал таблицы](https://dbdiagram.io/d/65e9f26ab1f3d4062c63b323)
### Примеры запросов
<details>
<summary>Cписок всех пользователей по id и логину</summary>
SELECT id,
       login
FROM user;
</details>

<details>
<summary>Получение таблицы id и логина друзей пользователя с id N</summary>
SELECT u.id,
       u.login
FROM user AS u
LEFT JOIN friendship AS f ON u.id = f.friend_id
WHERE f.user_id = N AND f.is_friend = 'true';
 -- TODO  
</details>

<details>
<summary>Таблица с id пользователей, являющихся общими друзьями для юзеров с id=N и id=M</summary>
-- TODO  
</details>

<details>
<summary>Таблица с названиями фильмов, которые понравились пользователю с id N</summary>
SELECT f.title
FROM film AS f
LEFT JOIN like AS l ON f.id = l.film_id
LEFT JOIN user AS u ON l.user_id = u.id
WHERE u.id = N;
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
INNER JOIN genre AS g ON f.genre_id = g.id
WHERE g.name = N;
</details>

<details>
<summary>Список названий всех фильмов рейтинга N</summary>
SELECT f.title
FROM film AS f
INNER JOIN rating AS r ON f.rating_id = r.id
WHERE r.name = N;
</details>

<details>
<summary>Топ 10 самых популярных фильмов</summary>
SELECT f.title,
       COUNT(user_id) AS likes_posted
FROM film AS f
INNER JOIN like AS l ON f.id = l.film_id
ORDER BY likes_posted DESC
LIMIT 10;  
</details>
