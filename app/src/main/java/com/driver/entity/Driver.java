package com.driver.entity;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("serial")
public class Driver implements Parcelable  {
    private String driverID;
    private String password;
    private String contact;
    private String driverName;
    private String status;
    private int routeNo;

    public Driver(String driverID, String password,String contact, String driverName, String status, int routeNo) {
        this.driverID = driverID;
        this.password = password;
        this.contact = contact;
        this.driverName = driverName;
        this.status = status;
        this.routeNo = routeNo;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String deliveryContact) { this.status = status; }

    public int getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(int routeNo) {
        this.routeNo = routeNo;
    }

    public Driver(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<com.driver.entity.Driver> CREATOR = new Parcelable.Creator<com.driver.entity.Driver>() {
        public com.driver.entity.Driver createFromParcel(Parcel in) {
            return new com.driver.entity.Driver(in);
        }

        public com.driver.entity.Driver[] newArray(int size) {
            return new com.driver.entity.Driver[size];
        }

    };

    public void readFromParcel(Parcel in) {
        driverID = in.readString();
        password = in.readString();
        contact = in.readString();
        driverName = in.readString();
        status = in.readString();
        routeNo = in.readInt();
    }
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(driverID);
        dest.writeString(password);
        dest.writeString(contact);
        dest.writeString(driverName);
        dest.writeString(status);
        dest.writeInt(routeNo);
    }
}
