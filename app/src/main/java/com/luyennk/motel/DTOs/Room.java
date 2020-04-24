package com.luyennk.motel.DTOs;

public class Room {

    private String idRoom;
    private String acreage;
    private String status;
    private String priceRoom;

    public Room() {
    }

    public Room(String idRoom, String acreage, String status, String priceRoom) {
        this.idRoom = idRoom;
        this.acreage = acreage;
        this.status = status;
        this.priceRoom = priceRoom;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public String getAcreage() {
        return acreage;
    }

    public void setAcreage(String acreage) {
        this.acreage = acreage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriceRoom() {
        return priceRoom;
    }

    public void setPriceRoom(String priceRoom) {
        this.priceRoom = priceRoom;
    }
}
