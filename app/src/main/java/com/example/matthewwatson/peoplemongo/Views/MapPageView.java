package com.example.matthewwatson.peoplemongo.Views;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.matthewwatson.peoplemongo.Componets.Constants;
import com.example.matthewwatson.peoplemongo.MainActivity;
import com.example.matthewwatson.peoplemongo.Model.User;
import com.example.matthewwatson.peoplemongo.Network.RestClient;
import com.example.matthewwatson.peoplemongo.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Matthew.Watson on 11/4/16.
 */

public class MapPageView extends RelativeLayout implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Context context;
    private ArrayList<User> peopleMon;
    private ArrayList<User> caughtPeoplemon;

    @Bind(R.id.googleMapView)
    MapView map;


    public MapPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        map.onCreate(((MainActivity) getContext()).savedInstanceState);
        map.onResume();
        map.getMapAsync(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        int BUILDING_LEVEL = 20;
//        LatLng MY_HOUSE = new LatLng(37.8145, -82.8071);
//
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MY_HOUSE, BUILDING_LEVEL));
//        googleMap.addMarker(new MarkerOptions()
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokemon))
//                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
//                .position(new LatLng(37.8145, -82.8071)));

        mMap = googleMap;
        mMap.setPadding(12, 12, 12, 12);
        mMap.getUiSettings().isMyLocationButtonEnabled();
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setOnMyLocationChangeListener(myLocationChangeListener);

        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {

        }

        mMap.clear();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));

            final RestClient restClient = new RestClient();
            restClient.getApiService().checkIn(latLng).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) { //checks for 200-299
                        Toast.makeText(context, "Check in", Toast.LENGTH_SHORT).show();
                        checkForNearby();

                    } else {
                        Toast.makeText(context, "Failed to Check in.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                    Toast.makeText(context, "Failed to Check in.", Toast.LENGTH_SHORT).show();
                }
            });
            mMap.clear();
        }
    };

    public void checkForNearby() {
        RestClient restClient = new RestClient();
        restClient.getApiService().nearBy(Constants.radiusInMeters).enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "There are users nearby!", Toast.LENGTH_SHORT).show();
                    peopleMon = new ArrayList<>(Arrays.asList(response.body()));
                    caughtPeoplemon = new ArrayList<>();
                    for (User user : peopleMon) {
                        String name = user.getUserName();
                        LatLng latLng = new LatLng(user.getLatitude(), user.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).snippet(name));
                        
                    }

                } else {
                    Toast.makeText(context, "There's nobody here!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(context, "Failed to Find Users", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void catchPeopleMon() {
        final User caughtUser = new User();
        RestClient restClient = new RestClient();
        restClient.getApiService().catchUser(caughtUser).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "You caught something!", Toast.LENGTH_SHORT).show();


                    }else{
                        Toast.makeText(context, R.string.run_away, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure (Call < User > call, Throwable t){
                    Toast.makeText(context, R.string.run_away, Toast.LENGTH_SHORT).show();
                }
            }

            );
        }


    }

