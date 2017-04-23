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

    /**
     * <s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/">
     * <s:Body>
     * <SearchHeadwordByCriteria xmlns="http://tempuri.org/">
     * <searchCriterias xmlns:a="http://schemas.datacontract.org/2004/07/ITJakub.Shared.Contracts.Searching.Criteria" xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
     * <p>
     * <a:SearchCriteriaContract  i:type="a:ResultCriteriaContract">
     * <a:Count>10</a:Count>
     * <a:Start>0</a:Start>
     * <a:Direction>Ascending</a:Direction>
     * </a:SearchCriteriaContract>
     * <p>
     * <a:SearchCriteriaContract i:type="a:WordListCriteriaContract">
     * <a:Key>Headword</a:Key>
     * <a:Disjunctions>
     * <a:WordCriteriaContract>
     * <a:Contains xmlns:b="http://schemas.microsoft.com/2003/10/Serialization/Arrays">
     * <b:string>pes</b:string>
     * </a:Contains>
     * </a:WordCriteriaContract>
     * </a:Disjunctions>
     * </a:SearchCriteriaContract>
     * <p>
     * <a:SearchCriteriaContract i:type="a:SelectedCategoryCriteriaContract">
     * <a:SelectedBookIds xmlns:b="http://schemas.microsoft.com/2003/10/Serialization/Arrays">
     * <b:long>294</b:long>
     * </a:SelectedBookIds>
     * <a:SelectedCategoryIds i:nil="true" xmlns:b="http://schemas.microsoft.com/2003/10/Serialization/Arrays"/>
     * </a:SearchCriteriaContract>
     * <p>
     * </searchCriterias>
     * <searchTarget>Headword</searchTarget>
     * </SearchHeadwordByCriteria>
     * </s:Body>
     * </s:Envelope>
     *
     * @param parentNode
     * @param nodes
     * @param subParent
     * @param subNodes
     */
/*
    public Envelope(Map<String, String> resultCriterias, String word, Map<String, String> selectedCategoryCriterias){
        declarePrefix("tem", TEM_NAMESPACE);
        declarePrefix("itj", ITJ_NAMESPACE);
        declarePrefix("i", ATTRIBUTE_NAMESPACE);
        declarePrefix("arr", ARR_NAMESPACE);
        XMLParentNode node = getBody().addNode(TEM_NAMESPACE, "SearchHeadwordByCriteria");
        XMLParentNode searchCriterias = node.addNode(TEM_NAMESPACE, "searchCriterias");

        XMLParentNode resultCrit = searchCriterias.addNode(ITJ_NAMESPACE, "SearchCriteriaContract");
        resultCrit.addAttribute(ATTRIBUTE_NAMESPACE, "type", "itj:ResultCriteriaContract");
        for (Map.Entry<String, String> resultCriteria : resultCriterias.entrySet()) {
            resultCrit.addTextNode(ITJ_NAMESPACE, resultCriteria.getKey(), resultCriteria.getValue());

        }

        XMLParentNode wordListCrit = searchCriterias.addNode(ITJ_NAMESPACE, "SearchCriteriaContract");
        wordListCrit.addAttribute(ATTRIBUTE_NAMESPACE, "type", "itj:WordListCriteriaContract");
        wordListCrit.addTextNode(ITJ_NAMESPACE, "key", "Headword");
        XMLParentNode disjunctions = wordListCrit.addNode(ITJ_NAMESPACE, "Disjunctions");
        XMLParentNode wordCriteriaContract = disjunctions.addNode(ITJ_NAMESPACE, "WordCriteriaContract");
        XMLParentNode contains = wordCriteriaContract.addNode(ITJ_NAMESPACE, "Contains");
        contains.addTextNode(ARR_NAMESPACE, "string", word);


        XMLParentNode selectedCategoryCrit = searchCriterias.addNode(ITJ_NAMESPACE, "SearchCriteriaContract");
        selectedCategoryCrit.addAttribute(ATTRIBUTE_NAMESPACE, "type", "itj:SelectedCategoryCriteriaContract");
        for (Map.Entry<String, String> selectedCategoryCriteria : selectedCategoryCriterias.entrySet()) {
            selectedCategoryCrit.addTextNode(ITJ_NAMESPACE, selectedCategoryCriteria.getValue(), selectedCategoryCriteria.getKey());

        }
        node.addTextNode(TEM_NAMESPACE, "searchTarget", "Headword");
    }*/
    public Envelope(Map<String, String> resultCriterias, String word, Map<String, String> selectedCategoryCriterias, boolean isFullText) {
        declarePrefix("tem", TEM_NAMESPACE);
        declarePrefix("itj", ITJ_NAMESPACE);
        declarePrefix("i", ATTRIBUTE_NAMESPACE);
        declarePrefix("arr", ARR_NAMESPACE);
        String key, target;
        if(isFullText){
            key = "HeadwordDescription";
            target = "Fulltext";
        }else{
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
            for (Map.Entry<String, String> selectedCategoryCriteria : selectedCategoryCriterias.entrySet()) {
                selectedCategoryCrit.addTextNode(ITJ_NAMESPACE, selectedCategoryCriteria.getValue(), selectedCategoryCriteria.getKey());

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
