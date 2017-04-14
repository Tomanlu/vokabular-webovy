package com.example.lukas.vokabularwebovy.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.example.lukas.vokabularwebovy.MainActivity;
import com.example.lukas.vokabularwebovy.R;
import com.example.lukas.vokabularwebovy.adapters.ListingAdapter;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.listeners.EndlessRecyclerViewScrollListener;
import com.example.lukas.vokabularwebovy.observers.HeadwordListResponseObserver;

/**
 * Created by lukas on 26.03.2017.
 */
public class ListingFragment extends Fragment {
    private RecyclerView rv;
    private ListingAdapter adapter;
    private DataProvider dataProvider;
    private Button searchButton;
    private SwipeRefreshLayout swipeContainer;
    private int counter = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.listing_fragment, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        dataProvider = DataProvider.getInstance();

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        final EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextData(page);
            }
        };
        rv = (RecyclerView) view.findViewById(R.id.listing_rv);

        searchButton = (Button) view.findViewById(R.id.searchStart);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).hideSoftKeyboard();
                String query = ((EditText) view.findViewById(R.id.searchQuery)).getText().toString();
                if (!query.isEmpty()) {
                    dataProvider.getHeadwordRowNumber(scrollListener, query);
                    rv.scrollToPosition(0);
                    counter = 1;
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
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

        adapter = new ListingAdapter();
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
        dataProvider.getHeadwordList(new HeadwordListResponseObserver(), 0, 20);
        return view;
    }

    private void loadNextData(int page) {
        dataProvider.getHeadwordList(new HeadwordListResponseObserver(), page * 20, 20);

    }

    private void loadNextData(int page, boolean up) {
        dataProvider.getHeadwordList(new HeadwordListResponseObserver(swipeContainer), page * 20, 20);
    }


}