package com.limkee.order;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.limkee.R;
import com.limkee.constant.HttpConstant;
import com.limkee.constant.PostData;
import com.limkee.dao.OrderDAO;
import com.limkee.entity.Driver;
import com.limkee.entity.Order;
import com.limkee.navigation.NavigationActivity;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentOrderFragment extends Fragment {

    private CurrentOrderFragment.OnFragmentInteractionListener mListener;
    private CurrentOrderAdapter mAdapter;
    private View view;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private Driver driver;
    private String driverId;
    private String routeNo;
    public static Retrofit retrofit;
    private String isEnglish;
    TextView lbl_noOrders;

    public CurrentOrderFragment(){}

    public static CurrentOrderFragment newInstance() {
        CurrentOrderFragment fragment = new CurrentOrderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        driver = bundle.getParcelable("driver");
        driverId = driver.getDriverID();
        routeNo = Integer.toString(driver.getRouteNo());
        isEnglish = bundle.getString("language");
        if (isEnglish.equals("Yes")){
            ((NavigationActivity)getActivity()).setActionBarTitle("Today's Orders");
        } else {
            ((NavigationActivity)getActivity()).setActionBarTitle("今日订单");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_current_order, container, false);
        TextView lbl_orderIDHeader, lbl_numItemsHeader, lbl_deliveryDateHeader, lbl_noOrders;

        lbl_orderIDHeader = (TextView) view.findViewById(R.id.lbl_orderIDHeader);
        lbl_deliveryDateHeader = (TextView) view.findViewById(R.id.lbl_deliveryDateHeader);
        lbl_numItemsHeader = (TextView) view.findViewById(R.id.lbl_numItemsHeader);

        if (isEnglish.equals("Yes")) {
            lbl_orderIDHeader.setText("Order ID");
            lbl_deliveryDateHeader.setText("Delivery Date");
            lbl_numItemsHeader.setText("Total No.");
        }

        recyclerView = view.findViewById(R.id.currentOrderRecyclerView);
        recyclerView = (RecyclerView) view.findViewById(R.id.currentOrderRecyclerView);
        mAdapter = new CurrentOrderAdapter(this, OrderDAO.currentOrdersList, driver, isEnglish);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        doGetCurrentOrders(routeNo);

        return view;
    }

    private void doGetCurrentOrders(String routeNo) {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(HttpConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        PostData service = retrofit.create(PostData.class);
        Call<ArrayList<Order>> call = service.getCurrentOrders(routeNo);
        call.enqueue(new Callback<ArrayList<Order>>() {

            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                ArrayList<Order> data = response.body();
                OrderDAO.currentOrdersList = data;
                mAdapter.update(OrderDAO.currentOrdersList);

                if (data.size() == 0) {
                    if (isEnglish.equals("Yes")) {
                        lbl_noOrders = view.findViewById(R.id.lbl_noOrders);
                        view.findViewById(R.id.lbl_noOrders).setVisibility(View.VISIBLE);
                        lbl_noOrders.setText("No current orders");
                    } else {
                        lbl_noOrders = view.findViewById(R.id.lbl_noOrders);
                        lbl_noOrders.setText("没有当前订单");
                        view.findViewById(R.id.lbl_noOrders).setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationActivity) {
            mListener = (CurrentOrderFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
