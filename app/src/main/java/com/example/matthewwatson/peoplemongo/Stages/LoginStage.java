package com.example.matthewwatson.peoplemongo.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;
import com.example.matthewwatson.peoplemongo.PeoplemonApplication;
import com.example.matthewwatson.peoplemongo.R;
import com.example.matthewwatson.peoplemongo.Riggers.SlideRigger;

/**
 * Created by Matthew.Watson on 11/4/16.
 */

public class LoginStage extends IndexedStage {
    private final SlideRigger rigger;

    public LoginStage(Application context) {
        super(LoginStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.login_view;
    }

    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }

    public LoginStage() {
        this(PeoplemonApplication.getInstance());
    }
}
