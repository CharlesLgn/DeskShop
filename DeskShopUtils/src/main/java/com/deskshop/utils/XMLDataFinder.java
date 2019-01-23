package com.deskshop.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;

public class XMLDataFinder {

    private static String pathPersonalData = "personal-data.xml";

    private static NodeList XPathFinder(String url, String expression) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(url);
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile(expression);
        return (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
    }

    private static String getPersonnalData(String expr, String casError) {
        try {
            NodeList res1 = XPathFinder(pathPersonalData, expr);
            return res1.item(0).getTextContent();
        } catch (ParserConfigurationException | IOException | SAXException | XPathExpressionException e) {
            createXML("", "white", "En", "");
            return casError;
        }
    }

    private static void createXML(String MAIL, String THEME, String LANGUAGE, String PASSWORD) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("user");
            doc.appendChild(rootElement);

            // pseudo elements
            Element pseudo = doc.createElement("mail");
            pseudo.appendChild(doc.createTextNode(MAIL));
            rootElement.appendChild(pseudo);

            // pseudo elements
            Element password = doc.createElement("password");
            password.appendChild(doc.createTextNode(PASSWORD));
            rootElement.appendChild(password);

            // theme elements
            Element theme = doc.createElement("theme");
            theme.appendChild(doc.createTextNode(THEME));
            rootElement.appendChild(theme);

            // language elements
            Element language = doc.createElement("language");
            language.appendChild(doc.createTextNode(LANGUAGE));
            rootElement.appendChild(language);

            new File(pathPersonalData).delete();

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(pathPersonalData));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static String getMail() {
        return getPersonnalData("//mail", "");
    }

    public static String getTheme() {
        return getPersonnalData("//theme", "white");
    }

    public static String getLangage() {
        return getPersonnalData("//language", "En");
    }

    public static String getPassword() {
        return getPersonnalData("//password", "");
    }

    public static void setMail(String mail) {
        createXML(mail, getTheme(), getLangage(), getPassword());
    }

    public static void setTheme(String theme) {
        createXML(getMail(), theme, getLangage(), getPassword());
    }

    public static void setLangage(String language) {
        createXML(getMail(), getTheme(), language, getPassword());
    }

    public static void setPassword(String password) {
        createXML(getMail(), getTheme(), getLangage(), password);
    }

}
