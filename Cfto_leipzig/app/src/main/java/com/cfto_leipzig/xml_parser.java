package com.cfto_leipzig;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Lina on 23/04/16.
 */
public class xml_parser {

    public static void parse_hash() {


        HashMap<String,Integer> hmap = new HashMap<String, Integer>();

        try {

            File fXmlFile = new File("/Users/Lina/StudioProjects/cfto_leipzig/Cfto_leipzig/app/src/main/res/xml/impactRules.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("rule");

            //System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                //System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    int impactI = Integer.parseInt(eElement.getAttribute("impact"));

                    hmap.put(eElement.getAttribute("des"), impactI);

                    //System.out.println("What : " + eElement.getAttribute("des"));
                    //System.out.println("Impact : " + eElement.getAttribute("impact"));


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //int i=0;
        for (String key : hmap.keySet()) {
            System.out.println(key + " " + hmap.get(key));
            //System.out.println(i++);
        }
        //return hmap;
        logic.logi(hmap);

    }

}
