package com.example.matthewwatson.peoplemongo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.matthewwatson.peoplemongo.Model.User;
import com.example.matthewwatson.peoplemongo.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Matthew.Watson on 11/11/16.
 */

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.NearbyHolder> {
    private Context context;
    public ArrayList<User> nearbyUsers;


    public NearbyAdapter(ArrayList<User> nearbyUsers, Context context) {
        this.nearbyUsers = nearbyUsers;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(NearbyHolder holder, int position) {
        User user = nearbyUsers.get(position);
        holder.bindUser(user);

    }

    @Override
    public int getItemCount() {

        return nearbyUsers == null ? 0 : nearbyUsers.size();
    }

    @Override
    public NearbyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(context)
                .inflate(R.layout.nearby_item, parent, false);
        return new NearbyHolder(inflatedView);
    }

    class NearbyHolder extends RecyclerView.ViewHolder { //populates view

        @Bind(R.id.nearby_user_name_field)
        TextView nearbyNameField;


        public NearbyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindUser(User user) {

            nearbyNameField.setText(user.getUserName());
        }
    }

}