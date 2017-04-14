package com.example.lukas.vokabularwebovy.observers;

import android.support.v7.widget.RecyclerView;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.Request;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.listeners.EndlessRecyclerViewScrollListener;
import com.example.lukas.vokabularwebovy.models.*;

/**
 * Created by lukas on 27.03.2017.
 */
public class HeadwordRowEntryResponseObserver implements SOAP11Observer {
    private RecyclerView.Adapter adapter;
private EndlessRecyclerViewScrollListener scrollListener;
    public HeadwordRowEntryResponseObserver(RecyclerView.Adapter adapter, EndlessRecyclerViewScrollListener scrollListener) {
        this.adapter = adapter;
        this.scrollListener = scrollListener;
    }

    @Override
    public void onCompletion(Request request) {
        HeadwordRowNumber rowNumber = (HeadwordRowNumber) request.getResult();
        if(rowNumber == null) return;
        int row = rowNumber.getRow() - 1;
        scrollListener.setCurrentPage(row/20);
        DataProvider.getInstance().ClearList();
        DataProvider.getInstance().getHeadwordList(new HeadwordListResponseObserver(), row-1, 20);

    }



    @Override
    public void onException(Request request, SOAPException e) {

    }
}
