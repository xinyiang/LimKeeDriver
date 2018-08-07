package com.limkee.order;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.limkee.R;
import com.limkee.entity.Customer;
import com.limkee.entity.Driver;
import com.limkee.entity.Order;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CurrentOrderAdapter extends RecyclerView.Adapter<CurrentOrderAdapter.MyViewHolder> {
    CurrentOrderFragment fragment;
    private ArrayList<Order> currentOrderList;
    private String orderID;
    private Driver driver;
    private String isEnglish;
    public RelativeLayout rel;

    public CurrentOrderAdapter(CurrentOrderFragment fragment, ArrayList<Order> col, Driver driver, String isEnglish) {
        this.fragment = fragment;
        this.currentOrderList = col;
        this.driver = driver;
        this.isEnglish = isEnglish;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_recycler_view, parent, false);

        rel= (RelativeLayout) itemView.findViewById(R.id.view_foreground);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Order order = currentOrderList.get(position);
        holder.bindContent(order);

        rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderID = order.getOrderID();

                Intent intent = new Intent(view.getContext(), CurrentOrderDetailActivity.class);
                intent.putExtra("orderID", orderID);
                intent.putExtra("language", isEnglish);
                intent.putExtra("driver", driver);
                fragment.startActivity(intent);
            }
        });

        holder.orderID.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                orderID = order.getOrderID();

                Intent intent = new Intent(view.getContext(), CurrentOrderDetailActivity.class);
                intent.putExtra("orderID", orderID);
                intent.putExtra("language", isEnglish);
                intent.putExtra("driver", driver);
                fragment.startActivity(intent);

            }
        });

        holder.contactPerson.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                orderID = order.getOrderID();

                Intent intent = new Intent(view.getContext(), CurrentOrderDetailActivity.class);
                intent.putExtra("orderID", orderID);
                intent.putExtra("language", isEnglish);
                intent.putExtra("driver", driver);
                fragment.startActivity(intent);

            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderID, contactPerson;
        public RelativeLayout viewForeground;

        public MyViewHolder(View view) {
            super(view);
            orderID = (TextView) view.findViewById(R.id.orderID);
            contactPerson = (TextView) view.findViewById(R.id.name);
        }

        public void bindContent(Order order) {

            orderID.setText(order.getOrderID());
            contactPerson.setText(order.getContactPerson());

        }
    }

    public void update(ArrayList<Order> orderList){
        currentOrderList = orderList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return currentOrderList.size();

    }
}
