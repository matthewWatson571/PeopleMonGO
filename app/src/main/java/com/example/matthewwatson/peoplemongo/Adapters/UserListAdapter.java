package com.example.matthewwatson.peoplemongo.Adapters;

import android.content.Context;

import com.example.matthewwatson.peoplemongo.Model.User;

import java.util.ArrayList;

/**
 * Created by Matthew.Watson on 11/9/16.
 */

public class UserListAdapter {
    private Context context;
    private String userName;
    public ArrayList<User> caughtUsers;

    public UserListAdapter(Context context, String userName, ArrayList<User> caughtUsers) {
        this.context = context;
        this.userName = userName;
        this.caughtUsers = caughtUsers;
    }
}
