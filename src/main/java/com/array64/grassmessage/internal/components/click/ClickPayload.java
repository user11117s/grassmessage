package com.array64.grassmessage.internal.components.click;

import com.array64.grassmessage.internal.components.InstantiationContext;
import net.kyori.adventure.text.event.ClickEvent;

public interface ClickPayload {
    ClickEvent.Payload getPayload(InstantiationContext ctx);
}
