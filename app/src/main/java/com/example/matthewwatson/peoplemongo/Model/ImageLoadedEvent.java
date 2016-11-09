package com.example.matthewwatson.peoplemongo.Model;

/**
 * Created by Matthew.Watson on 11/8/16.
 */

public class ImageLoadedEvent {

    public String selectedImage;

    public ImageLoadedEvent(String selectedImage) {
        this.selectedImage = selectedImage;
    }

    public String getSelectedImage() {
        return selectedImage;
    }
}
