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
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    private GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private LocationRequest mLocationRequest;
    private LatLng latLng;
    private Context context;
    private ArrayList<User> peopleMon;
    private ArrayList<User> caughtPeoplemon;
    private String caughtName;

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

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this.context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
        createLocationRequest();

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException s) {
            Toast.makeText(context, R.string.connection_failed, Toast.LENGTH_LONG).show();
        }

        latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()); //NULL POINTER EXCEPTION

        mMap.addMarker(new MarkerOptions().position(latLng).title(getContext().getString(R.string.me))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokemon)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));

        final RestClient restClient = new RestClient();
        restClient.getApiService().checkIn(latLng).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) { //checks for 200-299
                    Toast.makeText(context, R.string.check_in, Toast.LENGTH_SHORT).show();
                    checkForNearby();

                } else {
                    Toast.makeText(context, R.string.check_in_failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Toast.makeText(context, R.string.check_in_failed, Toast.LENGTH_SHORT).show();
            }
        });
        mMap.clear();
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        RestClient restClient = new RestClient();
        restClient.getApiService().catchemAll(marker.getTitle(), Constants.radiusInMeters).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(context, "You caught a wild " + marker.getId(), Toast.LENGTH_SHORT).show();
                caughtUsers();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, marker.getTitle() + " ran away!", Toast.LENGTH_SHORT).show();

            }
        });

        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//      .position(new LatLng(37.8145, -82.8071)));

        mMap = googleMap;
        mMap.setPadding(12, 12, 12, 12);

        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            Toast.makeText(context, R.string.location_failed, Toast.LENGTH_SHORT).show();
        }

        mMap.clear();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(this);

    }

    public void checkForNearby() {
        final RestClient restClient = new RestClient();
        restClient.getApiService().nearBy(Constants.radiusInMeters).enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, R.string.nearby_users, Toast.LENGTH_SHORT).show();

                    peopleMon = new ArrayList<>(Arrays.asList(response.body()));

                    for (final User user : peopleMon) {
                        LatLng latLng = new LatLng(user.getLatitude(), user.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(user.getUserName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.peoplemon)));
                    }

                } else {
                    Toast.makeText(context, R.string.no_users, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(context, R.string.find_user_failed, Toast.LENGTH_SHORT).show();

            }
        });
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

//        try {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//
//        }catch (SecurityException s){
//            Toast.makeText(context, "shit didn't work", Toast.LENGTH_SHORT).show();
//        }

    }

    public void caughtUsers() {
        final RestClient restclient = new RestClient();
        restclient.getApiService().caught().enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                for (final User user : caughtPeoplemon) {
                    caughtPeoplemon.add(user);
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(context, R.string.list_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

}











