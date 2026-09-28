package com.array64.grassmessage.api;

import com.array64.grassmessage.internal.impl.GrassImpl;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;

public interface Grass {
    static Grass create(URL messagesFile) throws IOException, SAXException {
        return new GrassImpl(messagesFile);
    }

    static Grass create(URL messagesFile, URL schemaFile) throws IOException, SAXException {
        return new GrassImpl(messagesFile, schemaFile);
    }

    MessageInstance createMessageInstance(String messageName);
}