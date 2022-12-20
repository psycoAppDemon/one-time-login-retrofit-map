package com.example.assignment_osos;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Retrofit_Interface {
    @GET("users")
    Call<ArrayList<Users>>  getUsers();
}
