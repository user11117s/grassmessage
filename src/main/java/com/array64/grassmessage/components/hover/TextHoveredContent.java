package com.array64.grassmessage.components.hover;

import com.array64.grassmessage.components.Component;
import com.array64.grassmessage.components.InstantiationContext;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;

public class TextHoveredContent implements HoveredContent {
    private final Component component;

    public TextHoveredContent(Component component) {
        this.component = component;
    }

    @Override
    public Content instantiate(InstantiationContext ctx) {
        BaseComponent parent = new TextComponent();
        component.instantiateInParent(parent, ctx);
        return new Text(parent);
    }
}
