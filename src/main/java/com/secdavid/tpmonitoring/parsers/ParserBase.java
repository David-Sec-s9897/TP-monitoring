package com.secdavid.tpmonitoring.parsers;

import com.secdavid.tpmonitoring.enums.Category;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserBase {

    public static final Logger LOGGER = Logger.getLogger(ParserBase.class.getName());

    static String getElementValue(Element parentElement, String tagName) {
        if (parentElement != null) {
            NodeList nodeList = parentElement.getElementsByTagName(tagName);
            if (nodeList.getLength() > 0) {
                return nodeList.item(0).getTextContent();
            } else {
                return "0";
            }
        }
        LOGGER.log(Level.SEVERE, "parentElement is null");
        return "0";

    }

    static List<String> getElementsValueAsList(Element parentElement, String tagName) {
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            list.add(nodeList.item(i).getTextContent());
        }
        return list;
    }

    static Category getCategory(Element parentElement, String category) {
        NodeList nodeList = parentElement.getElementsByTagName(category);

        switch (nodeList.item(0).getTextContent()) {
            case "Balancing":
                return Category.BALANCING;
            case "Congestion Management":
                return Category.CONGESTION_MANAGEMENT;
            case "Transmission":
                return Category.TRANSMISSION;
            case "Generation":
                return Category.GENERATION;
            case "FinalNomination":
                return Category.FINAL_NOMINATION;
            case "Load":
                return Category.LOAD;
            case "Outage":
                return Category.OUTAGE;
            case "Congestion":
                return Category.CONGESTION;
            default:
                return Category.UNKNOWN;
        }
    }

    static void handleNoParentElement(Element periodTimeInterval) {
        String code = getElementValue(periodTimeInterval, "code");
        String text = getElementValue(periodTimeInterval, "text");
        LOGGER.log(Level.SEVERE, "Code: " + code + " Text: " + text);
    }
}
