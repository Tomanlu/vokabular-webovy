package com.example.lukas.vokabularwebovy.models;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * Created by lukas on 14.03.2017.
 */
@XMLObject("//GetDictionaryEntryByXmlIdResponse")
public class DictionaryEntryByXmlId {
    @XMLField("//GetDictionaryEntryByXmlIdResult")
    private String dictionaryEntry;

    public String getDictionaryEntry() {
        return dictionaryEntry;
    }


}
