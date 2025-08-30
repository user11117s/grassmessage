package com.array64.grassmessage.components;

import net.md_5.bungee.api.chat.BaseComponent;

@FunctionalInterface
public interface ComponentModifier {
    void modify(BaseComponent component);
}
