package com.array64.grassmessage.api;

import com.array64.grassmessage.internal.impl.GrassImpl;

import java.io.IOException;
import java.net.URL;

public interface Grass {
    /**
     * Creates a new instance of grass using the default schema.
     * Use {@link #parse()} to load the messages.
     *
     * @param messagesFile A {@link java.net.URL} that links to the messages file
     * */
    static Grass create(URL messagesFile) {
        return new GrassImpl(messagesFile);
    }

    /**
     * Creates a new instance of grass using a custom schema.
     * Use {@link #parse()} to load the messages.
     *
     * @param messagesFile a {@link java.net.URL} that links to the messages file
     * @param schemaFile a {@link java.net.URL} that links to the schemas file
     * */
    static Grass create(URL messagesFile, URL schemaFile) {
        return new GrassImpl(messagesFile, schemaFile);
    }

    /**
     * Parses the messages file.
     * */
    void parse() throws IOException;

    /**
     * Creates an instance of a message.
     *
     * @param messageName the message name
     * @return a {@link MessageInstance} which you can configure with variables before calling {@link MessageInstance#build()} on
     */
    MessageInstance createMessageInstance(String messageName);
}