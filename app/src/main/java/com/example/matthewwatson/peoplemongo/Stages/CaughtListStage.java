package com.example.matthewwatson.peoplemongo.Stages;

import android.app.Application;

import com.davidstemmer.screenplay.stage.Stage;
import com.example.matthewwatson.peoplemongo.PeoplemonApplication;
import com.example.matthewwatson.peoplemongo.R;
import com.example.matthewwatson.peoplemongo.Riggers.SlideRigger;

/**
 * Created by Matthew.Watson on 11/10/16.
 */

public class CaughtListStage extends IndexedStage {
    private final SlideRigger rigger;

    public CaughtListStage(Application context){
        super(CaughtListStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    @Override
    public int getLayoutId() {
        return R.layout.caught_list_view;
    }

    @Override
    public Stage.Rigger getRigger() {
        return rigger;
    }

    public CaughtListStage(){
        this(PeoplemonApplication.getInstance());
    }
}
