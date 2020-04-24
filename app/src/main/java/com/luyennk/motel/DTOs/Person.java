package com.luyennk.motel.DTOs;

public class Person {
    private String fullName;
    private String idCard;
    private String phoneNumber;
    private String job;

    public Person() {
    }

    public Person(String fullName, String idCard, String phoneNumber, String job) {
        this.fullName = fullName;
        this.idCard = idCard;
        this.phoneNumber = phoneNumber;
        this.job = job;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
