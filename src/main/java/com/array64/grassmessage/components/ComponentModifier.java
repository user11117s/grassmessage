package com.array64.grassmessage.components;

import net.kyori.adventure.text.Component;

@FunctionalInterface
public interface ComponentModifier {
    void modify(Component component);
}
