package com.array64.grassmessage.api;

import com.array64.grassmessage.internal.impl.GrassImpl;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;

public interface Grass {
    static Grass create(URL messagesFile) {
        return new GrassImpl(messagesFile);
    }

    static Grass create(URL messagesFile, URL schemaFile) {
        return new GrassImpl(messagesFile, schemaFile);
    }

    void parse() throws IOException, SAXException;
    MessageInstance createMessageInstance(String messageName);
}