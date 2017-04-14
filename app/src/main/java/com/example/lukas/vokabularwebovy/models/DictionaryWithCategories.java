package com.example.lukas.vokabularwebovy.models;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * Created by lukas on 05.04.2017.
 */
@XMLObject("//BookContractWithCategories")
public class DictionaryWithCategories {
    @XMLField("Guid")
    private String guid;

    @XMLField("Id")
    private String id;

    @XMLField("Subtitle")
    private String subtitle;

    @XMLField("Title")
    private String title;

    public String getGuid() {
        return guid;
    }

    public String getId() {
        return id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTitle() {
        return title;
    }
}
