package com.luyennk.motel.DTOs;

public class UpdatePriceElectric {

    private String id;
    private String topLimit;
    private String lowerLimitl;
    private String priceElectric;

    public UpdatePriceElectric() {
    }

    public UpdatePriceElectric(String id,String topLimit, String lowerLimitl, String priceElectric) {
        this.id=id;
        this.topLimit = topLimit;
        this.lowerLimitl = lowerLimitl;
        this.priceElectric = priceElectric;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopLimit() {
        return topLimit;
    }

    public void setTopLimit(String topLimit) {
        this.topLimit = topLimit;
    }

    public String getLowerLimitl() {
        return lowerLimitl;
    }

    public void setLowerLimitl(String lowerLimitl) {
        this.lowerLimitl = lowerLimitl;
    }

    public String getPriceElectric() {
        return priceElectric;
    }

    public void setPriceElectric(String priceElectric) {
        this.priceElectric = priceElectric;
    }
}
