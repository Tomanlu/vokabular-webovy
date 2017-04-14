package com.example.lukas.vokabularwebovy.requests;

import com.alexgilleran.icesoap.envelope.impl.BaseSOAP11Envelope;
import com.alexgilleran.icesoap.xml.XMLParentNode;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lukas on 14.03.2017.
 */
public class Envelope extends BaseSOAP11Envelope {
    private final String TEM_NAMESPACE = "http://tempuri.org/";
    private final String ARR_NAMESPACE = "http://schemas.microsoft.com/2003/10/Serialization/Arrays";

    public Envelope(String parentNode, Map<String, String> nodes) {
        declarePrefix("tem", TEM_NAMESPACE);
        XMLParentNode node = getBody().addNode(TEM_NAMESPACE, parentNode);
        for (Map.Entry<String, String> entry : nodes.entrySet()) {
            node.addTextNode(TEM_NAMESPACE, entry.getKey(), entry.getValue());
        }

    }

    public Envelope(String parentNode, Map<String, String> nodes, String subParent, Map<String, String> subNodes) {
        declarePrefix("tem", TEM_NAMESPACE);
        declarePrefix("arr", ARR_NAMESPACE);
        XMLParentNode node = getBody().addNode(TEM_NAMESPACE, parentNode);
        if (subNodes.size() != 0) {
            XMLParentNode subParentNode = node.addNode(TEM_NAMESPACE, subParent);
            for (Map.Entry<String, String> entry : subNodes.entrySet()) {
                subParentNode.addTextNode(ARR_NAMESPACE, entry.getValue(), entry.getKey());

            }
        }
        for (Map.Entry<String, String> entry : nodes.entrySet()) {
            node.addTextNode(TEM_NAMESPACE, entry.getKey(), entry.getValue());
        }


    }
}
