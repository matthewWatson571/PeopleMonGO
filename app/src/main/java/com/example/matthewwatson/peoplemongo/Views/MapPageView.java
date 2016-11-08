package com.example.matthewwatson.peoplemongo.Views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.matthewwatson.peoplemongo.MainActivity;
import com.example.matthewwatson.peoplemongo.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Matthew.Watson on 11/4/16.
 */

public class MapPageView extends RelativeLayout implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener {

//    @Bind(R.id.myMapView)
//    MapPageView mapPageView;

    @Bind(R.id.googleMapView)
    MapView map;


    public MapPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

        @Override
        protected void onFinishInflate () {
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
        Place currentPlace = null;
        int STREET_LEVEL = 15;
        int BUILDING_LEVEL = 20;
        int zoom = 10;
        LatLng MY_HOUSE = new LatLng(37.8145, -82.8071);


        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MY_HOUSE, 15));
        googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokemon))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(37.8145, -82.8071)));

    }

}
