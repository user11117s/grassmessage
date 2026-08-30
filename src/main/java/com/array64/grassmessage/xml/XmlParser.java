package com.array64.grassmessage.xml;

import org.xml.sax.Attributes;

public interface XmlParser {
    void startTag(String qName, Attributes attrs);
    void endTag(String qName);
    void parseText(String text);
}
