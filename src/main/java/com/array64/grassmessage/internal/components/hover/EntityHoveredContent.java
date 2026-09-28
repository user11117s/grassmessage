package com.array64.grassmessage.internal.components.hover;

import com.array64.grassmessage.internal.components.GComponent;
import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.misc.Evaluation;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;

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
        if(name == null) return HoverEvent.showEntity(Key.key(ctx.substituteVars(type)), Evaluation.evalUUID(uuid, ctx));

        Component parent = Component.empty();
        parent = name.instantiateInParent(parent, ctx);
        return HoverEvent.showEntity(Key.key(ctx.substituteVars(type)), Evaluation.evalUUID(uuid, ctx), parent);
    }
}
