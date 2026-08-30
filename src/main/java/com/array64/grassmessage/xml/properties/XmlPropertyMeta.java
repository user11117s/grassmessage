package com.array64.grassmessage.xml.properties;

import org.xml.sax.Attributes;

public record XmlPropertyMeta(String propertyName, Attributes attrs, XmlProperty<?> parser) {
    @SuppressWarnings("unchecked")
    public <T> T getValue(Class<T> clazz) {
        return (T) parser.get();
    }
}