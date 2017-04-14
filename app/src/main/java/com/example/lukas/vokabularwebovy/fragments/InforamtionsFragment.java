package com.example.lukas.vokabularwebovy.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.lukas.vokabularwebovy.R;
import com.example.lukas.vokabularwebovy.adapters.ListingAdapter;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.listeners.EndlessRecyclerViewScrollListener;
import com.example.lukas.vokabularwebovy.observers.HeadwordListResponseObserver;

/**
 * Created by lukas on 29.03.2017.
 */
public class InforamtionsFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.informations_fragment, container, false);
        TextView textView = (TextView) view.findViewById(R.id.informations_tv);
        if(textView == null) return view;
        textView.setMovementMethod(new ScrollingMovementMethod());
        return  view;
    }

}
