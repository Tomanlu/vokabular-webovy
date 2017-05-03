package com.example.lukas.vokabularwebovy.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import com.example.lukas.vokabularwebovy.R;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;

/**
 * Created by lukas on 05.04.2017.
 */
public class DictionariesFragmentDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final DataProvider dataProvider = DataProvider.getInstance();
        final String[] dictionaries =  dataProvider.getDictionaryListAsStringArray();
        final boolean[] mSelectedItems = dataProvider.getCheckedDictionaries();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dictionaries)

                .setMultiChoiceItems(dictionaries, mSelectedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                mSelectedItems[which] = isChecked;
                            }
                        })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dataProvider.setCheckedDictionaries(mSelectedItems);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
}
}
