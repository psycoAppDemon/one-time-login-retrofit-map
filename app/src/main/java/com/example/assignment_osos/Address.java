package com.example.assignment_osos;

import com.google.gson.annotations.SerializedName;

public class Address {
    private final String street, suite, city, zipcode;
    @SerializedName("geo")
    private final LatLng latlng;

    public Address(String street, String suite, String city, String zipcode, LatLng latlng) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.latlng = latlng;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public LatLng getLatlng() {
        return latlng;
    }
}
