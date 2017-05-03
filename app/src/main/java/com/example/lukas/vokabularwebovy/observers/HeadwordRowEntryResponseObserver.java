package com.example.lukas.vokabularwebovy.observers;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.Request;
import com.example.lukas.vokabularwebovy.ListType;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.listeners.EndlessRecyclerViewScrollListener;
import com.example.lukas.vokabularwebovy.models.*;

/**
 * Created by lukas on 27.03.2017.
 */
public class HeadwordRowEntryResponseObserver implements SOAP11Observer {
    private int pageSize;
    private EndlessRecyclerViewScrollListener scrollListener;
    private DataProvider dataProvider;
    public HeadwordRowEntryResponseObserver(int pageSize, EndlessRecyclerViewScrollListener scrollListener) {
        this.pageSize = pageSize;
        this.scrollListener = scrollListener;
        dataProvider = DataProvider.getInstance();
    }

    @Override
    public void onCompletion(Request request) {
        HeadwordRowNumber rowNumber = (HeadwordRowNumber) request.getResult();
        if(rowNumber == null) return;
        int row = rowNumber.getRow() - 1;
        scrollListener.setCurrentPage(row/pageSize);
        scrollListener.resetState();
        dataProvider.getHeadwordList(new HeadwordListResponseObserver(ListType.headwordList), row-(row%pageSize), pageSize);

    }



    @Override
    public void onException(Request request, SOAPException e) {

    }
}
