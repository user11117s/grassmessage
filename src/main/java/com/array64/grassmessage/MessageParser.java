package com.array64.grassmessage;

import org.xml.sax.Attributes;

class MessageParser extends XMLParser {
    private final ComponentHolder holder;

    MessageParser(ComponentHolder holder) {
        this.holder = holder;
    }

    @Override
    protected void startTag(String qName, Attributes attributes) {
        holder.addComponent();
    }

    @Override
    protected void endTag(String qName) {

    }

    @Override
    protected void parseText(String text) {
        text.;
    }
}
