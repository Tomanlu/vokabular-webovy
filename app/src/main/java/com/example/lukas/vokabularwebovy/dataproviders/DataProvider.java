package com.example.lukas.vokabularwebovy.dataproviders;

import android.content.Context;
import android.content.SharedPreferences;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.example.lukas.vokabularwebovy.ListType;
import com.example.lukas.vokabularwebovy.adapters.ListingAdapter;
import com.example.lukas.vokabularwebovy.listeners.DictionarySetChangedListener;
import com.example.lukas.vokabularwebovy.models.*;
import com.example.lukas.vokabularwebovy.observers.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 14.03.2017.
 */
public final class DataProvider {
    private static DataProvider instance = null;
    private static ServerDataProvider serverDataProvider;
    private List<Headword> headwordList;
    private List<Headword> headwordBasicSearchList;
    private List<Headword> headwordFullTextSearchList;

    private List<DictionaryWithCategories> dictionaryList;
    private CacheDataProvider<String, String> listEntryCache;
    private boolean[] checkedDictionaries;
    private String query;

    private ListingAdapter headwordListAdapter;
    private ListingAdapter headwordBasicSearchListAdapter;
    private ListingAdapter headwordFullTextSearchListAdapter;

    private DictionarySetChangedListener headwordListListener;
    private DictionarySetChangedListener headwordBasicSearchListListener;
    private DictionarySetChangedListener headwordFullTextSearchListListener;

    private DataProvider() {
        headwordList = new ArrayList<>();
        headwordBasicSearchList = new ArrayList<>();
        headwordFullTextSearchList = new ArrayList<>();
        serverDataProvider = ServerDataProvider.getInstance();
        dictionaryList = new ArrayList<>();
        listEntryCache = new CacheDataProvider<>();
    }

    public static DataProvider getInstance() {
        if (instance == null) instance = new DataProvider();
        return instance;
    }

    public void getHeadwordCount(SOAP11Observer observer) {
        serverDataProvider.getHeadwordCount(observer);
    }

    public void getDictionariesWithCategories() {
        serverDataProvider.getDictionariesWithCategories();
    }

    public void getHeadwordList(SOAP11Observer observer, int start, int count) {
        serverDataProvider.getHeadwordList(observer, start, count, getCheckedDictionariesAsList());
    }

    public void getHeadwordRowNumber(SOAP11Observer observer, String query) {
        serverDataProvider.getHeadwordRowNumber(observer, query);
    }

    public void searchHeadwordByCriteria(SOAP11Observer observer, int start, int count, ListType listType) {
        if (listType == ListType.headwordFullTextSearchList)
            serverDataProvider.searchHeadwordByCriteria(observer, start, count, query, true, getCheckedDictionariesAsList());
        else
            serverDataProvider.searchHeadwordByCriteria(observer, start, count, query, false, getCheckedDictionariesAsList());

    }

    public void getTypeheadHeadword(SOAP11Observer observer, String query) {
        serverDataProvider.getTypeheadHeadword(observer, query);
    }

    public void getHeadwordImageByXmlId(Headword headword) {
        String entry = listEntryCache.getItem(headword.getBookXmlId() + headword.getImageName());
        DictionaryImageResponseObserver observer = new DictionaryImageResponseObserver(headword);
        if (entry != null) {
            DictionaryImageResponseObserver.BitmapWorkerTask task = observer.getTask();
            task.execute(entry);
        } else {
            serverDataProvider.getHeadwordImageByXmlId(observer, headword);
        }

    }

    public void getDictionaryEntry(Headword headword, ListType listType) {
        if (listType == ListType.headwordFullTextSearchList) {
            getDictionaryEntryFromSearch(headword);
        } else {
            getDictionaryEntryByXmlId(headword);
        }
    }

    private void getDictionaryEntryByXmlId(Headword headword) {
        String entry = listEntryCache.getItem(headword.getBookXmlId() + headword.getEntryXmlId());
        if (entry != null) {
            headword.setEntry(entry);
        } else {
            serverDataProvider.getDictionaryEntryByXmlId(headword);
        }
    }

    private void getDictionaryEntryFromSearch(Headword headword) {
        String entry = listEntryCache.getItem(headword.getBookXmlId() + headword.getEntryXmlId());
        if (entry != null) {
            headword.setEntry(entry);
        } else {
            serverDataProvider.getDictionaryEntryFromSearch(headword, query);
        }
    }

    public void addToCache(String key, String value) {
        listEntryCache.addItem(key, value);
    }

    public Headword getHeadwordFromList(int position, ListType listType) {
        switch (listType) {
            case headwordList:
                return headwordList.get(position);
            case headwordBasicSearchList:
                return headwordBasicSearchList.get(position);
            case headwordFullTextSearchList:
                return headwordFullTextSearchList.get(position);
        }
        return null;
    }

    public int getSizeOfList(ListType listType) {
        switch (listType) {
            case headwordList:
                return headwordList.size();
            case headwordBasicSearchList:
                return headwordBasicSearchList.size();
            case headwordFullTextSearchList:
                return headwordFullTextSearchList.size();
        }
        return 0;
    }

