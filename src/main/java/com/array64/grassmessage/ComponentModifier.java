package com.array64.grassmessage;

import net.md_5.bungee.api.chat.BaseComponent;

@FunctionalInterface
public interface ComponentModifier {
    void modify(BaseComponent component);
}
