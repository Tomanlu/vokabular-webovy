package com.example.lukas.vokabularwebovy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.lukas.vokabularwebovy.R;

import java.util.List;

/**
 * Created by lukas on 10.04.2017.
 */
public class HistoryAdapter extends ArrayAdapter<String> {

    public HistoryAdapter(Context context, List<String> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        String text = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_item_layout, parent, false);
        }

        TextView textView = (TextView)convertView.findViewById(R.id.history_tv);
        textView.setText(text);

        return convertView;

    }
}