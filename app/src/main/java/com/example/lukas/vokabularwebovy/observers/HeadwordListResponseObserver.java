package com.example.lukas.vokabularwebovy.observers;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.Request;
import com.example.lukas.vokabularwebovy.ListType;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.listeners.OnHeadwordListCompleteListener;
import com.example.lukas.vokabularwebovy.models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 21.03.2017.
 */
public class HeadwordListResponseObserver implements SOAP11Observer {
    private OnHeadwordListCompleteListener listener;
    private DataProvider dataProvider;
    private ListType listType;
    private boolean addToTop;

    public HeadwordListResponseObserver(OnHeadwordListCompleteListener listener, ListType listType, boolean addToTop) {
        this.listener = listener;
        this.listType = listType;
        this.addToTop = addToTop;
        dataProvider = DataProvider.getInstance();
    }

    public HeadwordListResponseObserver(ListType listType) {
        this.listType = listType;
        dataProvider = DataProvider.getInstance();
    }


    @Override
    public void onCompletion(Request request) {
        HeadwordList list = (HeadwordList) request.getResult();
        List<Headword> tmpList = new ArrayList<>();
        if (list == null || list.getHeadwordEntries() == null) return;

        for (HeadwordEntry headwordEntry : list.getHeadwordEntries()) {
            for (HeadwordBookInfoContract bookInfoContract : headwordEntry.getBookInfo()) {
                String bookTitle = dataProvider.getBookTitleFromXmlId(bookInfoContract.getBookXmlId());
                if (bookInfoContract.getImage() != null && bookInfoContract.getImage() != " ") {
                    Headword headword = new Headword(headwordEntry.getHeadword(), bookInfoContract.getBookXmlId(), bookTitle, null, bookInfoContract.getImage());
                    tmpList.add(headword);
                } else {
                    Headword headword = new Headword(headwordEntry.getHeadword(), bookInfoContract.getBookXmlId(), bookTitle, bookInfoContract.getEntryXmlId(), null);
                    tmpList.add(headword);
                }
            }
        }
        if(listener != null)
            listener.onComplete();
        dataProvider.addAllToHeadwordList(tmpList, addToTop, listType);

    }




    @Override
    public void onException(Request request, SOAPException e) {

    }
}
