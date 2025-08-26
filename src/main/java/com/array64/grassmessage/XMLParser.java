package com.array64.grassmessage;

import org.xml.sax.Attributes;

interface XMLParser {
    boolean isDoneParsing();
    void startTag(String qName, Attributes attrs);
    void endTag(String qName);
    void parseText(String text);
}
