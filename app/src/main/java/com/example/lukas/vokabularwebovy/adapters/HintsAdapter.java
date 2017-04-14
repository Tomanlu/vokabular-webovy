package com.example.lukas.vokabularwebovy.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.lukas.vokabularwebovy.R;

import java.util.List;

/**
 * Created by lukas on 29.03.2017.
 */
public class HintsAdapter extends ArrayAdapter<String> {
    public HintsAdapter(Context context, List<String> objects) {
        super(context, 0,objects);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        String text = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hint_item_layout, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.hint_tv);
        textView.setText(text);

        return convertView;

    }
}
