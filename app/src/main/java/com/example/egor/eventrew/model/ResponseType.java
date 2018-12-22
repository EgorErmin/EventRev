package com.example.egor.eventrew.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ResponseType implements Serializable {
    BAD_SIGNATURE("Неправильная сигнатура URL."),
    EVENT_DOES_NOT_EXISTS("Событие с данным ID не существует."),
    INCORRECT_USER_DATA("Неверный логин или пароль."),
    SUCCESS_AUTHORIZATION("Авторизация прошла успешно."),
    INVALID_EMAIL("Введен некорректный e-mail."),
    INVALID_PASSWORD("Длина пароля от 8 до 33 символов."),
    SUCCESS_REGISTRATION("Регистрация прошла успешно."),
    ACCESS_DENIED("Отказано в доступе."),
    SUCCESS_ADD_EVENT("Событие успешно добавлено."),
    EMAIL_REGISTERED("Пользователь с таким email уже зарегистрирован."),
    NOT_FOUND("404"),
    PAGE_NOT_FOUND("Данная страница не найдена."),
    USER_DOES_NOT_EXISTS("Пользователь с таким ID не существует.");


    private String message;

    private static final Map<String, ResponseType> ENUM_MAP;

    ResponseType (String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    static {
        Map<String, ResponseType> map = new ConcurrentHashMap();
        for (ResponseType instance : ResponseType.values()) {
            map.put(instance.getMessage(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static ResponseType get(String message) {
        return ENUM_MAP.get(message);
    }
}