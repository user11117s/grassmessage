package com.array64.grassmessage.xml.properties;

import org.xml.sax.Attributes;

public class TextHolder implements XMLProperty<String> {
    private String text = "";

    @Override
    public void startTag(String qName, Attributes attrs) {
        throw new UnsupportedOperationException("Text holder can't have child elements.");
    }

    @Override
    public void endTag(String qName) {
        throw new UnsupportedOperationException("Text holder can't have child elements.");
    }

    @Override
    public void parseText(String text) {
        this.text += text;
        // Despite what it looks like, no, I'm not using text as a StringBuilder.
        // There should only ever be one call to parseText per TextHolder instance.
    }

    @Override
    public String get() {
        return text;
    }
}
