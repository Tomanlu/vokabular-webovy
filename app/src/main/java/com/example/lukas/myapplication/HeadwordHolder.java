package com.example.lukas.myapplication;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lukas.myapplication.databinding.CardLayoutBinding;

/**
 * Created by lukas on 20.03.2017.
 */
public class HeadwordHolder extends RecyclerView.ViewHolder {
   /* CardView cardView;
    TextView headword;
    TextView dictionaryEntry;
    ImageView image;
*/
   CardLayoutBinding binding;

    HeadwordHolder(View itemView) {
        super(itemView);
     /*   cardView = (CardView)itemView.findViewById(R.id.cv);
        headword = (TextView)itemView.findViewById(R.id.headword);
        dictionaryEntry = (TextView)itemView.findViewById(R.id.dictionaryEntry);
        image = (ImageView)itemView.findViewById(R.id.image);*/
        binding = DataBindingUtil.bind(itemView);

    }
}

