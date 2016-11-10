package com.example.matthewwatson.peoplemongo.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.matthewwatson.peoplemongo.MainActivity;
import com.example.matthewwatson.peoplemongo.Model.ImageLoadedEvent;
import com.example.matthewwatson.peoplemongo.Model.ImageSized;
import com.example.matthewwatson.peoplemongo.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Matthew.Watson on 11/7/16.
 */

public class EditProfileView extends LinearLayout {
    private Context context;

    public String selectedImage;
    public Bitmap scaledImage;

    @Bind(R.id.edit_user_name)
    EditText editUserName;

    @Bind(R.id.edit_profile_imageView)
    ImageView editProfileImageView;

    @Bind(R.id.edit_picture_button)
    Button editPictureButton;

    @Bind(R.id.save_button)
    Button saveButton;

    public EditProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() { //inflates our container
        super.onFinishInflate();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        EventBus.getDefault().unregister(this);
        super.onDetachedFromWindow();
    }

    @OnClick(R.id.edit_picture_button)
    public void pictureTapped() {
        ((MainActivity) context).getImage();
        Toast.makeText(context, "CLICK", Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void imageReady(ImageLoadedEvent event) {
        event.getSelectedImage();
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void imageLoaded(ImageLoadedEvent event) { //waiting for an image loaded event
        selectedImage = event.selectedImage;
        int width = editProfileImageView.getWidth();
        int height = editProfileImageView.getHeight();

        Bitmap image = BitmapFactory.decodeFile(selectedImage);

        float ratio = (float) image.getWidth() / (float) image.getHeight();
        if (ratio > 1) {
            height = (int) (width / ratio);
        } else {
            width = (int) (height * ratio);
        }
        scaledImage = Bitmap.createScaledBitmap(image, width, height, true);

        EventBus.getDefault().post(new ImageSized());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        scaledImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] b = outputStream.toByteArray();


         editProfileImageView.setImageBitmap(scaledImage);
        //Take the bitmap Array and encode it to Base64
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


    }
}






