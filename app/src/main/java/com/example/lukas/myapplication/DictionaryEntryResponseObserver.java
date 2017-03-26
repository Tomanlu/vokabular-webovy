package com.example.lukas.myapplication;

import android.content.SharedPreferences;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.Request;

/**
 * Created by lukas on 15.03.2017.
 */
public class DictionaryEntryResponseObserver implements SOAP11Observer {
   /* private String headword;
    private String bookXmlId;
    private String entryXmlId;
    private DataProvider dataProvider;*/
   Headword headword;

   /*  public DictionaryEntryResponseObserver(String headword, String bookXmlId, String entryXmlId) {
       this.headword = headword;
        this.bookXmlId = bookXmlId;
        this.entryXmlId = entryXmlId;
        this.dataProvider = DataProvider.getInstance();

    }*/

    public DictionaryEntryResponseObserver(Headword headword) {
        this.headword = headword;
    }

    @Override
    public void onCompletion(Request request) {
    /*    DictionaryEntryByXmlId entry = (DictionaryEntryByXmlId) request.getResult();
        Headword headword = new Headword(this.headword, bookXmlId, entryXmlId, null, entry.getDictionaryEntry());
        dataProvider.AddToList(headword);*/
        DictionaryEntryByXmlId entry = (DictionaryEntryByXmlId) request.getResult();
        headword.setEntry(entry.getDictionaryEntry());
    }

    @Override
    public void onException(Request request, SOAPException e) {
    }
}
