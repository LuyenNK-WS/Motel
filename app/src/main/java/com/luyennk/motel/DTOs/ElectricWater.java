package com.luyennk.motel.DTOs;

public class ElectricWater {
    private String numberElectric;
    private String numberWater;

    public ElectricWater() {
    }

    public ElectricWater(String numberElectric, String numberWater) {
        this.numberElectric = numberElectric;
        this.numberWater = numberWater;
    }

    public String getNumberElectric() {
        return numberElectric;
    }

    public void setNumberElectric(String numberElectric) {
        this.numberElectric = numberElectric;
    }

    public String getNumberWater() {
        return numberWater;
    }

    public void setNumberWater(String numberWater) {
        this.numberWater = numberWater;
    }
}
