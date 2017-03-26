package com.example.lukas.vokabularwebovy.models;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * Created by lukas on 17.12.2016.
 */
@XMLObject("//GetHeadwordCountResponse")
public class HeadwordCount {
    @XMLField("GetHeadwordCountResult")
    private int count;
    public int getCount(){
        return count;
    }

}

