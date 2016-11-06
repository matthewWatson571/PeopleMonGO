package com.example.matthewwatson.peoplemongo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.davidstemmer.flow.plugin.screenplay.ScreenplayDispatcher;
import com.example.matthewwatson.peoplemongo.Network.UserStore;
import com.example.matthewwatson.peoplemongo.Stages.LoginStage;
import com.example.matthewwatson.peoplemongo.Stages.MapStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.History;

public class MainActivity extends AppCompatActivity {
    private Flow flow;
    private ScreenplayDispatcher dispatcher;

    @Bind(R.id.container)
    RelativeLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this); //NOTHING WORKS WITHOUT THIS LINE

        flow = PeoplemonApplication.getMainFlow();
        dispatcher = new ScreenplayDispatcher(this, container); //controls UI, container is the views
        dispatcher.setUp(flow);//sets up based on what's in flow

//        testCalls();
//        UserStore.getInstance().setToken(null);

        if (UserStore.getInstance().getToken() == null || UserStore.getInstance().getTokenExpiration() == null) {
            History newHistory = History.single(new LoginStage());
            flow.setHistory(newHistory, Flow.Direction.REPLACE); //no visual transition
        }

    }

    @Override
    public void onBackPressed() { //overrides back button
        if (!flow.goBack()) {
            flow.removeDispatcher(dispatcher);
            flow.setHistory(History.single(new MapStage()), Flow.Direction.BACKWARD);
            super.onBackPressed();
        }
    }

}
