package com.array64.grassmessage.xml.properties;

import com.array64.grassmessage.xml.XmlParser;

import java.util.function.Supplier;

public interface XmlProperty<T> extends XmlParser, Supplier<T> {}
