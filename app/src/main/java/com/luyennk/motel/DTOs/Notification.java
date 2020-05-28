package com.luyennk.motel.DTOs;

public class Notification {
    private String date;
    private String notification;

    public Notification() {
    }

    public Notification(String date, String notification) {
        this.date = date;
        this.notification = notification;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
