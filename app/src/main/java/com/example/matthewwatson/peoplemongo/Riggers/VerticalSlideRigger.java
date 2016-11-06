package com.example.matthewwatson.peoplemongo.Riggers;

import android.app.Application;

import com.davidstemmer.screenplay.stage.rigger.AnimResources;
import com.davidstemmer.screenplay.stage.rigger.TweenRigger;
import com.example.matthewwatson.peoplemongo.R;

/**
 * Created by Matthew.Watson on 11/4/16.
 */

public class VerticalSlideRigger extends TweenRigger {
    private static final AnimResources params = new AnimResources();

    static {
        params.forwardIn = R.anim.slide_in_up;
        params.backIn = R.anim.slide_in_down;
        params.backOut = R.anim.slide_out_down;
        params.forwardOut = R.anim.slide_out_up;
    }

    public VerticalSlideRigger(Application context) {
        super(context, params);
    }
}
