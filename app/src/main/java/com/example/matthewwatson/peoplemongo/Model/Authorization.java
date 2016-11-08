package com.example.matthewwatson.peoplemongo.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Matthew.Watson on 11/7/16.
 */

public class Authorization {

    @SerializedName("grant_type")
    private String grantType;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("access_token")
    private String token;

    @SerializedName(".expires")
    private Date expiration;


    public Authorization(){}

    public Authorization(String grantType, String username, String password) {
        this.grantType = grantType;
        this.username = username;
        this.password = password;
    }


    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
