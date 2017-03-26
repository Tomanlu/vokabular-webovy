package com.example.lukas.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.Request;

/**
 * Created by lukas on 20.03.2017.
 */
public class DictionaryImageResponseObserver implements SOAP11Observer {
  /*  private String headword;
    private String bookXmlId;
    private String imgId;
    private DataProvider dataProvider;*/

    private Headword headword;
   /*
    public DictionaryImageResponseObserver(String headword, String bookXmlId, String imgId) {
        this.headword = headword;
        this.bookXmlId = bookXmlId;
        this.imgId = imgId;
        this.dataProvider = DataProvider.getInstance();
    }*/

    public DictionaryImageResponseObserver(Headword headword) {
        this.headword = headword;
    }

    @Override
    public void onCompletion(Request request) {
        HeadwordImage entry = (HeadwordImage) request.getResult();
        byte[] decodedByte = Base64.decode(entry.getImage(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        headword.setImg(bitmap);
    }

    @Override
    public void onException(Request request, SOAPException e) {
    }
}