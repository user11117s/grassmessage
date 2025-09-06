package com.array64.grassmessage.xml.properties;

import org.xml.sax.Attributes;

public record XMLPropertyMeta(String propertyName, Attributes attrs, XMLProperty<?> parser) {}