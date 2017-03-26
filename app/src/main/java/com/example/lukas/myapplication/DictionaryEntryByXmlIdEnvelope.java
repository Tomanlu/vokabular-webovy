package com.example.lukas.myapplication;

import com.alexgilleran.icesoap.envelope.impl.BaseSOAP11Envelope;
import com.alexgilleran.icesoap.xml.XMLParentNode;

/**
 * Created by lukas on 14.03.2017.
 */
public class DictionaryEntryByXmlIdEnvelope extends BaseSOAP11Envelope {
    private final String TEM_NAMESPACE= "http://tempuri.org/";

    public DictionaryEntryByXmlIdEnvelope() {
        declarePrefix("tem", TEM_NAMESPACE);
        XMLParentNode node = getBody().addNode(TEM_NAMESPACE, "GetDictionaryEntryByXmlId");
        node.addTextNode(TEM_NAMESPACE, "bookGuid", "0");
        node.addTextNode(TEM_NAMESPACE, "xmlEntryId", "20");
        node.addTextNode(TEM_NAMESPACE, "resultFormat", "Dictionary");
        node.addTextNode(TEM_NAMESPACE, "bookType", "Dictionary");
    }
}
