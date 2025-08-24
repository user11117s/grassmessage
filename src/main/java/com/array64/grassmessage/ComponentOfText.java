package com.array64.grassmessage;

import net.md_5.bungee.api.chat.ComponentBuilder;

class ComponentOfText implements Component {
    private String text = "";
    private final Component parent;

    ComponentOfText(Component parent) {
        this.parent = parent;
    }

    @Override
    public void modify(ComponentBuilder builder) {
        builder.append(text);
    }

    @Override
    public void append(String text) {
        this.text += text;
    }

    @Override
    public Component getParent() {
        return this.parent;
    }
}
