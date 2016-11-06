package com.example.matthewwatson.peoplemongo.Network;

import com.example.matthewwatson.peoplemongo.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Matthew.Watson on 11/4/16.
 */

public interface ApiService {

    @POST("auth")
    Call<User> login(@Body User user);

    @POST("register")
    Call<User> register(@Body User user);


}
