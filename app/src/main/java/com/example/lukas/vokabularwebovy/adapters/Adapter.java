package com.example.lukas.vokabularwebovy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.lukas.vokabularwebovy.databinding.CardLayoutBinding;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.holders.HeadwordHolder;
import com.example.lukas.vokabularwebovy.models.Headword;
import com.example.lukas.vokabularwebovy.observers.DictionaryEntryResponseObserver;
import com.example.lukas.vokabularwebovy.observers.DictionaryImageResponseObserver;

/**
 * Created by lukas on 20.03.2017.
 */
public class Adapter extends RecyclerView.Adapter<HeadwordHolder> {
     DataProvider dataProvider;

    public Adapter() {
        super();
        dataProvider = DataProvider.getInstance();
        dataProvider.setAdapter(this);
    }

    @Override
    public HeadwordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardLayoutBinding binding = CardLayoutBinding.inflate(inflater, parent, false);
        return new HeadwordHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(HeadwordHolder holder, int position) {
        Headword headword = dataProvider.GetHeadwordFromList(position);
        holder.binding.setHeadword(headword);

    }
//snapzdb
    @Override
    public void onViewAttachedToWindow(HeadwordHolder holder){
        Headword headword = holder.binding.getHeadword();
        if(headword.isImage())
            dataProvider.GetHeadwordImage(headword);
        else
            dataProvider.GetDictionaryEntryByXmlId(headword);

    }

    @Override
    public void onViewDetachedFromWindow(HeadwordHolder holder){
        Headword headword = holder.binding.getHeadword();
        headword.setEntry("");
        headword.setImg(null);
        headword.setReady(false);
    }

    @Override
    public int getItemCount() {
        return dataProvider.GetSizeOfList();
    }





}
