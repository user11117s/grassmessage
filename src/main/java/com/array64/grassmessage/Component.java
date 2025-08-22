package com.array64.grassmessage;

import net.md_5.bungee.api.chat.ComponentBuilder;

interface Component {
    void modify(ComponentBuilder builder);
    void append(String text);
}
