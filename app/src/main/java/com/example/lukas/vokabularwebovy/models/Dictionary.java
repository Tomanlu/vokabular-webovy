package com.example.lukas.vokabularwebovy.models;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;

/**
 * Created by lukas on 12.03.2017.
 */
@XMLObject("//KeyValueOfstringDictionaryContractogreX8G6")
public class Dictionary {
    @XMLField("Key")
    private String key;

    @XMLField("Value/BookAcronym")
    private String bookAcronym;

    @XMLField("Value/BookId")
    private String bookId;

    @XMLField("Value/BookTitle")
    private String bookTitle;

    @XMLField("Value/BookVersionId")
    private String bookVersionId;

    @XMLField("Value/BookVersionXmlId")
    private String bookVersionXmlId;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBookAcronym() {
        return bookAcronym;
    }

    public void setBookAcronym(String bookAcronym) {
        this.bookAcronym = bookAcronym;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookVersionId() {
        return bookVersionId;
    }

    public void setBookVersionId(String bookVersionId) {
        this.bookVersionId = bookVersionId;
    }

    public String getBookVersionXmlId() {
        return bookVersionXmlId;
    }

    public void setBookVersionXmlld(String bookVersionXmlId) {
        this.bookVersionXmlId = bookVersionXmlId;
    }
}
