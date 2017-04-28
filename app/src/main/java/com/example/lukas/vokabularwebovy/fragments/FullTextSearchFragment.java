package com.example.lukas.vokabularwebovy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.lukas.vokabularwebovy.R;
import com.example.lukas.vokabularwebovy.adapters.ListingAdapter;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.listeners.EndlessRecyclerViewScrollListener;
import com.example.lukas.vokabularwebovy.observers.HeadwordListResponseObserver;

/**
 * Created by lukas on 28.04.2017.
 */
public class FullTextSearchFragment extends Fragment {
    private RecyclerView rv;
    private ListingAdapter adapter;
    private DataProvider dataProvider;
    private Button searchButton;
    private SwipeRefreshLayout swipeContainer;
    private int counter = 1;
    public FullTextSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.basic_headword_search_layout, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        dataProvider = DataProvider.getInstance();
        dataProvider.ClearList();
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        final EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextData(page);
            }
        };
        rv = (RecyclerView) view.findViewById(R.id.listing_rv);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int page = scrollListener.getCurrentPage();
                if (page - 1 * counter >= 0) {
                    loadNextData(page - 1 * counter++, true);

                } else {
                    swipeContainer.setRefreshing(false);
                }
            }
        });


        rv.setLayoutManager(llm);
        rv.setOnScrollListener(scrollListener);
        rv.addOnScrollListener(scrollListener);

        adapter = new ListingAdapter(this);
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(20);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rv.setAdapter(adapter);

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        dataProvider.getDictionariesWithCategories();
        loadNextData(0);

        return view;
    }
    private void loadNextData(int page) {
        dataProvider.searchHeadwordByCriteria(new HeadwordListResponseObserver(), page * 20, 20, true);

    }

    private void loadNextData(int page, boolean up) {
        dataProvider.searchHeadwordByCriteria(new HeadwordListResponseObserver(swipeContainer), page * 20, 20, true);
    }
}
