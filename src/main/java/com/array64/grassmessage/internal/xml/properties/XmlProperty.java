package com.array64.grassmessage.internal.xml.properties;

import com.array64.grassmessage.internal.xml.XmlParser;

import java.util.function.Supplier;

public interface XmlProperty<T> extends XmlParser, Supplier<T> {}
