package com.example.lukas.vokabularwebovy.dataproviders;

import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.RequestFactory;
import com.alexgilleran.icesoap.request.SOAP11Request;
import com.alexgilleran.icesoap.request.impl.RequestFactoryImpl;
import com.example.lukas.vokabularwebovy.models.*;
import com.example.lukas.vokabularwebovy.observers.DictionariesWithCategoriesResponseObserver;
import com.example.lukas.vokabularwebovy.observers.DictionaryEntryFromSearchResponseObserver;
import com.example.lukas.vokabularwebovy.observers.DictionaryEntryResponseObserver;
import com.example.lukas.vokabularwebovy.observers.DictionaryImageResponseObserver;
import com.example.lukas.vokabularwebovy.requests.Envelope;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lukas on 26.03.2017.
 */
public class ServerDataProvider {
    private static ServerDataProvider instance = null;
    private final String URL = "http://censeo.felk.cvut.cz/ITJakub.ITJakubService/ItJakubService.svc";
    private RequestFactory requestFactory;

    public static ServerDataProvider getInstance() {
        if (instance == null) instance = new ServerDataProvider();
        return instance;
    }

    private ServerDataProvider() {
        requestFactory = new RequestFactoryImpl();
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

    public void getHeadwordRowNumber(SOAP11Observer observer, String query) {
        Map<String, String> nodes = new LinkedHashMap<>();
        nodes.put("query", query);
        nodes.put("bookType", "Dictionary");
        Envelope en = new Envelope("GetHeadwordRowNumber", nodes);
        SOAP11Request<HeadwordRowNumber> definitionRequest = requestFactory.buildRequest(
                URL,
                en,
                "http://tempuri.org/IItJakubService/GetHeadwordRowNumber",
                HeadwordRowNumber.class);
        definitionRequest.registerObserver(observer);
        definitionRequest.execute();

    }

    public void searchHeadwordByCriteria(SOAP11Observer observer, int start, int count, String query, boolean isFullText, List<String> dictionaryList) {
        Map<String, String> resultCriterias = new LinkedHashMap<>();
        Map<String, String> selectedCategoriesCriterias = new LinkedHashMap<>();
        resultCriterias.put("Count", Integer.toString(count));
        resultCriterias.put("Start", Integer.toString(start));
        resultCriterias.put("Direction", "Ascending");
        for (String id : dictionaryList) {
            selectedCategoriesCriterias.put(id, "long");
        }
        Envelope en = new Envelope(resultCriterias, query, selectedCategoriesCriterias, isFullText);
        SOAP11Request<HeadwordList> definitionRequest = requestFactory.buildRequest(
                URL,
                en,
                "http://tempuri.org/IItJakubService/SearchHeadwordByCriteria",
                HeadwordList.class);
        definitionRequest.registerObserver(observer);
        definitionRequest.execute();
    }

    public void getHeadwordList(SOAP11Observer observer, int start, int count, List<String> dictionaryList) {
        Map<String, String> nodes = new LinkedHashMap<>();
        Map<String, String> selectedCategoriesCriterias = new LinkedHashMap<>();
        for (String id : dictionaryList) {
            selectedCategoriesCriterias.put(id, "long");
        }
        nodes.put("start", Integer.toString(start));
        nodes.put("count", Integer.toString(count));
        nodes.put("bookType", "Dictionary");
        Envelope en = new Envelope("GetHeadwordList", nodes, "selectedBookIds", selectedCategoriesCriterias);
        SOAP11Request<HeadwordList> definitionRequest = requestFactory.buildRequest(
                URL,
                en,
                "http://tempuri.org/IItJakubService/GetHeadwordList",
                HeadwordList.class);
        definitionRequest.registerObserver(observer);
        definitionRequest.execute();
    }

    public void getHeadwordImageByXmlId(SOAP11Observer observer, Headword headword) {
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

    public void getDictionaryEntryByXmlId(Headword headword) {
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

    public void getDictionaryEntryFromSearch(Headword headword, String query) {
        Map<String, String> nodes = new LinkedHashMap<>();
        nodes.put("bookGuid", headword.getBookXmlId());
        nodes.put("xmlEntryId", headword.getEntryXmlId());
        nodes.put("resultFormat", "Html");
        nodes.put("bookType", "Dictionary");
        Envelope en = new Envelope("GetDictionaryEntryFromSearch", nodes, query);
        SOAP11Request<DictionaryEntryFromSearch> definitionRequest = requestFactory.buildRequest(
                URL,
                en,
                "http://tempuri.org/IItJakubService/GetDictionaryEntryFromSearch",
                DictionaryEntryFromSearch.class);
        DictionaryEntryFromSearchResponseObserver observer = new DictionaryEntryFromSearchResponseObserver(headword);
        definitionRequest.registerObserver(observer);
        definitionRequest.execute();
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
}

