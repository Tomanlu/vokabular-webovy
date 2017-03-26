package com.example.lukas.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.lukas.myapplication.databinding.CardLayoutBinding;

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
            dataProvider.GetHeadwordImage(new DictionaryImageResponseObserver(headword), headword.getBookXmlId(), headword.getImageName());
        else
            dataProvider.GetDictionaryEntryByXmlId(new DictionaryEntryResponseObserver(headword), headword.getBookXmlId(), headword.getEntryXmlId());

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
