package com.example.lukas.vokabularwebovy.dataproviders;

import android.content.Context;
import android.content.SharedPreferences;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.RequestFactory;
import com.alexgilleran.icesoap.request.SOAP11Request;
import com.alexgilleran.icesoap.request.impl.RequestFactoryImpl;
import com.example.lukas.vokabularwebovy.adapters.ListingAdapter;
import com.example.lukas.vokabularwebovy.fragments.DictionariesFragmentDialogFragment;
import com.example.lukas.vokabularwebovy.listeners.EndlessRecyclerViewScrollListener;
import com.example.lukas.vokabularwebovy.models.*;
import com.example.lukas.vokabularwebovy.observers.*;
import com.example.lukas.vokabularwebovy.requests.Envelope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lukas on 14.03.2017.
 */
public final class DataProvider {
    private final String URL = "http://censeo.felk.cvut.cz/ITJakub.ITJakubService/ItJakubService.svc";
    private static DataProvider instance = null;
    private RequestFactory requestFactory;
    private List<Headword> headwordList;
    private List<DictionaryWithCategories> dictionaryList;
    private CacheDataProvider<String, String> listEntryCache;
    private boolean[] checkedDictionaries;
    private String searchText;
    ListingAdapter adapter;

    private DataProvider() {
        headwordList = new ArrayList<>();
        dictionaryList = new ArrayList<>();
        requestFactory = new RequestFactoryImpl();
        listEntryCache = new CacheDataProvider<>();
    }

    public static DataProvider getInstance() {
        if (instance == null) instance = new DataProvider();
        return instance;
    }

    public void getDictionariesWithCategories() {
        Map<String, String> nodes = new LinkedHashMap<>();
        nodes.put("bookType", "Dictionary");
        Envelope en = new Envelope("GetBooksWithCategoriesByBookType", nodes);
        SOAP11Request<DictionariesWithCategories> definitionRequest = requestFactory.buildRequest(
                URL,
                en,
                "http://tempuri.org/IItJakubService/GetBooksWithCategoriesByBookType",
                DictionariesWithCategories.class);
        definitionRequest.registerObserver(new DictionariesWithCategoriesResponseObserver());
        definitionRequest.execute();
    }

    public void getHeadwordCount(SOAP11Observer observer) {
        Map<String, String> nodes = new LinkedHashMap<>();
        nodes.put("bookType", "Dictionary");
        Envelope en = new Envelope("GetHeadwordCount", nodes);
        SOAP11Request<HeadwordCount> definitionRequest = requestFactory.buildRequest(
                URL,
                en,
                "http://tempuri.org/IItJakubService/GetHeadwordCount",
                HeadwordCount.class);
        definitionRequest.registerObserver(observer);
        definitionRequest.execute();
    }

    public void getHeadwordRowNumber(EndlessRecyclerViewScrollListener scrollListener, String query) {
        Map<String, String> nodes = new LinkedHashMap<>();
        nodes.put("query", query);
        nodes.put("bookType", "Dictionary");
        Envelope en = new Envelope("GetHeadwordRowNumber", nodes);
        SOAP11Request<HeadwordRowNumber> definitionRequest = requestFactory.buildRequest(
                URL,
                en,
                "http://tempuri.org/IItJakubService/GetHeadwordRowNumber",
                HeadwordRowNumber.class);
        definitionRequest.registerObserver(new HeadwordRowEntryResponseObserver(adapter, scrollListener));
        definitionRequest.execute();

    }

    public void searchHeadwordByCriteria(SOAP11Observer observer, int start, int count, boolean isFullText) {
        Map<String, String> resultCriterias = new LinkedHashMap<>();

        Map<String, String> selectedCategoriesCriterias = new LinkedHashMap<>();
        resultCriterias.put("Count", Integer.toString(count));
        resultCriterias.put("Start", Integer.toString(start));
        resultCriterias.put("Direction", "Ascending");

        if (checkedDictionaries != null) {
            for (int i = 0; i < checkedDictionaries.length; i++) {
                if (checkedDictionaries[i]) {
                    selectedCategoriesCriterias.put(dictionaryList.get(i).getId(), "long");
                }
            }
        }
        Envelope en = new Envelope(resultCriterias, searchText, selectedCategoriesCriterias, isFullText);
        SOAP11Request<HeadwordList> definitionRequest = requestFactory.buildRequest(
                URL,
                en,
                "http://tempuri.org/IItJakubService/SearchHeadwordByCriteria",
                HeadwordList.class);
        definitionRequest.registerObserver(observer);
        definitionRequest.execute();
    }

    public void getHeadwordList(SOAP11Observer observer, int start, int count) {
        Map<String, String> nodes = new LinkedHashMap<>();
        Map<String, String> subNodes = new LinkedHashMap<>();
        if (checkedDictionaries != null) {
            for (int i = 0; i < checkedDictionaries.length; i++) {
                if (checkedDictionaries[i]) {
                    subNodes.put(dictionaryList.get(i).getId(), "long");
                }
            }
        }
        nodes.put("start", Integer.toString(start));
        nodes.put("count", Integer.toString(count));
        nodes.put("bookType", "Dictionary");
        Envelope en = new Envelope("GetHeadwordList", nodes, "selectedBookIds", subNodes);
        SOAP11Request<HeadwordList> definitionRequest = requestFactory.buildRequest(
                URL,
                en,
                "http://tempuri.org/IItJakubService/GetHeadwordList",
                HeadwordList.class);
        definitionRequest.registerObserver(observer);
        definitionRequest.execute();
    }

