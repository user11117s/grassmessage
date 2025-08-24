package com.array64.grassmessage;

import org.xml.sax.Attributes;

import java.util.function.Function;

@FunctionalInterface
interface ComponentFactory {
    Component getComponent(Attributes attrs, Component parent);
}
