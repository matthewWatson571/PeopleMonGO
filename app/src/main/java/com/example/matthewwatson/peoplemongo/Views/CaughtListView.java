package com.example.matthewwatson.peoplemongo.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.matthewwatson.peoplemongo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Matthew.Watson on 11/8/16.
 */

public class CaughtListView extends RecyclerView {
    private Context context;

    @Bind(R.id.user_name_field)
    TextView userNameField;

    public CaughtListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        context = this.context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }
}
