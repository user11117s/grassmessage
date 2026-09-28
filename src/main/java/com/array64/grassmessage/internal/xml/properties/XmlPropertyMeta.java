package com.array64.grassmessage.internal.xml.properties;

import org.xml.sax.Attributes;

public record XmlPropertyMeta(String propertyName, Attributes attrs, XmlProperty<?> parser) {
    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        return (T) parser.get();
    }
}