package com.array64.grassmessage.components.click;

import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.event.ClickEvent;

public class CustomClickPayload implements ClickPayload {
    private final String id, payload;

    public CustomClickPayload(String id, String payload) {
        this.id = id;
        this.payload = payload;
    }

    @Override
    public ClickEvent.Payload getPayload(InstantiationContext ctx) {
        return ClickEvent.Payload.custom(Key.key(ctx.substituteVars(id)), BinaryTagHolder.binaryTagHolder(ctx.substituteVars(payload)));
    }
}
