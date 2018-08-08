package com.driver.dao;

import com.driver.entity.Order;
import com.driver.entity.OrderDetails;
import com.driver.entity.OrderQuantity;

import java.util.ArrayList;

public class OrderDAO {

    public static ArrayList<Order> currentOrdersList = new ArrayList<Order>();
    public static OrderDetails od;
    public static ArrayList<OrderQuantity> oq;

}
