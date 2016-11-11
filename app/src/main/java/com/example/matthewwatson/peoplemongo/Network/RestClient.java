package com.example.matthewwatson.peoplemongo.Network;

import com.example.matthewwatson.peoplemongo.PeoplemonApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Matthew.Watson on 11/4/16.
 */

public class RestClient {//binds network files together
    private ApiService apiService;

    public RestClient() {
        GsonBuilder builder = new GsonBuilder(); //converting string to date format
        builder.setDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
        Gson gson = builder.create();

        HttpLoggingInterceptor log = new HttpLoggingInterceptor(); //print to log, body of requested response
        log.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder() //connects to server
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new SessionRequestInterceptor())
                .addInterceptor(log)
                .build();

        Retrofit restAdapter = new Retrofit.Builder() //setting up urls
                .baseUrl(PeoplemonApplication.API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)) //convert json to classes
                .build();

        apiService = restAdapter.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
