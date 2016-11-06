package com.example.matthewwatson.peoplemongo;

import android.app.Application;

import com.example.matthewwatson.peoplemongo.Stages.MapStage;

import flow.Flow;
import flow.History;

/**
 * Created by Matthew.Watson on 11/5/16.
 */
public class PeoplemonApplication extends Application {
    private static PeoplemonApplication application;
    public final Flow mainFlow = new Flow(History.single(new MapStage()));//history is stack information

    public static final String API_BASE_URL =
            "https://efa-peoplemon-api.azurewebsites.net:443/";

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    public static PeoplemonApplication getInstance() {
        return application;
    }

    public static Flow getMainFlow() {
        return getInstance().mainFlow;
    }
}
