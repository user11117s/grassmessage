package com.array64.grassmessage.components;

import com.array64.grassmessage.components.impl.AbstractComponent;
import org.xml.sax.Attributes;

@FunctionalInterface
public interface ComponentFactory {
    Component getComponent(Attributes attrs);

    @FunctionalInterface
    interface Abstract extends ComponentFactory {

        @Override
        default Component getComponent(Attributes attrs) {
            return getAbstractComponent(attrs);
        }

        AbstractComponent getAbstractComponent(Attributes attrs);
    }
}
