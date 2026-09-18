package com.array64.grassmessage.components.click;

import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.text.event.ClickEvent;

public class IntegerClickPayload implements ClickPayload {
    private String value;

    public IntegerClickPayload(String value) {
        this.value = value;
    }

    @Override
    public ClickEvent.Payload getPayload(InstantiationContext ctx) {
        return ClickEvent.Payload.integer(Integer.parseInt(ctx.substituteVars(value)));
    }
}
