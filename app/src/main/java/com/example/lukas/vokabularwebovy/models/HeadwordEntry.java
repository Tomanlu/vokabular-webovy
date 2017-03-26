package com.example.lukas.vokabularwebovy.models;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

import java.util.List;

/**
 * Created by lukas on 12.03.2017.
 */
@XMLObject("//HeadwordContract")
public class HeadwordEntry {
    @XMLField("Headword")
    private String headword;
    @XMLField("Dictionaries/HeadwordBookInfoContract")
    private List<HeadwordBookInfoContract> bookInfo;


    public String getHeadword() {
        return headword;
    }

    public List<HeadwordBookInfoContract> getBookInfo() { return bookInfo; }
}
