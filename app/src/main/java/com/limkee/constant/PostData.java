package com.limkee.constant;

import java.util.ArrayList;
import com.limkee.entity.Order;
import com.limkee.entity.OrderDetails;
import com.limkee.entity.OrderQuantity;
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
