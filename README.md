# Менеджер новостей

## Описание проекта
Сервис для управления порталом новостей

## Стэк используемых технологий
* Java
* Maven
* MySQL

## Инструкция
### Конфигурация проекта 
[Пример файла](src/main/resources/application.yaml)
#### Основные настройки
* spring.datasource.username - Пользователь базы данных
* spring.datasource.password - Пароль базы данных
* spring.datasource.url - Адрес сервера базы данных

Остальные параметры можно оставить без изменений

### Сборка и запуск приложения
После того как приложение было сконфигурировано, его можно запустить.
Для запуска проекта нужно запустить MYSQL через docker, для этого подготовлен docker-compose.yaml,
запускаем docker-compose.yaml:
```cmd
docker-compose up -d
```
затем запускаем само приложение локально
