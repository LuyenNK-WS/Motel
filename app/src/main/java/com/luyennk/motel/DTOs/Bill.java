package com.luyennk.motel.DTOs;

public class Bill {

    private String idBill;
    private String idRoom;
    private String billDate;

    public Bill() {
    }

    public Bill(String idBill, String idRoom, String billDate) {
        this.idBill = idBill;
        this.idRoom = idRoom;
        this.billDate = billDate;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

}
