package com.example.lukas.vokabularwebovy.models;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

import java.util.List;

/**
 * Created by lukas on 05.04.2017.
 */
@XMLObject("//GetBooksWithCategoriesByBookTypeResponse")
public class DictionariesWithCategories {
    @XMLField("//BookContractWithCategories")
    private List<DictionaryWithCategories> dictionariesWithCategories;

    public List<DictionaryWithCategories> getDictionariesWithCategories() {
        return dictionariesWithCategories;
    }
}
