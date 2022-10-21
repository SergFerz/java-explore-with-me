# java-explore-with-me
- - -
Фича - подписка на друзей

Подписка на друзей и возможность получать список актуальных событий, в которых они принимают участие.

Описание API находится в Docs/rest_api/subscription_api.yaml (Файл необходимо загрузить в https://editor-next.swagger.io)

Весь код к фиче находится в main-service пакет ru.practicum.subscription
- - - 
### Описание explore-with-me.

Приложение состоит из трех модулей: 
1. main-service - главный сервис 
2. statatistic-service - сервис статистики 
3. expl-me-common - модуль с общим функционалом

Запуск осуществляется в 4 докерах:

+ главный сервис (main-service слушает порт 8080)
+ сервис статистики (statatistic-service порт 9090)
+ база данных главного сервиса
+ база данных сервиса статистики

Запуск осуществляется командами:

mvn clean package

docker-compose up