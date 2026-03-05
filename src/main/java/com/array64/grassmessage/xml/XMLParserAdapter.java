package com.array64.grassmessage.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParserAdapter extends DefaultHandler {
    private final XMLParser parser;
    private StringBuilder cumulativeText;

    public XMLParserAdapter(XMLParser parser) {
        this.parser = parser;
        this.cumulativeText = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
        parser.parseText(cumulativeText.toString());
        parser.startTag(qName, attrs);
        cumulativeText = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        parser.parseText(cumulativeText.toString());
        parser.endTag(qName);
        cumulativeText = new StringBuilder();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        cumulativeText.append(ch, start, length);
    }
}
