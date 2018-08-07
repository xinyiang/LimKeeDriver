package com.limkee.userProfile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.limkee.R;
import com.limkee.entity.Customer;
import com.limkee.entity.Driver;
import com.limkee.login.LogoutActivity;
import com.limkee.navigation.NavigationActivity;
import java.util.ArrayList;
import io.reactivex.disposables.CompositeDisposable;

public class UserProfileFragment extends Fragment {
    private UserProfileFragment.OnFragmentInteractionListener mListener;
    CompositeDisposable compositeDisposable;
    private View view;
    private Driver driver;
    private String isEnglish;

    public UserProfileFragment(){}

    public static UserProfileFragment newInstance() {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((NavigationActivity)getActivity()).setActionBarTitle("用户资料");
        compositeDisposable = new CompositeDisposable();

        Bundle bundle = getArguments();
        driver = bundle.getParcelable("driver");
        isEnglish = bundle.getString("language");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        Button logout = view.findViewById(R.id.logout_btn);
        TextView name, phone, companyCode;

        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        companyCode = view.findViewById(R.id.companyCode);

        if (isEnglish.equals("Yes")){
            TextView lbl_name, lbl_phone, lbl_address;
            //change label
            lbl_name = view.findViewById(R.id.lbl_name);
            lbl_phone = view.findViewById(R.id.lbl_phone);
            lbl_name.setText("Name");
            lbl_phone.setText("Contact Number");
        }

        name.setText(driver.getDriverName());
        phone.setText(driver.getContact());
        companyCode.setText(driver.getDriverID());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),LogoutActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationActivity) {
            mListener = (UserProfileFragment.OnFragmentInteractionListener) context;
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