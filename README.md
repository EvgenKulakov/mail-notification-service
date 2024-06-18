# Mail notification service

## Старт приложения:
#### Kafka-server and postgreSQL
```
docker-compose up -d 
```
---

#### Start CoreApplication: (properties для Yandex cloud)
```
cloud.aws.credentials.access-key=<access-key>
cloud.aws.credentials.secret-key=<secret-key>
yandex.bucket.name=<bucket-name>
```
---
#### Start MailApplication (properties для работы email)
```
spring.mail.username=<username>
spring.mail.password=<password>
```
---
## Endpoints (basic auth in Postman):
#### Новый пользователь (общий доступ)
```
post localhost:7070/api/register
body:
{
    "username": "username",
    "password": "password",
    "role": "ROLE_MODERATOR",
    "email": "email@email.com"
}
```
---
#### Добавить картинку (USER, MODERATOR)
```
post localhost:7070/api/upload
```
---
#### Получить информацию о своих файлах (USER, MODERATOR)
```
get localhost:7070/api/images?sortBy=uploadDate&size=266&uploadDate=2024-06-18
params: sortBy, size, uploadDate
```
---
#### Скачать файл (USER, MODERATOR)
```
get localhost:7070/api/download/{image-name-in-cloud.jpg}
```
---
#### Получить информацию о всех файлах (MODERATOR)
```
get localhost:7070/api/moderator/images?sortBy=uploadDate&size=266&uploadDate=2024-06-18
params: sortBy, size, uploadDate
```
---
#### Заблокировать аккаунт (MODERATOR)
```
patch localhost:7070/api/moderator/block/{username}
```
---
#### Разблокировать аккаунт (MODERATOR)
```
patch localhost:7070/api/moderator/unblock/{username}
```
---