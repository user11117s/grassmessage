package com.array64.grassmessage;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

abstract class AbstractXMLParser implements XMLParser {
    private boolean isDoneParsing;

    protected void setDoneParsing() {
        isDoneParsing = true;
    }

    public boolean isDoneParsing() {
        return isDoneParsing;
    }
}
