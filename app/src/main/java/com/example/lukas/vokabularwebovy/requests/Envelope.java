package com.example.lukas.vokabularwebovy.requests;

import com.alexgilleran.icesoap.envelope.impl.BaseSOAP11Envelope;
import com.alexgilleran.icesoap.xml.XMLParentNode;

import java.util.Map;

/**
 * Created by lukas on 14.03.2017.
 */
public class Envelope extends BaseSOAP11Envelope {
    private final String TEM_NAMESPACE= "http://tempuri.org/";
    
    public Envelope(String parentNode, Map<String, String> nodes){
        declarePrefix("tem", TEM_NAMESPACE);
        XMLParentNode node = getBody().addNode(TEM_NAMESPACE, parentNode);
        for (Map.Entry<String, String> entry : nodes.entrySet()) {
            node.addTextNode(TEM_NAMESPACE, entry.getKey(), entry.getValue());
        }

    }
}
