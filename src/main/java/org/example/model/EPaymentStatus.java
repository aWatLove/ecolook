package org.example.model;

public enum EPaymentStatus {
    PAYMENT_YES("Оплачено"),
    PAYMENT_NO("Не оплачено");

    private String title;

    public String getTitle() {
        return title;
    }

    EPaymentStatus(String title) {
        this.title = title;
    }
}
