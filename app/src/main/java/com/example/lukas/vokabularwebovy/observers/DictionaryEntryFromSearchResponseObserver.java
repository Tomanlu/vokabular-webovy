package com.example.lukas.vokabularwebovy.observers;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.Request;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.models.DictionaryEntryByXmlId;
import com.example.lukas.vokabularwebovy.models.DictionaryEntryFromSearch;
import com.example.lukas.vokabularwebovy.models.Headword;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by lukas on 23.04.2017.
 */
public class DictionaryEntryFromSearchResponseObserver implements SOAP11Observer {
    Headword headword;


    public DictionaryEntryFromSearchResponseObserver(Headword headword) {
        this.headword = headword;
    }

    @Override
    public void onCompletion(Request request) {
        DictionaryEntryFromSearch entry = (DictionaryEntryFromSearch) request.getResult();
        Document doc = Jsoup.parse(entry.getDictionaryEntry());
        Elements elements = doc.getElementsByClass("reader-search-result-match");
        for (Element element : elements) {
            Element newElement = new Element("font");
            newElement.attr("color", "red");
            newElement.text(element.text());
            element.replaceWith(newElement);
        }
        if(headword.isInView())
            headword.setEntry(doc.html());
        DataProvider.getInstance().addToCache(headword.getBookXmlId() + headword.getEntryXmlId(),entry.getDictionaryEntry());

    }

    @Override
    public void onException(Request request, SOAPException e) {
    }
}
