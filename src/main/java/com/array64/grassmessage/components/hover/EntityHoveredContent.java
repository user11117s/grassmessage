package com.array64.grassmessage.components.hover;

import com.array64.grassmessage.components.Component;
import com.array64.grassmessage.components.InstantiationContext;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Entity;

public class EntityHoveredContent implements HoveredContent {
    private final String type;
    private final String uuid;
    private final Component name;

    public EntityHoveredContent(String type, String uuid, Component name) {
        this.type = type;
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public Content instantiate(InstantiationContext ctx) {
        net.kyori.adventure.text.Component parent = net.kyori.adventure.text.Component.empty();
        name.instantiateInParent(parent, ctx);
        return new Entity(ctx.substituteVars(type), ctx.substituteVars(uuid), parent);
    }
}
