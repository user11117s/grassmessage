package com.array64.grassmessage.internal.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParserAdapter extends DefaultHandler {
    private final XmlParser parser;
    private StringBuilder cumulativeText;

    public XmlParserAdapter(XmlParser parser) {
        this.parser = parser;
        this.cumulativeText = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        if(!cumulativeText.isEmpty()) parser.parseText(cumulativeText.toString());
        parser.startTag(qName, attrs);
        cumulativeText = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if(!cumulativeText.isEmpty()) parser.parseText(cumulativeText.toString());
        parser.endTag(qName);
        cumulativeText = new StringBuilder();
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        cumulativeText.append(ch, start, length);
    }
}
