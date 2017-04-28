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
        final boolean[] mSelectedItems = DataProvider.getInstance().getCheckedDictionaries();  // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dictionaries)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(DataProvider.getInstance().getDictionaryListAsStringArray(), mSelectedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                mSelectedItems[which] = isChecked;
                            }
                        })
                // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DataProvider.getInstance().setCheckedDictionaries(mSelectedItems);
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
