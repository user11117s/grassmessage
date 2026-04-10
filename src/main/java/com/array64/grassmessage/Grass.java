package com.array64.grassmessage;

import com.array64.grassmessage.components.GComponentRegistry;
import com.array64.grassmessage.data.FileData;
import com.array64.grassmessage.data.Message;
import com.array64.grassmessage.xml.DepthTracker;
import com.array64.grassmessage.xml.parsers.FileParser;
import com.array64.grassmessage.xml.XMLParserAdapter;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.catalog.CatalogFeatures;
import javax.xml.catalog.CatalogManager;
import javax.xml.catalog.CatalogResolver;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class Grass {
    private final FileData fileData = new FileData();
    private final GComponentRegistry componentRegistry = new GComponentRegistry(new DepthTracker());

    public Grass(InputStream messagesStream) throws IOException, SAXException {
        InputStream schemaStream = getClass().getClassLoader().getResourceAsStream("schema.xsd");
        if(schemaStream == null)
            throw new RuntimeException("Schema could not be found. This should not be happening.");

        try(schemaStream) {
            parseXML(schemaStream, messagesStream);
        }
        catch(ParserConfigurationException | URISyntaxException e) {
            throw new RuntimeException(e); // Nous ne mettons pas la blâme sur le client pour nos propres fauts.
        }
    }

    public Grass(InputStream messagesStream, InputStream schemaStream) throws IOException, SAXException {
        try {
            parseXML(schemaStream, messagesStream);
        } catch(ParserConfigurationException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseXML(InputStream schemaIS, InputStream is) throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
        // Load catalog
        URI catalogURI = Objects.requireNonNull(getClass().getClassLoader().getResource("catalog.xml")).toURI();
        CatalogResolver resolver = CatalogManager.catalogResolver(CatalogFeatures.defaults(), catalogURI);

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        schemaFactory.setResourceResolver(resolver);
        Schema schema = schemaFactory.newSchema(new StreamSource(schemaIS));

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setSchema(schema);
        factory.setNamespaceAware(true);

        SAXParser saxParser = factory.newSAXParser();
        saxParser.getXMLReader().setEntityResolver(resolver);

        FileParser fileParser = new FileParser(fileData, componentRegistry);
        saxParser.parse(is, new XMLParserAdapter(fileParser));
    }

    public GComponentRegistry getComponentRegistry() {
        return componentRegistry;
    }

    public Message getMessage(String messageName) {
        return fileData.getMessage(messageName);
    }
}