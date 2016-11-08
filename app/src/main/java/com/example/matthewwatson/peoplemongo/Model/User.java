package com.example.matthewwatson.peoplemongo.Model;

import com.google.gson.annotations.SerializedName;

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


}

