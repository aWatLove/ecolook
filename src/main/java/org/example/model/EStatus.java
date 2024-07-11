package org.example.model;

public enum EStatus {
    IN_PROGRESS("Обрабатывается"),
    ON_THE_WAY("В пути"),
    DELIVERED("Доставлено"),
    CANCELED("Отменено"),
    ERROR("Ошибка");

    private String title;

    public String getTitle() {
        return title;
    }

    EStatus(String title) {
        this.title = title;
    }
}
