package com.array64.grassmessage;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class XMLParserAdapter extends DefaultHandler {
    private final XMLParser parser;

    XMLParserAdapter(XMLParser parser) {
        this.parser = parser;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) throws SAXException {
        parser.startTag(uri, attrs);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        parser.endTag(qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        parser.parseText(String.valueOf(ch));
    }
}
