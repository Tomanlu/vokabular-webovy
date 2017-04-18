package com.example.lukas.vokabularwebovy.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

/**
 * Created by lukas on 21.03.2017.
 */
public class Headword extends BaseObservable {

    private String headword;
    private String bookXmlId;
    private String bookName;
    private String entryXmlId;
    private String imageName;
    private Bitmap img;
    private Spanned entry;
    private boolean isImage;
    private boolean isReady;
    private boolean isInView;

    public Headword(String headword, String bookXmlId, String bookName, String entryXmlId, String image) {
        this.headword = headword;
        this.bookXmlId = bookXmlId;
        this.entryXmlId = entryXmlId;
        this.bookName = bookName;
        this.imageName = image;
        this.isImage = (image != null);
        this.img = null;
        this.entry = null;
    }
    @Bindable
    public String getHeadword() {
        return headword;
    }

    public void setHeadword(String headword) {
        this.headword = headword;
        notifyChange();
    }

    public String getBookXmlId() {
        return bookXmlId;
    }

    public void setBookXmlId(String bookXmlId) {
        this.bookXmlId = bookXmlId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getEntryXmlId() {
        return entryXmlId;
    }

    public void setEntryXmlId(String entryXmlId) {
        this.entryXmlId = entryXmlId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String image) {
        this.imageName = image;
        this.isImage = true;
        notifyChange();
    }
    @Bindable
    public Spanned getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.entry = Html.fromHtml(entry, Html.FROM_HTML_MODE_COMPACT);
        } else {
            this.entry = Html.fromHtml(entry);
        }
        isReady = true;
        notifyChange();
    }
    @Bindable
    public boolean isImage() {
        return isImage;
    }

    @Bindable
    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
        isReady = true;
        notifyChange();
    }

    @Bindable
    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
        notifyChange();
    }

    public boolean isInView() {
        return isInView;
    }

    public void setInView(boolean inView) {
        isInView = inView;
    }
}
