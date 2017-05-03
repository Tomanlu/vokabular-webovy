package com.example.lukas.vokabularwebovy.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lukas.vokabularwebovy.ListType;
import com.example.lukas.vokabularwebovy.R;
import com.example.lukas.vokabularwebovy.adapters.ViewPagerAdapter;

/**
 * Created by lukas on 01.05.2017.
 */
public class SearchResultFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.tab_layout, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager, true);
        return view;

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(SearchTabFragment.newInstance(ListType.headwordBasicSearchList), "Hesla");
        adapter.addFragment(SearchTabFragment.newInstance(ListType.headwordFullTextSearchList), "Fulltext");
        viewPager.setAdapter(adapter);
    }
}
