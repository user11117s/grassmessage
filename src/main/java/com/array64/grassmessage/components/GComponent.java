package com.array64.grassmessage.components;

import com.array64.grassmessage.xml.properties.XMLProperty;
import net.kyori.adventure.text.Component;

public interface GComponent extends XMLProperty<GComponent> {
    Component instantiateInParent(Component parent, InstantiationContext ctx);
    default void onStart() {}
    default void onEnd() {}

    @Override
    default GComponent get() {
        return this;
    }
}
