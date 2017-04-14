package com.example.lukas.vokabularwebovy.observers;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.Request;
import com.example.lukas.vokabularwebovy.adapters.HintsAdapter;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.models.HeadwordRowNumber;
import com.example.lukas.vokabularwebovy.models.TypeheadHeadwords;

import java.lang.reflect.Type;

/**
 * Created by lukas on 29.03.2017.
 */
public class TypeheadHeadwordsObserver  implements SOAP11Observer {
    HintsAdapter adapter;
    public TypeheadHeadwordsObserver(HintsAdapter adapter){this.adapter = adapter;}
    @Override
    public void onCompletion(Request request) {
        TypeheadHeadwords typeheadHeadwords = (TypeheadHeadwords) request.getResult();
        if(typeheadHeadwords != null && typeheadHeadwords.getHints() != null && typeheadHeadwords.getHints().size() > 0){
                adapter.clear();
            adapter.addAll(typeheadHeadwords.getHints());
        }


    }



    @Override
    public void onException(Request request, SOAPException e) {

    }
}
