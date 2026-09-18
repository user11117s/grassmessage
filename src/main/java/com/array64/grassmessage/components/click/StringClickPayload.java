package com.array64.grassmessage.components.click;

import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.text.event.ClickEvent;

public class StringClickPayload implements ClickPayload {
    private final String value;

    public StringClickPayload(String value) {
        this.value = value;
    }

    @Override
    public ClickEvent.Payload getPayload(InstantiationContext ctx) {
        return ClickEvent.Payload.string(ctx.substituteVars(value));
    }
}
