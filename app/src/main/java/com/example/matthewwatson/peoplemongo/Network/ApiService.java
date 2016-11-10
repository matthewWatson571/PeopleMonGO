package com.example.matthewwatson.peoplemongo.Network;

import com.example.matthewwatson.peoplemongo.Model.Account;
import com.example.matthewwatson.peoplemongo.Model.Authorization;
import com.example.matthewwatson.peoplemongo.Model.User;
import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Matthew.Watson on 11/4/16.
 */

public interface ApiService {

    @GET("/api/Account/UserInfo")
    Call<Account> userInfo(@Body Account account);

    @POST("/api/Account/Register")
    Call<Void> register(@Body Account account);

    @POST("/v1/User/CheckIn")
    Call<Void> checkIn(@Body LatLng latLng);

    @GET("/v1/User/Caught")
    Call<User[]> caught();

    @GET("/v1/User/Nearby")
    Call<User[]> nearBy(@Query("radiusInMeters") Integer radiusInMeters);

    @FormUrlEncoded
    @POST("v1/User/Catch")
    Call <Void> catchemAll(@Field("CaughtUserId") String userId,
                           @Field("RadiusInMeters") Integer radius);

    @FormUrlEncoded
    @POST("token")
    Call<Authorization> login(@Field("grant_type") String grantType,
                              @Field("username") String email,
                              @Field("password") String password);

}
