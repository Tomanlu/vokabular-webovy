package com.example.lukas.vokabularwebovy.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lukas.vokabularwebovy.R;
import com.example.lukas.vokabularwebovy.adapters.Adapter;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.listeners.EndlessRecyclerViewScrollListener;
import com.example.lukas.vokabularwebovy.observers.HeadwordListResponseObserver;

/**
 * Created by lukas on 26.03.2017.
 */
public class ListingFragment extends Fragment {
    private RecyclerView rv;
    private Adapter adapter;
    private DataProvider dataProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.listing_fragment, container, false);
        super.onCreate(savedInstanceState);
        dataProvider = DataProvider.getInstance();

        rv = (RecyclerView)view.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        rv.setLayoutManager(llm);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextData(page);
            }
        };

        rv.setOnScrollListener(scrollListener);
        rv.addOnScrollListener(scrollListener);
        dataProvider.GetHeadwordList(new HeadwordListResponseObserver(), 0, 20);
        adapter = new Adapter();
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(20);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rv.setAdapter(adapter);
        return view;
    }

    private void loadNextData(int page){
        dataProvider.GetHeadwordList(new HeadwordListResponseObserver(), page*20, 20);
    }

}