package com.example.lukas.vokabularwebovy.dataproviders;

/**
 * Created by lukas on 26.03.2017.
 */
public class ServerDataProvider {
    private static ServerDataProvider instance = null;

    public static ServerDataProvider getInstance() {
        if(instance == null) instance = new ServerDataProvider();
        return instance;
    }

    private ServerDataProvider() {
    }
}
