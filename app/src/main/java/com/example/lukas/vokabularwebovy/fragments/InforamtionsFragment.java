package com.example.lukas.vokabularwebovy.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import com.example.lukas.vokabularwebovy.R;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by lukas on 29.03.2017.
 */
public class InforamtionsFragment extends Fragment {
    public WebView webView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.informations_fragment, container, false);
        webView = (WebView) view.findViewById(R.id.informations_webview);
        if(webView == null) return view;

        GetDocumentTask task = new GetDocumentTask();
        task.execute();

        return  view;
    }
private class GetDocumentTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void...params) {
        Element content = null;
        try {
            Document doc = Jsoup.connect("http://censeo.felk.cvut.cz/Dictionaries/Dictionaries/Information").get();
            doc.select("a").attr("href", "#");
            content = doc.getElementsByClass("module-content").first();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.html();
    }
    @Override
    protected void onPostExecute(String data){
        webView.loadData(data, "text/html; charset=UTF-8", null);
    }
}
}
