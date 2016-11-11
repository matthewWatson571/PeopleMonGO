package com.example.matthewwatson.peoplemongo.Views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.matthewwatson.peoplemongo.Adapters.CaughtListAdapter;
import com.example.matthewwatson.peoplemongo.Model.User;
import com.example.matthewwatson.peoplemongo.Network.RestClient;
import com.example.matthewwatson.peoplemongo.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Matthew.Watson on 11/8/16.
 */

public class CaughtListView extends RelativeLayout {
    private Context context;
    private CaughtListAdapter peopleCaughtAdapter;

    @Bind(R.id.caught_recycler)
    RecyclerView recyclerView;

    public CaughtListView(Context context,AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        peopleCaughtAdapter = new CaughtListAdapter(new ArrayList<User>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(peopleCaughtAdapter);
        listCaughtPeople();
    }
    private void listCaughtPeople(){
        RestClient restClient = new RestClient();
        restClient.getApiService().caught().enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                // Is the server response between 200 to 299
                if (response.isSuccessful()){
                    peopleCaughtAdapter.caughtUsers = new ArrayList<>(Arrays.asList(response.body()));
                    for (User user  : peopleCaughtAdapter.caughtUsers) {
                        peopleCaughtAdapter.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText(context,"Get User Info Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(context,"Get User Info Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}