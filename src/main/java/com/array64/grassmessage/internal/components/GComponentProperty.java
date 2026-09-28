package com.array64.grassmessage.internal.components;

import com.array64.grassmessage.internal.xml.properties.XmlProperty;
import org.xml.sax.Attributes;

public class GComponentProperty implements XmlProperty<GComponent> {
    private final GComponent component;

    public GComponentProperty(GComponent component) {
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
    public GComponent get() {
        return component;
    }
}
