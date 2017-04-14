package com.example.lukas.vokabularwebovy.models;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

import java.util.List;

/**
 * Created by lukas on 29.03.2017.
 */
@XMLObject("//GetTypeaheadDictionaryHeadwordsResponse")
public class TypeheadHeadwords {
    @XMLField("//string")
    private List<String> hints;

    public List<String> getHints() {
        return hints;
    }
}
