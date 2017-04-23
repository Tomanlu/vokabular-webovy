package com.example.lukas.vokabularwebovy.models;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * Created by lukas on 23.04.2017.
 */
@XMLObject("//GetDictionaryEntryFromSearchResponse")
public class DictionaryEntryFromSearch {
    @XMLField("//GetDictionaryEntryFromSearchResult")
    private String dictionaryEntry;

    public String getDictionaryEntry() {
        return dictionaryEntry;
    }

}
