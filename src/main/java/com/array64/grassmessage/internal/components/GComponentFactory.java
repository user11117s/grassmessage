package com.array64.grassmessage.internal.components;

import org.xml.sax.Attributes;

@FunctionalInterface
public interface GComponentFactory {
    GComponent getComponent(Attributes attrs);

    @FunctionalInterface
    interface Abstract extends GComponentFactory {

        @Override
        default GComponent getComponent(Attributes attrs) {
            return getAbstractComponent(attrs);
        }

        GAbstractComponent getAbstractComponent(Attributes attrs);
    }
}
