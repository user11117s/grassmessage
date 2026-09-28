package com.array64.grassmessage.internal.components;

import net.kyori.adventure.text.Component;

@FunctionalInterface
public interface GComponentModifier {
    Component modify(Component component, InstantiationContext ctx);
}
