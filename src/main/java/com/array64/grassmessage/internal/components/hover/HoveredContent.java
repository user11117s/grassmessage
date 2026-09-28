package com.array64.grassmessage.internal.components.hover;

import com.array64.grassmessage.internal.components.InstantiationContext;
import net.kyori.adventure.text.event.HoverEventSource;

public interface HoveredContent {
    HoverEventSource<?> instantiate(InstantiationContext ctx);
}
