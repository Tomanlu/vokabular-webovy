package com.example.lukas.vokabularwebovy.observers;

import android.support.v4.widget.SwipeRefreshLayout;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.Request;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.models.Headword;
import com.example.lukas.vokabularwebovy.models.HeadwordBookInfoContract;
import com.example.lukas.vokabularwebovy.models.HeadwordEntry;
import com.example.lukas.vokabularwebovy.models.HeadwordList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 21.03.2017.
 */
public class HeadwordListResponseObserver implements SOAP11Observer {
    private SwipeRefreshLayout swipeContainer;

    public HeadwordListResponseObserver() {
    }

    public HeadwordListResponseObserver(SwipeRefreshLayout swipeContainer) {
        this.swipeContainer = swipeContainer;
    }

    @Override
    public void onCompletion(Request request) {
        HeadwordList list = (HeadwordList) request.getResult();

        List<Headword> tmpList = new ArrayList<>();
        for(HeadwordEntry headwordEntry : list.getHeadwordEntries()){
            for(HeadwordBookInfoContract bookInfoContract : headwordEntry.getBookInfo()){
                    if(bookInfoContract.getImage() != null && bookInfoContract.getImage() != " ") {
                        Headword headword = new Headword(headwordEntry.getHeadword(), bookInfoContract.getBookXmlId(),null, bookInfoContract.getImage());
                        tmpList.add(headword);
                    }
                    else {
                        Headword headword = new Headword(headwordEntry.getHeadword(), bookInfoContract.getBookXmlId(),bookInfoContract.getEntryXmlId(), null);
                        tmpList.add(headword);
                    }
            }

        }

        if(swipeContainer != null){
            DataProvider.getInstance().addAllToList(tmpList, true);
            swipeContainer.setRefreshing(false);
        }else{
            DataProvider.getInstance().addAllToList(tmpList, false);

        }


    }



    @Override
    public void onException(Request request, SOAPException e) {

    }
}