package com.array64.grassmessage.components;

import com.array64.grassmessage.xml.properties.XMLProperty;
import org.xml.sax.Attributes;

public class ComponentProperty implements XMLProperty<Component> {
    private final Component component;

    public ComponentProperty(Component component) {
        this.component = component;
    }

    @Override
    public void startTag(String qName, Attributes attrs) {
        component.startTag(qName, attrs);
    }

    @Override
    public void endTag(String qName) {
        component.endTag(qName);
    }

    @Override
    public void parseText(String text) {
        component.parseText(text);
    }

    @Override
    public Component get() {
        return component;
    }
}
