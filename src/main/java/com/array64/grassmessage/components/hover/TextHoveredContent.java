package com.array64.grassmessage.components.hover;

import com.array64.grassmessage.components.Component;
import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;

public class TextHoveredContent implements HoveredContent {
    private final Component component;

    public TextHoveredContent(Component component) {
        this.component = component;
    }

    @Override
    public HoverEventSource<?> instantiate(InstantiationContext ctx) {
        net.kyori.adventure.text.Component parent = net.kyori.adventure.text.Component.empty();
        component.instantiateInParent(parent, ctx);
        return HoverEvent.showText(parent);
    }
}
