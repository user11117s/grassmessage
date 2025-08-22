package com.array64.grassmessage;

import net.md_5.bungee.api.chat.ComponentBuilder;

class ComponentOfText implements Component {
    private String text = "";

    @Override
    public void modify(ComponentBuilder builder) {
        builder.append(text);
    }

    @Override
    public void append(String text) {
        this.text += text;
    }
}
