package com.array64.grassmessage.components.hover;

import com.array64.grassmessage.components.GComponent;
import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;

import java.util.UUID;

public class EntityHoveredContent implements HoveredContent {
    private final String type;
    private final String uuid;
    private final GComponent name;

    public EntityHoveredContent(String type, String uuid, GComponent name) {
        this.type = type;
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public HoverEventSource<?> instantiate(InstantiationContext ctx) {
        Component parent = Component.empty();
        parent = name.instantiateInParent(parent, ctx);
        return HoverEvent.showEntity(Key.key(ctx.substituteVars(type)), UUID.fromString(ctx.substituteVars(uuid)), parent);
    }
}
