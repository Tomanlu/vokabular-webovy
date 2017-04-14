package com.example.lukas.vokabularwebovy.observers;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.Request;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.models.DictionariesWithCategories;

/**
 * Created by lukas on 05.04.2017.
 */
public class DictionariesWithCategoriesResponseObserver implements SOAP11Observer {
    @Override
    public void onCompletion(Request request) {
        DictionariesWithCategories dictionariesWithCategories = (DictionariesWithCategories)request.getResult();
        DataProvider.getInstance().setDictionaryList(dictionariesWithCategories.getDictionariesWithCategories());

    }

    @Override
    public void onException(Request request, SOAPException e) {

    }
}
