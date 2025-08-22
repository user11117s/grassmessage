package com.array64.grassmessage;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

abstract class XMLParser extends DefaultHandler {
    private boolean isDoneParsing;
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        startTag(uri, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        endTag(qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        parseText(String.valueOf(ch));
    }

    public void setDoneParsing() {
        isDoneParsing = true;
    }

    public boolean isDoneParsing() {
        return isDoneParsing;
    }

    protected abstract void startTag(String qName, Attributes attributes);
    protected abstract void endTag(String qName);
    protected abstract void parseText(String text);
}
