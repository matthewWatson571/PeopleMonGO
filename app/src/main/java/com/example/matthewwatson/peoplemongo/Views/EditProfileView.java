package com.example.matthewwatson.peoplemongo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.matthewwatson.peoplemongo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Matthew.Watson on 11/7/16.
 */

public class EditProfileView extends LinearLayout {


    @Bind(R.id.edit_user_name)
    EditText editUserName;

    @Bind(R.id.edit_profile_imageView)
    ImageButton editProfileImageView;

    @Bind(R.id.save_button)
    ImageView saveButton;

    public EditProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() { //inflates our container
        super.onFinishInflate();
        ButterKnife.bind(this);
    }


    @OnClick(R.id.edit_profile_imageView)
    public void getImage() {

    }
}
