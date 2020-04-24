package com.luyennk.motel.DTOs;

public class Service {

    private String idService;
    private String nameService;
    private String priceService;

    public Service() {
    }

    public Service(String idService, String nameService, String priceService) {
        this.idService = idService;
        this.nameService = nameService;
        this.priceService = priceService;
    }

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public String getPriceService() {
        return priceService;
    }

    public void setPriceService(String priceService) {
        this.priceService = priceService;
    }
}
