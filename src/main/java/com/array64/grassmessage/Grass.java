package com.array64.grassmessage;

import com.array64.grassmessage.components.GComponentRegistry;
import com.array64.grassmessage.data.FileData;
import com.array64.grassmessage.data.Message;
import com.array64.grassmessage.data.MessageInstance;
import com.array64.grassmessage.data.MessageInstanceImpl;
import com.array64.grassmessage.xml.DepthTracker;
import com.array64.grassmessage.xml.parsers.FileParser;
import com.array64.grassmessage.xml.XmlParserAdapter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class Grass {
    private final FileData fileData = new FileData();
    private final GComponentRegistry componentRegistry = new GComponentRegistry(new DepthTracker());

    public Grass(URL messagesFile) throws IOException, SAXException {
        this(messagesFile, Objects.requireNonNull(Grass.class.getResource("/META-INF/xml/schema.xsd"), "[THIS SHOULD NEVER HAPPEN] Schema could not be found."));
    }

    public Grass(URL messagesFile, URL schemaFile) throws IOException, SAXException {
        try {
            parseXML(messagesFile, schemaFile);
        }
        catch(ParserConfigurationException | URISyntaxException e) {
            throw new RuntimeException(e); // Nous ne mettons pas la blâme sur le client pour nos propres fauts.
        }
    }

    private void parseXML(URL messagesFile, URL schemaFile) throws IOException, SAXException, ParserConfigurationException, URISyntaxException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setSchema(schema);
        factory.setNamespaceAware(true);
        factory.setXIncludeAware(true);
        SAXParser saxParser = factory.newSAXParser();

        Validator validator = schema.newValidator();
        validator.validate(new SAXSource(saxParser.getXMLReader(), new InputSource(messagesFile.toExternalForm())));

        FileParser fileParser = new FileParser(fileData, componentRegistry);
        saxParser.parse(messagesFile.toExternalForm(), new XmlParserAdapter(fileParser));
    }

    public GComponentRegistry getComponentRegistry() {
        return componentRegistry;
    }

    public MessageInstance createMessageInstance(String messageName) {
        Message message = Objects.requireNonNull(fileData.getMessage(messageName), "Message " + messageName + "does not exist.");
        return new MessageInstanceImpl(message);
    }
}