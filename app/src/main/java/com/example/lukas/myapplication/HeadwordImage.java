package com.example.lukas.myapplication;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * Created by lukas on 15.03.2017.
 */
@XMLObject("//GetHeadwordImageResponse")
public class HeadwordImage {
    @XMLField("GetHeadwordImageResult")
    private String Image;

    public String getImage(){return Image;}

}
