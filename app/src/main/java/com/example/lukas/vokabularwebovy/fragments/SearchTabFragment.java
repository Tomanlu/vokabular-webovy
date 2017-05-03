package com.example.lukas.vokabularwebovy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.example.lukas.vokabularwebovy.ListType;
import com.example.lukas.vokabularwebovy.R;
import com.example.lukas.vokabularwebovy.adapters.ListingAdapter;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.listeners.DictionarySetChangedListener;
import com.example.lukas.vokabularwebovy.listeners.EndlessRecyclerViewScrollListener;
import com.example.lukas.vokabularwebovy.listeners.OnHeadwordListCompleteListener;
import com.example.lukas.vokabularwebovy.observers.HeadwordListResponseObserver;

/**
 * Created by lukas on 01.05.2017.
 */
public class SearchTabFragment extends Fragment implements OnHeadwordListCompleteListener, DictionarySetChangedListener {
    private RecyclerView rv;
    private ListingAdapter adapter;
    private DataProvider dataProvider;
    private int pageSize = 20;
    private ProgressBar progressBar;
    private ListType listType;
    public SearchTabFragment(){
        super();
    }

    public static SearchTabFragment newInstance(ListType listType) {
        SearchTabFragment fragment = new SearchTabFragment();
        Bundle args = new Bundle();
        args.putSerializable("listType", listType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listType = (ListType) getArguments().getSerializable("listType");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.search_result_fragment, container, false);
        super.onCreateView(inflater, container, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        final EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextData(page);
            }
        };
        rv = (RecyclerView) view.findViewById(R.id.search_result_rv);
        progressBar = (ProgressBar) view.findViewById(R.id.search_result_progressBar);
        adapter = new ListingAdapter(this, listType);

        dataProvider = DataProvider.getInstance();
        dataProvider.setDictionarySetChangedListener(this, listType);

        rv.setLayoutManager(layoutManager);
        rv.setOnScrollListener(scrollListener);
        rv.addOnScrollListener(scrollListener);

        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(pageSize);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rv.setAdapter(adapter);

        dataProvider.clearList(listType);
        loadNextData(0);

        return view;
    }

    private void loadNextData(int page) {
        progressBar.setVisibility(View.VISIBLE);
        dataProvider.searchHeadwordByCriteria(new HeadwordListResponseObserver(this, listType, false), page * pageSize, pageSize, listType);
    }
    @Override
    public void onComplete() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSetChanged() {
        dataProvider.clearList(listType);
        loadNextData(0);
    }
}

