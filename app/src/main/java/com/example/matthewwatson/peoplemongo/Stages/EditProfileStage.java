package com.example.matthewwatson.peoplemongo.Stages;

import android.app.Application;

import com.example.matthewwatson.peoplemongo.PeoplemonApplication;
import com.example.matthewwatson.peoplemongo.R;
import com.example.matthewwatson.peoplemongo.Riggers.SlideRigger;

/**
 * Created by Matthew.Watson on 11/8/16.
 */

public class EditProfileStage extends IndexedStage {
    private final SlideRigger rigger; //sets Rigger

    @Override
    public int getLayoutId() {
        return R.layout.edit_profile;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

    public EditProfileStage(Application context){
        super(MapStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public EditProfileStage(){
        this(PeoplemonApplication.getInstance());
    }

}


