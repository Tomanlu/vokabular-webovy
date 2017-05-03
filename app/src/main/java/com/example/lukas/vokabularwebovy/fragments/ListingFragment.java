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
import android.widget.EditText;
import android.widget.ProgressBar;
import com.example.lukas.vokabularwebovy.ListType;
import com.example.lukas.vokabularwebovy.MainActivity;
import com.example.lukas.vokabularwebovy.R;
import com.example.lukas.vokabularwebovy.adapters.ListingAdapter;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.listeners.DictionarySetChangedListener;
import com.example.lukas.vokabularwebovy.listeners.EndlessRecyclerViewScrollListener;
import com.example.lukas.vokabularwebovy.listeners.OnHeadwordListCompleteListener;
import com.example.lukas.vokabularwebovy.observers.HeadwordListResponseObserver;
import com.example.lukas.vokabularwebovy.observers.HeadwordRowEntryResponseObserver;

/**
 * Created by lukas on 26.03.2017.
 */
public class ListingFragment extends Fragment implements OnHeadwordListCompleteListener, DictionarySetChangedListener {
    private RecyclerView recyclerView;
    private ListingAdapter adapter;
    private DataProvider dataProvider;
    private Button searchButton;
    private SwipeRefreshLayout swipeContainer;
    private ProgressBar progressBar;
    private int pageSize = 20;
    private int counter = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.listing_fragment, container, false);
        super.onCreateView(inflater, container, savedInstanceState);

        dataProvider = DataProvider.getInstance();
        dataProvider.setDictionarySetChangedListener(this, ListType.headwordList);

        recyclerView = (RecyclerView) view.findViewById(R.id.listing_rv);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        searchButton = (Button) view.findViewById(R.id.searchStart);
        progressBar = (ProgressBar) view.findViewById(R.id.listing_progressBar);

        adapter = new ListingAdapter(this, ListType.headwordList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        final EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                counter++;
                loadNextData(page, false);
            }
        };

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).hideSoftKeyboard();
                String query = ((EditText) view.findViewById(R.id.searchQuery)).getText().toString();
                if (!query.isEmpty()) {
                    dataProvider.getHeadwordRowNumber(new HeadwordRowEntryResponseObserver(pageSize, scrollListener), query);
                    recyclerView.scrollToPosition(0);
                    dataProvider.clearList(ListType.headwordList);
                    counter = 1;
                }
            }
        });

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
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnScrollListener(scrollListener);
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(pageSize);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setAdapter(adapter);

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        dataProvider.clearList(ListType.headwordList);
        loadNextData(0, false);
        return view;
    }

    private void loadNextData(int page, boolean addToTop) {
        progressBar.setVisibility(View.VISIBLE);
        dataProvider.getHeadwordList(new HeadwordListResponseObserver(this, ListType.headwordList, addToTop), page * pageSize, pageSize);

    }


    @Override
    public void onComplete() {
        swipeContainer.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSetChanged() {
        dataProvider.clearList(ListType.headwordList);
        loadNextData(0, false);
    }
}