    public void addAllToHeadwordList(List<Headword> list, boolean addToTop, ListType listType) {
        int size;
        if (addToTop) {
            List tmp = new ArrayList(headwordList);
            headwordList.clear();
            headwordList.addAll(list);
            headwordList.addAll(tmp);
            notifyDataSetChanged(listType);
        } else {
            switch (listType) {
                case headwordList:
                    size = headwordList.size();
                    headwordList.addAll(list);
                    headwordListAdapter.notifyItemRangeInserted(size, list.size());
                    break;
                case headwordBasicSearchList:
                    size = headwordBasicSearchList.size();
                    headwordBasicSearchList.addAll(list);
                    headwordBasicSearchListAdapter.notifyItemRangeInserted(size, list.size());
                    break;
                case headwordFullTextSearchList:
                    size = headwordFullTextSearchList.size();
                    headwordFullTextSearchList.addAll(list);
                    headwordFullTextSearchListAdapter.notifyItemRangeInserted(size, list.size());
                    break;
            }

        }

    }

    @Nullable
    public String getBookTitleFromXmlId(String XmlId) {
        for (DictionaryWithCategories dictionary : dictionaryList) {
            if (dictionary.getGuid().equals(XmlId))
                return dictionary.getTitle();
        }
        return null;
    }

    public void clearList(ListType listType) {
        switch (listType) {
            case headwordList:
                headwordList.clear();
                break;
            case headwordBasicSearchList:
                headwordBasicSearchList.clear();
                break;
            case headwordFullTextSearchList:
                headwordFullTextSearchList.clear();
                break;
        }
        notifyDataSetChanged(listType);
    }

    public void setAdapter(ListingAdapter adapter, ListType listType) {
        switch (listType) {
            case headwordList:
                headwordListAdapter = adapter;
                break;
            case headwordBasicSearchList:
                headwordBasicSearchListAdapter = adapter;
                break;
            case headwordFullTextSearchList:
                headwordFullTextSearchListAdapter = adapter;
                break;
        }
    }

    public String[] getDictionaryListAsStringArray() {
        if (dictionaryList == null || dictionaryList.size() == 0) return null;
        String[] dictionaryNames = new String[dictionaryList.size()];
        for (int i = 0; i < dictionaryList.size(); i++) {
            dictionaryNames[i] = dictionaryList.get(i).getTitle();
        }
        return dictionaryNames;
    }

    public void setDictionaryList(List<DictionaryWithCategories> dictionaryList) {
        this.dictionaryList = dictionaryList;
        this.checkedDictionaries = new boolean[dictionaryList.size()];
    }

    public void setCheckedDictionaries(boolean[] checkedDictionaries) {
        if (checkedDictionaries == null) return;
        this.checkedDictionaries = checkedDictionaries;

        if(headwordListListener != null)
            headwordListListener.onSetChanged();
        if(headwordBasicSearchListListener != null)
            headwordBasicSearchListListener.onSetChanged();
        if(headwordBasicSearchListListener != null)
            headwordBasicSearchListListener.onSetChanged();


    }

    public void setDictionarySetChangedListener(DictionarySetChangedListener listener, ListType listType){
        switch (listType) {
            case headwordList:
                headwordListListener = listener;
                break;
            case headwordBasicSearchList:
                headwordBasicSearchListListener = listener;
                break;
            case headwordFullTextSearchList:
                headwordFullTextSearchListListener= listener;
                break;
        }
    }
    public boolean[] getCheckedDictionaries() {
        return this.checkedDictionaries;
    }

    public void setSearchQuery(String newItem, Context context) {
        List<String> history = getSearchHistory(context);
        SharedPreferences sharedPref = context.getSharedPreferences("history", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        StringBuilder historyList = new StringBuilder();
        if (!history.contains(newItem)) {
            history.add(0, newItem);
        }
        int stop = history.size() > 5 ? 5 : history.size();
        for (int i = 0; i < stop; i++) {
            historyList.append(history.get(i));
            if (i != stop - 1) {
                historyList.append(',');
            }
        }

        editor.putString("history", historyList.toString());
        editor.commit();
        query = newItem;
    }

    public List<String> getSearchHistory(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("history", Context.MODE_PRIVATE);
        String historyList = sharedPreferences.getString("history", null);
        if (historyList == null) return new ArrayList<>();
        String[] items = historyList.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            list.add(items[i]);
        }
        return list;
    }

    private void notifyDataSetChanged(ListType listType) {
        switch (listType) {
            case headwordList:
                headwordListAdapter.notifyDataSetChanged();
                break;
            case headwordBasicSearchList:
                headwordBasicSearchListAdapter.notifyDataSetChanged();
                break;
            case headwordFullTextSearchList:
                headwordFullTextSearchListAdapter.notifyDataSetChanged();
                break;
        }
    }

    private List<String> getCheckedDictionariesAsList() {
        List<String> dictionaries = new ArrayList<>();
        if (checkedDictionaries != null) {
            for (int i = 0; i < checkedDictionaries.length; i++) {
                if (checkedDictionaries[i]) {
                    dictionaries.add(dictionaryList.get(i).getId());
                }
            }
        }
        return dictionaries;
    }


}
