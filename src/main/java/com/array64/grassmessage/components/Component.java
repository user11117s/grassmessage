package com.array64.grassmessage.components;

import com.array64.grassmessage.xml.XMLParser;
import com.array64.grassmessage.xml.properties.XMLProperty;
import net.md_5.bungee.api.chat.BaseComponent;

public interface Component extends XMLProperty<Component> {
    void instantiateInParent(BaseComponent parent, InstantiationContext ctx);
    default void onStart() {}
    default void onEnd() {}

    @Override
    default Component get() {
        return this;
    }
}
