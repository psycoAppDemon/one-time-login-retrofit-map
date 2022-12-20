package com.example.assignment_osos;

import com.google.gson.annotations.SerializedName;

public class Users {
    private String name,username,email;
    private  int id;
    private int color = -1;
    @SerializedName("address")
    private Address address;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
