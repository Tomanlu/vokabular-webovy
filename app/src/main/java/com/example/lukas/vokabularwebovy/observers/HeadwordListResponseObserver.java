package com.example.lukas.vokabularwebovy.observers;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.Request;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.models.Headword;
import com.example.lukas.vokabularwebovy.models.HeadwordBookInfoContract;
import com.example.lukas.vokabularwebovy.models.HeadwordEntry;
import com.example.lukas.vokabularwebovy.models.HeadwordList;

/**
 * Created by lukas on 21.03.2017.
 */
public class HeadwordListResponseObserver implements SOAP11Observer {

    @Override
    public void onCompletion(Request request) {
        HeadwordList list = (HeadwordList) request.getResult();

        for(HeadwordEntry headwordEntry : list.getHeadwordEntries()){
            for(HeadwordBookInfoContract bookInfoContract : headwordEntry.getBookInfo()){
                    if(bookInfoContract.getImage() != null && bookInfoContract.getImage() != " ") {
                        Headword headword = new Headword(headwordEntry.getHeadword(), bookInfoContract.getBookXmlId(),null, bookInfoContract.getImage());
                          DataProvider.getInstance().AddToList(headword);
                    }
                    else {
                        Headword headword = new Headword(headwordEntry.getHeadword(), bookInfoContract.getBookXmlId(),bookInfoContract.getEntryXmlId(), null);
                        DataProvider.getInstance().AddToList(headword);
                    }
            }

        }
    }



    @Override
    public void onException(Request request, SOAPException e) {

    }
}
