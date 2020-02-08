package com.endpoint.giveme.models;

import java.io.Serializable;

public class BillModel implements Serializable {

    private String bill_image;
    private String bill_cost;
    private String driver_offer;

    public BillModel(String bill_image, String bill_cost, String driver_offer) {
        this.bill_image = bill_image;
        this.bill_cost = bill_cost;
        this.driver_offer = driver_offer;
    }

    public String getBill_image() {
        return bill_image;
    }

    public String getBill_cost() {
        return bill_cost;
    }

    public String getDelivery_cost() {
        return driver_offer;
    }
}