    public void getHeadwordImage(Headword headword) {
        String entry = listEntryCache.getItem(headword.getBookXmlId() + headword.getImageName());
        DictionaryImageResponseObserver observer = new DictionaryImageResponseObserver(headword);
        if (entry != null) {
            DictionaryImageResponseObserver.BitmapWorkerTask task = observer.getTask();
            task.execute(entry);
        } else {
            Map<String, String> nodes = new LinkedHashMap<>();
            nodes.put("bookXmlId", headword.getBookXmlId());
            nodes.put("fileName", headword.getImageName());
            Envelope en = new Envelope("GetHeadwordImage", nodes);
            SOAP11Request<HeadwordImage> definitionRequest = requestFactory.buildRequest(
                    URL,
                    en,
                    "http://tempuri.org/IItJakubService/GetHeadwordImage",
                    HeadwordImage.class);
            definitionRequest.registerObserver(observer);
            definitionRequest.execute();
        }

    }

    public void getDictionaryEntryByXmlId(Headword headword) {
        String entry = listEntryCache.getItem(headword.getBookXmlId() + headword.getEntryXmlId());
        if (entry != null) {
            headword.setEntry(entry);
        } else {
            Map<String, String> nodes = new LinkedHashMap<>();
            nodes.put("bookGuid", headword.getBookXmlId());
            nodes.put("xmlEntryId", headword.getEntryXmlId());
            nodes.put("resultFormat", "Html");
            nodes.put("bookType", "Dictionary");
            Envelope en = new Envelope("GetDictionaryEntryByXmlId", nodes);
            SOAP11Request<DictionaryEntryByXmlId> definitionRequest = requestFactory.buildRequest(
                    URL,
                    en,
                    "http://tempuri.org/IItJakubService/GetDictionaryEntryByXmlId",
                    DictionaryEntryByXmlId.class);
            DictionaryEntryResponseObserver observer = new DictionaryEntryResponseObserver(headword);
            definitionRequest.registerObserver(observer);
            definitionRequest.execute();
        }
    }

    public void getDictionaryEntryFromSearch(Headword headword, String text) {
        String entry = listEntryCache.getItem(headword.getBookXmlId() + headword.getEntryXmlId());
        if (entry != null) {
            headword.setEntry(entry);
        } else {
            Map<String, String> nodes = new LinkedHashMap<>();
            nodes.put("bookGuid", headword.getBookXmlId());
            nodes.put("xmlEntryId", headword.getEntryXmlId());
            nodes.put("resultFormat", "Html");
            nodes.put("bookType", "Dictionary");
            Envelope en = new Envelope("GetDictionaryEntryFromSearch", nodes, text);
            SOAP11Request<DictionaryEntryFromSearch> definitionRequest = requestFactory.buildRequest(
                    URL,
                    en,
                    "http://tempuri.org/IItJakubService/GetDictionaryEntryFromSearch",
                    DictionaryEntryFromSearch.class);
            DictionaryEntryFromSearchResponseObserver observer = new DictionaryEntryFromSearchResponseObserver(headword);
            definitionRequest.registerObserver(observer);
            definitionRequest.execute();
        }
    }

    public void getTypeheadHeadword(SOAP11Observer observer, String query) {
        Map<String, String> nodes = new LinkedHashMap<>();
        nodes.put("query", query);
        nodes.put("bookType", "Dictionary");
        Envelope en = new Envelope("GetTypeaheadDictionaryHeadwords", nodes);
        SOAP11Request<TypeheadHeadwords> definitionRequest = requestFactory.buildRequest(
                URL,
                en,
                "http://tempuri.org/IItJakubService/GetTypeaheadDictionaryHeadwords",
                TypeheadHeadwords.class);
        definitionRequest.registerObserver(observer);
        definitionRequest.execute();
    }

    public void addToCache(String key, String value) {
        listEntryCache.addItem(key, value);
    }

    public Headword getHeadwordFromList(int position) {
        return headwordList.get(position);
    }

    public int getSizeOfList() {
        return headwordList.size();
    }

    public void addAllToList(List<Headword> list, boolean addToTop) {
        if (addToTop) {
            List tmp = new ArrayList(headwordList);
            headwordList.clear();
            headwordList.addAll(list);
            headwordList.addAll(tmp);
        } else {
            headwordList.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    public void addAllToList(List<Headword> list) {
        headwordList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void addToList(Headword headword) {
        headwordList.add(headword);
        adapter.notifyDataSetChanged();
    }

    public void ClearList() {
        headwordList.clear();
    }

    public void setAdapter(ListingAdapter adapter) {
        this.adapter = adapter;
    }

    public String[] getDictionaryListAsStringArray() {
        String[] dictionariNames = new String[dictionaryList.size()];
        for (int i = 0; i < dictionaryList.size(); i++) {
            dictionariNames[i] = dictionaryList.get(i).getTitle();
        }

        return dictionariNames;

    }

    public void setDictionaryList(List<DictionaryWithCategories> dictionaryList) {
        this.dictionaryList = dictionaryList;
        this.checkedDictionaries = new boolean[dictionaryList.size()];

    }

    public void setCheckedDictionaries(boolean[] checkedDictionaries) {
        if(checkedDictionaries == null)return;
        this.checkedDictionaries = checkedDictionaries;
        headwordList.clear();
        adapter.notifyDataSetChanged();
        getHeadwordList(new HeadwordListResponseObserver(), 0, 20);
    }

    public int getDictionaryListSize() {
        return this.dictionaryList.size();
    }

    public boolean[] getCheckedDictionaries() {

        return this.checkedDictionaries;
    }

    public void addItemAndSaveSearchHistory(String newItem, Context context) {
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
        searchText = newItem;
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
}
