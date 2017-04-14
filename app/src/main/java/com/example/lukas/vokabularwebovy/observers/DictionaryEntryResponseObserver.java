package com.example.lukas.vokabularwebovy.observers;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.Request;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.models.DictionaryEntryByXmlId;
import com.example.lukas.vokabularwebovy.models.Headword;

/**
 * Created by lukas on 15.03.2017.
 */
public class DictionaryEntryResponseObserver implements SOAP11Observer {
   Headword headword;


    public DictionaryEntryResponseObserver(Headword headword) {
        this.headword = headword;
    }

    @Override
    public void onCompletion(Request request) {
        DictionaryEntryByXmlId entry = (DictionaryEntryByXmlId) request.getResult();
        if(headword.isInView())
            headword.setEntry(entry.getDictionaryEntry());
        DataProvider.getInstance().addToCache(headword.getBookXmlId() + headword.getEntryXmlId(),entry.getDictionaryEntry());

    }

    @Override
    public void onException(Request request, SOAPException e) {
    }
}
