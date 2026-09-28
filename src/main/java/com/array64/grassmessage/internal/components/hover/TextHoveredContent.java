package com.array64.grassmessage.internal.components.hover;

import com.array64.grassmessage.internal.components.GComponent;
import com.array64.grassmessage.internal.components.InstantiationContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;

public class TextHoveredContent implements HoveredContent {
    private final GComponent component;

    public TextHoveredContent(GComponent component) {
        this.component = component;
    }

    @Override
    public HoverEventSource<?> instantiate(InstantiationContext ctx) {
        Component parent = Component.empty();
        parent = component.instantiateInParent(parent, ctx);
        return HoverEvent.showText(parent);
    }
}
