package com.example.lukas.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity   {
    private RecyclerView rv;
    private Adapter adapter;
    private DataProvider dataProvider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataProvider = DataProvider.getInstance();
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                  loadNextData(page);
            }
        };

        rv.setOnScrollListener(scrollListener);
        dataProvider.GetHeadwordList(new HeadwordListResponseObserver(), 0, 20);
        adapter = new Adapter();
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(20);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rv.setAdapter(adapter);
    }

    private void loadNextData(int page){
        dataProvider.GetHeadwordList(new HeadwordListResponseObserver(), page*20, 20);
    }

}
