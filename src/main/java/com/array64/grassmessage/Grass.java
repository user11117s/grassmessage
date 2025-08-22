package com.array64.grassmessage;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

public class Grass {
    private static final String XSD_LOCATION = "schema.xsd";
    /**
     * Initialize GrassMessage. Expects a file named <code>messages.xml</code> in your resources folder.
     */
    public Grass() {
        this("messages.xml");
    }

    /**
     * Initialize GrassMessage from a file in your resources folder.
     * @param filename The file that GrassMessage should look for
     */
    public Grass(String filename) {
        try(InputStream is = Grass.class.getClassLoader().getResourceAsStream(filename)) {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setFeature("http://xml.org/sax/features/validation", true);
            factory.setFeature("http://apache.org/xml/features/validation/schema", true);

            SAXParser saxParser = factory.newSAXParser();
            saxParser.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", XSD_LOCATION);

            FileData fileData = new FileData();
            FileParser fileParser = new FileParser(fileData);
            saxParser.parse(is, fileParser);

        } catch(IOException | SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}