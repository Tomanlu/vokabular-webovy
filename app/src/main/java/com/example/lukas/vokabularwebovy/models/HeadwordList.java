package com.example.lukas.vokabularwebovy.models;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

import java.util.List;

/**
 * Created by lukas on 12.03.2017.
 */
@XMLObject("//Body")
public class HeadwordList {
    @XMLField("//KeyValueOfstringDictionaryContractogreX8G6")
    private List<Dictionary> dictionaries;

    @XMLField("//HeadwordContract")
    private List<HeadwordEntry> headwordEntries;

    public List<Dictionary> getDictionaries() {
        return dictionaries;
    }

    public List<HeadwordEntry> getHeadwordEntries() {
        return headwordEntries;
    }


}
