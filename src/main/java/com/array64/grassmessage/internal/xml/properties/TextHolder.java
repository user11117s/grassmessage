package com.array64.grassmessage.internal.xml.properties;

import org.xml.sax.Attributes;

public class TextHolder implements XmlProperty<String> {
    private String text = "";

    @Override
    public void startTag(String qName, Attributes attrs) {
        throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN] Text holder can't have child elements.");
    }

    @Override
    public void endTag(String qName) {
        throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN] Text holder can't have child elements.");
    }

    @Override
    public void parseText(String text) {
        this.text += text.strip();
        // Despite what it looks like, no, I'm not using text as a makeshift StringBuilder.
        // There should only ever be one call to parseText per TextHolder instance.
    }

    @Override
    public String get() {
        return text;
    }
}
