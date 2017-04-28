package com.example.lukas.vokabularwebovy.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.lukas.vokabularwebovy.MainActivity;
import com.example.lukas.vokabularwebovy.R;
import com.example.lukas.vokabularwebovy.adapters.HintsAdapter;
import com.example.lukas.vokabularwebovy.adapters.HistoryAdapter;
import com.example.lukas.vokabularwebovy.adapters.ViewPagerAdapter;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.observers.TypeheadHeadwordsObserver;

import java.util.ArrayList;

/**
 * Created by lukas on 28.03.2017.
 */
public class SearchFragment extends Fragment {

    private ListView hints;
    private ListView history;
    private EditText et;
    private HintsAdapter hintsAdapter;
    private HistoryAdapter historyAdapter;
    private DataProvider dataProvider;
    private Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.search_fragment, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        dataProvider = DataProvider.getInstance();
        et = (EditText) view.findViewById(R.id.search_et);
        hints = (ListView) view.findViewById(R.id.hint_lv);
        history = (ListView) view.findViewById(R.id.history_lv);


        hintsAdapter = new HintsAdapter(getContext(), new ArrayList<String>());
        hints.setAdapter(hintsAdapter);
        hints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               et.setText((String)adapterView.getItemAtPosition(i));
            }
        });

        historyAdapter = new HistoryAdapter(getContext(), dataProvider.getSearchHistory(getContext()));
        history.setAdapter(historyAdapter);
        history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                et.setText((String)adapterView.getItemAtPosition(i));
            }
        });
        searchButton = (Button) view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).hideSoftKeyboard();
                String text = et.getText().toString();
                dataProvider.addItemAndSaveSearchHistory(text, getContext());
                MainActivity activity = (MainActivity)getActivity();
               activity.setFragment(new BasicHeadwordSearchFragment(),"Vyhledávání", true);
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                dataProvider.getTypeheadHeadword(new TypeheadHeadwordsObserver(hintsAdapter), editable.toString());
            }
        });
        hints = (ListView) view.findViewById(R.id.hint_lv);
        return view;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new BasicHeadwordSearchFragment(), "Hesla");
        adapter.addFragment(new FullTextSearchFragment(), "Fulltext");
        viewPager.setAdapter(adapter);
    }
}