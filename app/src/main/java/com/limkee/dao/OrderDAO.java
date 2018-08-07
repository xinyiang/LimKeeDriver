package com.limkee.dao;

import com.limkee.entity.Order;
import com.limkee.entity.OrderDetails;
import com.limkee.entity.OrderQuantity;

import java.util.ArrayList;
import java.util.Date;

public class OrderDAO {

    public static ArrayList<Order> currentOrdersList = new ArrayList<Order>();
    public static OrderDetails od;
    public static ArrayList<OrderQuantity> oq;

}
