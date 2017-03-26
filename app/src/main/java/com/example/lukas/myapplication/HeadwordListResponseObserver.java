package com.example.lukas.myapplication;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 21.03.2017.
 */
public class HeadwordListResponseObserver implements SOAP11Observer {
    DataProvider dataProvider;

    public HeadwordListResponseObserver() {
        this.dataProvider = DataProvider.getInstance();
    }

    @Override
    public void onCompletion(Request request) {
        HeadwordList list = (HeadwordList) request.getResult();

        for(HeadwordEntry headwordEntry : list.getHeadwordEntries()){
            for(HeadwordBookInfoContract bookInfoContract : headwordEntry.getBookInfo()){
                    if(bookInfoContract.getImage() != null && bookInfoContract.getImage() != " ") {
                        Headword headword = new Headword(headwordEntry.getHeadword(), bookInfoContract.getBookXmlId(),null, bookInfoContract.getImage());
                          dataProvider.AddToList(headword);
                    }
                    else {
                        Headword headword = new Headword(headwordEntry.getHeadword(), bookInfoContract.getBookXmlId(),bookInfoContract.getEntryXmlId(), null);
                        dataProvider.AddToList(headword);
                    }
            }

        }
    }



    @Override
    public void onException(Request request, SOAPException e) {

    }
}
