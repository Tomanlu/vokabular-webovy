package com.example.lukas.vokabularwebovy.holders;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.example.lukas.vokabularwebovy.R;
import com.example.lukas.vokabularwebovy.databinding.CardLayoutBinding;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by lukas on 20.03.2017.
 */
public class HeadwordHolder extends RecyclerView.ViewHolder {
   /* CardView cardView;
    TextView headword;
    TextView dictionaryEntry;
    ImageView image;
*/
    public CardLayoutBinding binding;

    public HeadwordHolder(View itemView) {
        super(itemView);
     /*   cardView = (CardView)itemView.findViewById(R.id.cv);
        headword = (TextView)itemView.findViewById(R.id.headword);
        dictionaryEntry = (TextView)itemView.findViewById(R.id.dictionaryEntry);
        image = (ImageView)itemView.findViewById(R.id.image);*/
        binding = DataBindingUtil.bind(itemView);


    }
}

