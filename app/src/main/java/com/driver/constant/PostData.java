package com.driver.constant;

import java.util.ArrayList;
import com.driver.entity.Order;
import com.driver.entity.OrderDetails;
import com.driver.entity.OrderQuantity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PostData {

        @GET("get-order/todayorderbyroute")
        Call<ArrayList<Order>> getCurrentOrders(@Query("routeNo") String routeNo);

        @GET("get-order/currentorderdetails")
        Call<OrderDetails> getCurrentOrderDetails(@Query("orderNo") String orderNo);

        @GET("get-order/currentorderquantity")
        Call<ArrayList<OrderQuantity>> getCurrentOrderQuantity(@Query("orderNo") String orderNo);

}
