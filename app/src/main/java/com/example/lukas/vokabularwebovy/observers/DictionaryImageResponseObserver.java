package com.example.lukas.vokabularwebovy.observers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.Request;
import com.example.lukas.vokabularwebovy.dataproviders.DataProvider;
import com.example.lukas.vokabularwebovy.models.Headword;
import com.example.lukas.vokabularwebovy.models.HeadwordImage;

/**
 * Created by lukas on 20.03.2017.
 */
public class DictionaryImageResponseObserver implements SOAP11Observer {

    private Headword headword;

    public DictionaryImageResponseObserver(Headword headword) {
        this.headword = headword;
    }

    @Override
    public void onCompletion(Request request) {
        HeadwordImage entry = (HeadwordImage) request.getResult();
        BitmapWorkerTask task = getTask();
        task.execute(entry.getImage());
        DataProvider.getInstance().addToCache(headword.getBookXmlId() + headword.getImageName(),entry.getImage());
    }

    @Override
    public void onException(Request request, SOAPException e) {
    }
    public BitmapWorkerTask getTask(){
        return new BitmapWorkerTask();
    }
    public class BitmapWorkerTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... headwordImages) {
            byte[] decodedByte = Base64.decode(headwordImages[0], Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
            headword.setImg(bitmap);
            return null;
        }
    }
}