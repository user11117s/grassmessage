package com.array64.grassmessage;

import com.array64.grassmessage.components.ComponentRegistry;
import com.array64.grassmessage.data.FileData;
import com.array64.grassmessage.xml.parsers.FileParser;
import com.array64.grassmessage.xml.XMLParserAdapter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

public class Grass {
    private static final String XSD_LOCATION = "schema.xsd";
    private final FileData fileData = new FileData();
    private final ComponentRegistry componentRegistry = new ComponentRegistry(fileData);
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
            parseXML(is);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize GrassMessage from an {@link InputStream}.
     * @param is The stream that GrassMessage should use to read the XML from
     */
    public Grass(InputStream is) {
        parseXML(is);
    }

    private void parseXML(InputStream is) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setFeature("http://xml.org/sax/features/validation", true);
            factory.setFeature("http://apache.org/xml/features/validation/schema", true);

            SAXParser saxParser = factory.newSAXParser();
            saxParser.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", XSD_LOCATION);

            FileParser fileParser = new FileParser(fileData, componentRegistry);
            saxParser.parse(is, new XMLParserAdapter(fileParser));

        } catch(IOException | SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public ComponentRegistry getComponentRegistry() {
        return componentRegistry;
    }
}