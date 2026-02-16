package com.array64.grassmessage;

import com.array64.grassmessage.components.ComponentRegistry;
import com.array64.grassmessage.data.FileData;
import com.array64.grassmessage.data.Message;
import com.array64.grassmessage.xml.DepthTracker;
import com.array64.grassmessage.xml.parsers.FileParser;
import com.array64.grassmessage.xml.XMLParserAdapter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Grass {
    private final FileData fileData = new FileData();
    private final ComponentRegistry componentRegistry = new ComponentRegistry(new DepthTracker());

    public Grass() {
        this(new GrassConfig.Builder().build());
    }

    public Grass(GrassConfig config) {
        try(InputStream is = config.getXMLFilename().isEmpty() ? config.getInputStream() : new FileInputStream(config.getXMLFilename())) {
            parseXML(config.getXSDLocation(), is);
        } catch(IOException | SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseXML(String xsdLocation, InputStream is) throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature("http://xml.org/sax/features/validation", true);
        factory.setFeature("http://apache.org/xml/features/validation/schema", true);

        SAXParser saxParser = factory.newSAXParser();
        saxParser.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", xsdLocation);

        FileParser fileParser = new FileParser(fileData, componentRegistry);
        saxParser.parse(is, new XMLParserAdapter(fileParser));
    }

    public ComponentRegistry getComponentRegistry() {
        return componentRegistry;
    }

    public Message getMessage(String messageName) {
        return fileData.getMessage(messageName);
    }
}