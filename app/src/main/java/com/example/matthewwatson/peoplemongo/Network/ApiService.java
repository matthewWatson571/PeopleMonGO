package com.example.matthewwatson.peoplemongo.Network;

import com.example.matthewwatson.peoplemongo.Model.Account;
import com.example.matthewwatson.peoplemongo.Model.Authorization;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Matthew.Watson on 11/4/16.
 */

public interface ApiService {

    @GET("/api/Account/UserInfo")
    Call<Account> userInfo (@Body Account account);

    @POST("/api/Account/Register")
    Call<Void> register(@Body Account account);



    @FormUrlEncoded
    @POST("token")
    Call<Authorization> login(@Field("grant_type") String grantType,
                              @Field("username") String email,
                              @Field("password") String password);

}
