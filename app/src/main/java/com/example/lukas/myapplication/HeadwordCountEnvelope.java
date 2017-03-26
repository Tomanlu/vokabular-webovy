package com.example.lukas.myapplication;

import com.alexgilleran.icesoap.envelope.impl.BaseSOAP11Envelope;
import com.alexgilleran.icesoap.xml.XMLParentNode;

/**
 * Created by lukas on 16.12.2016.
 */
public class HeadwordCountEnvelope extends BaseSOAP11Envelope {
    private final String TEM_NAMESPACE= "http://tempuri.org/";

    public HeadwordCountEnvelope() {
        declarePrefix("tem", TEM_NAMESPACE);
        XMLParentNode node = getBody().addNode(TEM_NAMESPACE, "GetHeadwordCount");
        node.addTextNode(TEM_NAMESPACE, "bookType", "Dictionary");
    }
}
