package com.array64.grassmessage.components.click;

import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.text.event.ClickEvent;

public interface ClickPayload {
    ClickEvent.Payload getPayload(InstantiationContext ctx);
}
