package com.example.matthewwatson.peoplemongo.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;
import com.example.matthewwatson.peoplemongo.PeoplemonApplication;
import com.example.matthewwatson.peoplemongo.R;
import com.example.matthewwatson.peoplemongo.Riggers.SlideRigger;

/**
 * Created by Matthew.Watson on 11/4/16.
 */

public class RegisterStage extends IndexedStage {
    private final SlideRigger rigger;

    public RegisterStage(Application context) {
        super(RegisterStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.register_view;
    }

    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }

    public RegisterStage() {
        this(PeoplemonApplication.getInstance());
    }
}
