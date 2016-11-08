package com.example.matthewwatson.peoplemongo.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Matthew.Watson on 11/4/16.
 */

public class User {

    @SerializedName("Email")
    private String email;

    @SerializedName("FullName")
    private String fullName;

    @SerializedName("AvatarBase64")
    private String avatarBase64;

    @SerializedName("ApiKey")
    private String apiKey;

    @SerializedName("Password")
    private String password;




    @SerializedName("token")
    private String token;

    @SerializedName("expiration")
    private Date expiration;

    public User() {
    }

    public User(String fullName, String password) {
        this.fullName = fullName;
        this.password = password;
    }

    public User(String email, String fullName, String avatarBase64, String apiKey, String password) {
        this.email = email;
        this.fullName = fullName;
        this.avatarBase64 = avatarBase64;
        this.apiKey = apiKey;
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAvatarBase64() {
        return avatarBase64;
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

}

