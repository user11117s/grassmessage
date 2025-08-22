package com.array64.grassmessage;

import net.md_5.bungee.api.chat.ComponentBuilder;

@FunctionalInterface
interface ComponentModifier {
    void modify(ComponentBuilder builder);
}
