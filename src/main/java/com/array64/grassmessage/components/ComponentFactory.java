package com.array64.grassmessage.components;

import org.xml.sax.Attributes;

@FunctionalInterface
public interface ComponentFactory {
    Component getComponent(Attributes attrs);
}
