package com.array64.grassmessage.xml.properties;

import com.array64.grassmessage.xml.XMLParser;

import java.util.function.Supplier;

public interface XMLProperty<T> extends XMLParser, Supplier<T> {}
