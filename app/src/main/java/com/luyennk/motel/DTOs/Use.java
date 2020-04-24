package com.luyennk.motel.DTOs;

public class Use {
    private String idUse;
    private String fullName;
    private String passWord;
    private String nameUse;
    private String address;
    private String idCard;
    private String phoneNumber;
    private String job;
    private String mail;
    private String permission;

    public Use() {
    }

    public Use(String idUse, String fullName, String passWord, String nameUse, String address, String idCard, String phoneNumber, String job, String mail, String permission) {
        this.idUse = idUse;
        this.fullName=fullName;
        this.passWord = passWord;
        this.nameUse = nameUse;
        this.address = address;
        this.idCard = idCard;
        this.phoneNumber = phoneNumber;
        this.job = job;
        this.mail = mail;
        this.permission = permission;
    }

    public String getId() {
        return idUse;
    }

    public void setId(String idUse) {
        this.idUse = idUse;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNameUse() {
        return nameUse;
    }

    public void setNameUse(String nameUse) {
        this.nameUse = nameUse;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
