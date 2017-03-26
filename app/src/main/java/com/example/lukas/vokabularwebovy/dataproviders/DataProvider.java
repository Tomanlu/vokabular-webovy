package com.example.lukas.vokabularwebovy.dataproviders;

import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.RequestFactory;
import com.alexgilleran.icesoap.request.SOAP11Request;
import com.alexgilleran.icesoap.request.impl.RequestFactoryImpl;
import com.example.lukas.vokabularwebovy.adapters.Adapter;
import com.example.lukas.vokabularwebovy.models.*;
import com.example.lukas.vokabularwebovy.observers.DictionaryEntryResponseObserver;
import com.example.lukas.vokabularwebovy.observers.DictionaryImageResponseObserver;
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
    private List<Headword> dataList;
    private CacheDataProvider<String, String> listEntryCache;

    Adapter adapter;
    private DataProvider(){
        dataList = new ArrayList<>();
        requestFactory = new RequestFactoryImpl();
        listEntryCache = new CacheDataProvider<>();
    }

    public static DataProvider getInstance(){
        if(instance == null) instance = new DataProvider();
        return instance;
    }

    public void getHeadwordCount(SOAP11Observer observer){
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
    public void GetHeadwordList(SOAP11Observer observer, int start, int count){
        Map<String, String> nodes = new LinkedHashMap<>();
        nodes.put("start", Integer.toString(start));
        nodes.put("count", Integer.toString(count));
        nodes.put("bookType", "Dictionary");
        Envelope en = new Envelope("GetHeadwordList", nodes);
        SOAP11Request<HeadwordList> definitionRequest = requestFactory.buildRequest(
                URL,
                en,
                "http://tempuri.org/IItJakubService/GetHeadwordList",
                HeadwordList.class);
        definitionRequest.registerObserver(observer);
        definitionRequest.execute();
    }
    public void GetHeadwordImage(Headword headword){
        String entry = listEntryCache.getItem(headword.getBookXmlId()+headword.getImageName());
        DictionaryImageResponseObserver observer = new DictionaryImageResponseObserver(headword);
        if( entry != null){
            DictionaryImageResponseObserver.BitmapWorkerTask task = observer.getTask();
            task.execute(entry);
        }
        else {
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
    public void GetDictionaryEntryByXmlId(Headword headword){
        String entry = listEntryCache.getItem(headword.getBookXmlId()+headword.getEntryXmlId());
        if(entry != null){
            headword.setEntry(entry);
        }else {
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
    public void addToCache(String key, String value){
        listEntryCache.addItem(key, value);
    }
    public Headword GetHeadwordFromList(int position){
        return dataList.get(position);
    }
    public int GetSizeOfList(){
        return dataList.size();
    }
    public void AddToList(List<Headword> list){
        dataList.addAll(list);
        adapter.notifyDataSetChanged();
    }
    public void AddToList(Headword headword){
        dataList.add(headword);
        adapter.notifyDataSetChanged();
    }
    public void ClearList(){dataList.clear();}

    public void setAdapter(Adapter adapter){
        this.adapter = adapter;
    }
}
