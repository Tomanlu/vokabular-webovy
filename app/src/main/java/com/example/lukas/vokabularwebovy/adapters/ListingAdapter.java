package com.example.lukas.vokabularwebovy.adapters;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.lukas.vokabularwebovy.ImageFullScreen;
import com.example.lukas.vokabularwebovy.ListType;
import com.example.lukas.vokabularwebovy.R;
import com.example.lukas.vokabularwebovy.databinding.CardLayoutBinding;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.holders.HeadwordHolder;
import com.example.lukas.vokabularwebovy.models.Headword;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lukas on 20.03.2017.
 */
public class ListingAdapter extends RecyclerView.Adapter<HeadwordHolder> {
    private DataProvider dataProvider;
    private Set<Integer> mExpandedPosition = new HashSet<>();
    private Fragment fragment;
    private ListType listType;

    public ListingAdapter(Fragment fragment, ListType listType) {
        super();
        this.fragment = fragment;
        this.listType = listType;
        dataProvider = DataProvider.getInstance();
        dataProvider.setAdapter(this, listType);

    }

    @Override
    public HeadwordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardLayoutBinding binding = CardLayoutBinding.inflate(inflater, parent, false);
        return new HeadwordHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final HeadwordHolder holder, final int position) {
        final Headword headword = dataProvider.getHeadwordFromList(position, listType);
        holder.binding.setHeadword(headword);
        final LinearLayout entry = (LinearLayout) holder.itemView.findViewById(R.id.content);
        final boolean isExpanded = mExpandedPosition.contains(position);
        entry.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        if (isExpanded) {
            headword.setInView(true);
            if (headword.isImage()) {
                dataProvider.getHeadwordImageByXmlId(headword);
                final ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.image);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newIntent = new Intent(fragment.getActivity(), ImageFullScreen.class);
                        newIntent.putExtra("position", position);
                        newIntent.putExtra("listType", listType);
                        fragment.startActivity(newIntent);
                    }
                });
            } else
                dataProvider.getDictionaryEntry(headword, listType);

        } else {
            headword.setInView(false);
            headword.setEntry("");
            headword.setImg(null);
            headword.setReady(false);
        }
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (!isExpanded) mExpandedPosition.add(position);
                else mExpandedPosition.remove(position);
                notifyItemChanged(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataProvider.getSizeOfList(listType);
    }


}
