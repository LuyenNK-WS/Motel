package com.luyennk.motel.DTOs;

import java.util.List;

public class BillDetail {
    private String idBill;
    private List<Service> idService;
    private int numberElectricLast;
    private int numberWaterLast;
    private int numberElectricNow;
    private int numberWaterNow;
    private double total;
    private double pay;
    private double liabilities;

    public BillDetail() {
    }

    public BillDetail(String idBill, List<Service> idService, int numberElectricLast, int numberWaterLast,int numberElectricNow, int numberWaterNow, double total, double pay, double liabilities) {
        this.idBill = idBill;
        this.idService = idService;
        this.numberElectricLast = numberElectricLast;
        this.numberWaterLast = numberWaterLast;
        this.numberElectricNow = numberElectricNow;
        this.numberWaterNow = numberWaterNow;
        this.total = total;
        this.pay = pay;
        this.liabilities = liabilities;
    }

    public List<Service> getIdService() {
        return idService;
    }

    public void setIdService(List<Service> idService) {
        this.idService = idService;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public int getNumberElectricLast() {
        return numberElectricLast;
    }

    public void setNumberElectricLast(int numberElectricLast) {
        this.numberElectricLast = numberElectricLast;
    }

    public int getNumberWaterLast() {
        return numberWaterLast;
    }

    public void setNumberWaterLast(int numberWaterLast) {
        this.numberWaterLast = numberWaterLast;
    }

    public int getNumberElectricNow() {
        return numberElectricNow;
    }

    public void setNumberElectricNow(int numberElectricNow) {
        this.numberElectricNow = numberElectricNow;
    }

    public int getNumberWaterNow() {
        return numberWaterNow;
    }

    public void setNumberWaterNow(int numberWaterNow) {
        this.numberWaterNow = numberWaterNow;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public double getLiabilities() {
        return liabilities;
    }

    public void setLiabilities(double liabilities) {
        this.liabilities = liabilities;
    }
}
