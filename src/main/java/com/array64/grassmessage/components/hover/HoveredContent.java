package com.array64.grassmessage.components.hover;

import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.text.event.HoverEventSource;

public interface HoveredContent {
    HoverEventSource<?> instantiate(InstantiationContext ctx);
}
