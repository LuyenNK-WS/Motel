package com.luyennk.motel.DTOs;

import java.util.List;

public class Contract {

    private String idContract;
    private String idUse;
    private String nameUse;
    private List<Person> personList;
    private String idRoom;
    private List<Service> services;
    private String dateCheckIn;
    private String dateCheckOut;
    private String priceRoom;

    public Contract() {
    }

    public Contract(String idContract, String idUse, String nameUse,List<Person> personList, String idRoom, List<Service> services,String dateCheckIn, String dateCheckOut,String priceRoom) {
        this.idContract = idContract;
        this.idUse = idUse;
        this.nameUse = nameUse;
        this.personList=personList;
        this.idRoom = idRoom;
        this.services=services;
        this.dateCheckIn = dateCheckIn;
        this.dateCheckOut = dateCheckOut;
        this.priceRoom=priceRoom;
    }

    public String getPriceRoom() {
        return priceRoom;
    }

    public void setPriceRoom(String priceRoom) {
        this.priceRoom = priceRoom;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getIdContract() {
        return idContract;
    }

    public void setIdContract(String idContract) {
        this.idContract = idContract;
    }

    public String getIdUse() {
        return idUse;
    }

    public void setIdUse(String idUse) {
        this.idUse = idUse;
    }

    public String getNameUse() {
        return nameUse;
    }

    public void setNameUse(String nameUse) {
        this.nameUse = nameUse;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String idRoom) {
        this.idRoom = idRoom;
    }

    public String getDateCheckIn() {
        return dateCheckIn;
    }

    public void setDateCheckIn(String dateCheckIn) {
        this.dateCheckIn = dateCheckIn;
    }

    public String getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(String dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
    }
}
