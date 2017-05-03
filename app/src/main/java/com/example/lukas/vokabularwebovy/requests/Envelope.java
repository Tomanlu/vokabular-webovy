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
    private final String ITJ_NAMESPACE = "http://schemas.datacontract.org/2004/07/ITJakub.Shared.Contracts.Searching.Criteria";
    private final String ATTRIBUTE_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";

    public Envelope(String parentNode, Map<String, String> nodes) {
        declarePrefix("tem", TEM_NAMESPACE);
        XMLParentNode node = getBody().addNode(TEM_NAMESPACE, parentNode);
        for (Map.Entry<String, String> entry : nodes.entrySet()) {
            node.addTextNode(TEM_NAMESPACE, entry.getKey(), entry.getValue());
        }

    }

    public Envelope(Map<String, String> resultCriterias, String word, Map<String, String> selectedCategoryCriterias, boolean isFullText) {
        declarePrefix("tem", TEM_NAMESPACE);
        declarePrefix("itj", ITJ_NAMESPACE);
        declarePrefix("i", ATTRIBUTE_NAMESPACE);
        declarePrefix("arr", ARR_NAMESPACE);
        String key, target;
        if (isFullText) {
            key = "HeadwordDescription";
            target = "Fulltext";
        } else {
            key = "Headword";
            target = "Headword";
        }

        XMLParentNode node = getBody().addNode(TEM_NAMESPACE, "SearchHeadwordByCriteria");
        XMLParentNode searchCriterias = node.addNode(TEM_NAMESPACE, "searchCriterias");

        XMLParentNode resultCrit = searchCriterias.addNode(ITJ_NAMESPACE, "SearchCriteriaContract");
        resultCrit.addAttribute(ATTRIBUTE_NAMESPACE, "type", "itj:ResultCriteriaContract");
        for (Map.Entry<String, String> resultCriteria : resultCriterias.entrySet()) {
            resultCrit.addTextNode(ITJ_NAMESPACE, resultCriteria.getKey(), resultCriteria.getValue());

        }

        XMLParentNode wordListCrit = searchCriterias.addNode(ITJ_NAMESPACE, "SearchCriteriaContract");
        wordListCrit.addAttribute(ATTRIBUTE_NAMESPACE, "type", "itj:WordListCriteriaContract");

        wordListCrit.addTextNode(ITJ_NAMESPACE, "Key", key);
        XMLParentNode disjunctions = wordListCrit.addNode(ITJ_NAMESPACE, "Disjunctions");
        XMLParentNode wordCriteriaContract = disjunctions.addNode(ITJ_NAMESPACE, "WordCriteriaContract");
        XMLParentNode contains = wordCriteriaContract.addNode(ITJ_NAMESPACE, "Contains");
        contains.addTextNode(ARR_NAMESPACE, "string", word);

        if (!selectedCategoryCriterias.isEmpty()) {
            XMLParentNode selectedCategoryCrit = searchCriterias.addNode(ITJ_NAMESPACE, "SearchCriteriaContract");
            selectedCategoryCrit.addAttribute(ATTRIBUTE_NAMESPACE, "type", "itj:SelectedCategoryCriteriaContract");
            XMLParentNode selectedBookIds = selectedCategoryCrit.addNode(ITJ_NAMESPACE, "SelectedBookIds");
            for (Map.Entry<String, String> selectedCategoryCriteria : selectedCategoryCriterias.entrySet()) {
                selectedBookIds.addTextNode(ARR_NAMESPACE, selectedCategoryCriteria.getValue(), selectedCategoryCriteria.getKey());

            }
        }
        node.addTextNode(TEM_NAMESPACE, "searchTarget", target);
    }

    public Envelope(String parentNode, Map<String, String> nodes, String text) {
        declarePrefix("tem", TEM_NAMESPACE);
        declarePrefix("itj", ITJ_NAMESPACE);
        declarePrefix("i", ATTRIBUTE_NAMESPACE);
        declarePrefix("arr", ARR_NAMESPACE);
        XMLParentNode node = getBody().addNode(TEM_NAMESPACE, parentNode);
        XMLParentNode searchCriterias = node.addNode(TEM_NAMESPACE, "searchCriterias");
        XMLParentNode wordListCrit = searchCriterias.addNode(ITJ_NAMESPACE, "SearchCriteriaContract");
        wordListCrit.addAttribute(ATTRIBUTE_NAMESPACE, "type", "itj:WordListCriteriaContract");
        wordListCrit.addTextNode(ITJ_NAMESPACE, "Key", "HeadwordDescription");
        XMLParentNode disjunctions = wordListCrit.addNode(ITJ_NAMESPACE, "Disjunctions");
        XMLParentNode wordCriteriaContract = disjunctions.addNode(ITJ_NAMESPACE, "WordCriteriaContract");
        XMLParentNode contains = wordCriteriaContract.addNode(ITJ_NAMESPACE, "Contains");
        contains.addTextNode(ARR_NAMESPACE, "string", text);
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
