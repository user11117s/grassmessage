package com.array64.grassmessage.components;

import com.array64.grassmessage.xml.properties.XMLProperty;

public interface Component extends XMLProperty<Component> {
    void instantiateInParent(net.kyori.adventure.text.Component parent, InstantiationContext ctx);
    default void onStart() {}
    default void onEnd() {}

    @Override
    default Component get() {
        return this;
    }
}
