package com.example.lukas.vokabularwebovy.models;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * Created by lukas on 27.03.2017.
 */
@XMLObject("//GetHeadwordRowNumberResponse")
public class HeadwordRowNumber {
    @XMLField("GetHeadwordRowNumberResult")
    private int row;
    public int getRow(){
        return row;
    }
}
