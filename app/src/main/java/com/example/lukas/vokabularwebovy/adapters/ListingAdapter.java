package com.example.lukas.vokabularwebovy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.lukas.vokabularwebovy.databinding.CardLayoutBinding;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.holders.HeadwordHolder;
import com.example.lukas.vokabularwebovy.models.Headword;
/**
 * Created by lukas on 20.03.2017.
 */
public class ListingAdapter extends RecyclerView.Adapter<HeadwordHolder> {
    private DataProvider dataProvider;
    private int mExpandedPosition = -1;
    public ListingAdapter() {
        super();
        dataProvider = DataProvider.getInstance();
        dataProvider.setAdapter(this);
    }
private RecyclerView rv;
    @Override
    public HeadwordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        rv = (RecyclerView)parent;
        CardLayoutBinding binding = CardLayoutBinding.inflate(inflater, parent, false);
        return new HeadwordHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(HeadwordHolder holder, int position) {
        Headword headword = dataProvider.getHeadwordFromList(position);
        holder.binding.setHeadword(headword);
      /*  final boolean isExpanded = position==mExpandedPosition;
        holder.binding.entry.setVisibility(isExpanded ? View.VISIBLE: View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                TransitionManager.beginDelayedTransition(rv);
                notifyDataSetChanged();
            }
        });*/

    }
//snapzdb
    @Override
    public void onViewAttachedToWindow(HeadwordHolder holder){
        Headword headword = holder.binding.getHeadword();
        headword.setInView(true);
        if(headword.isImage())
            dataProvider.getHeadwordImage(headword);
        else
            dataProvider.getDictionaryEntryByXmlId(headword);

    }

    @Override
    public void onViewDetachedFromWindow(HeadwordHolder holder){
        Headword headword = holder.binding.getHeadword();
        headword.setInView(false);
        headword.setEntry("");
        headword.setImg(null);
        headword.setReady(false);
    }

    @Override
    public int getItemCount() {
        return dataProvider.getSizeOfList();
    }





}
