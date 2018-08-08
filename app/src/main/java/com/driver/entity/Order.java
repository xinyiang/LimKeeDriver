package com.driver.entity;

public class Order {
    private String orderID;
    private String deliveryDate;
    private int noOfItems;
    private String deliveryShift;
    private String customer;

    public Order() {

    }

    public Order(String orderID, String customer) {
        this.orderID = orderID;
        this.customer = customer;
    }

    public Order(String orderID, String deliveryDate, int noOfItems, String deliveryShift) {
        this.orderID = orderID;
        this.deliveryDate = deliveryDate;
        this.noOfItems = noOfItems;
        this.deliveryShift = deliveryShift;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(int noOfItems) {
        this.noOfItems = noOfItems;
    }

    public String getDeliveryShift() {
        return deliveryShift;
    }

    public void setDeliveryShift(String deliveryShift) {
        this.deliveryShift = deliveryShift;
    }

    public String getContactPerson() {
        return customer;
    }

    public void setContactPerson(String customer) {
        this.customer = customer;
    }

}