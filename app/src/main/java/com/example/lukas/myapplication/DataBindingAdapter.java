package com.example.lukas.myapplication;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by lukas on 22.03.2017.
 */
public class DataBindingAdapter {
    @BindingAdapter("android:src")
    public static void setImage(ImageView view, Bitmap image) {
        if(image != null)
            view.setImageBitmap(image);
    }

}
