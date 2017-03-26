package com.example.lukas.vokabularwebovy.models;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * Created by lukas on 15.03.2017.
 */
@XMLObject("//HeadwordBookInfoContract")
public class HeadwordBookInfoContract {
    @XMLField("BookXmlId")
    private String bookXmlId;

    @XMLField("EntryXmlId")
    private String entryXmlId;

    @XMLField("Image")
    private String image;

    public String getBookXmlId() {
        return bookXmlId;
    }

    public String getEntryXmlId() {
        return entryXmlId;
    }

    public String getImage() {
        return image;
    }
}
