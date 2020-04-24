package com.luyennk.motel.DTOs;

public class UpdatePriceWater {
    private String id;
    private String topLimit;
    private String lowerLimitl;
    private String priceWater;

    public UpdatePriceWater() {
    }

    public UpdatePriceWater(String id,String topLimit, String lowerLimitl, String priceWater) {
        this.id=id;
        this.topLimit = topLimit;
        this.lowerLimitl = lowerLimitl;
        this.priceWater = priceWater;
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

    public String getPriceWater() {
        return priceWater;
    }

    public void setPriceWater(String priceWater) {
        this.priceWater = priceWater;
    }
}
