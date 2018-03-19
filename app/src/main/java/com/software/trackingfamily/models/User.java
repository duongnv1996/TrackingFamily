package com.software.trackingfamily.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DuongKK on 3/18/2018.
 */

public class User {

    @SerializedName("user_id")
    private String user_id;
    @SerializedName("phone")
    private String phone;
    @SerializedName("username")
    private String username;
    @SerializedName("last_lng")
    private String last_lng;
    @SerializedName("last_lat")
    private String last_lat;
    @SerializedName("family_id")
    private String family_id;
    @SerializedName("password")
    private String password;
    @SerializedName("email")
    private String email;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLast_lng() {
        return last_lng;
    }

    public void setLast_lng(String last_lng) {
        this.last_lng = last_lng;
    }

    public String getLast_lat() {
        return last_lat;
    }

    public void setLast_lat(String last_lat) {
        this.last_lat = last_lat;
    }

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
