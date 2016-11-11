package com.example.matthewwatson.peoplemongo.Stages;

import android.app.Application;

import com.example.matthewwatson.peoplemongo.PeoplemonApplication;
import com.example.matthewwatson.peoplemongo.R;
import com.example.matthewwatson.peoplemongo.Riggers.SlideRigger;

/**
 * Created by Matthew.Watson on 11/11/16.
 */

public class NearbyStage extends IndexedStage {
    private final SlideRigger rigger; //sets Rigger

    @Override
    public int getLayoutId() {
        return R.layout.nearby_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

    public NearbyStage(Application context) {
        super(NearbyStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public NearbyStage() {
        this(PeoplemonApplication.getInstance());
    }
}