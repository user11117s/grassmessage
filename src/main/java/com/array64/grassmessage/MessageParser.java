package com.array64.grassmessage;

import org.xml.sax.Attributes;

class MessageParser extends XMLParser {
    private Component currentComponent;
    private ComponentRegistry componentRegistry;

    MessageParser(ComponentHolder message) {
        this.currentComponent = message;
        this.componentRegistry = ComponentRegistry.getInstance();
    }

    @Override
    protected void startTag(String qName, Attributes attrs) {
        Component newComponent = componentRegistry.get(qName, attrs, currentComponent);
        currentComponent.addComponent(newComponent);
        currentComponent = newComponent;
    }

    @Override
    protected void endTag(String qName) {
        currentComponent = currentComponent.getParent();
    }

    @Override
    protected void parseText(String text) {
        currentComponent.append(text);
    }
}
