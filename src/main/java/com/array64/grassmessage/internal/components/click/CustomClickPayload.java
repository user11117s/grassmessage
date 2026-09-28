package com.array64.grassmessage.internal.components.click;

import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.misc.Evaluation;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.event.ClickEvent;

public class CustomClickPayload implements ClickPayload {
    private final String id, payload;
    private final boolean isVar;

    public CustomClickPayload(String id, String payload, boolean isVar) {
        this.id = id;
        this.payload = payload;
        this.isVar = isVar;
    }

    @Override
    public ClickEvent.Payload getPayload(InstantiationContext ctx) {
        return ClickEvent.Payload.custom(
            Key.key(ctx.substituteVars(id)),
            isVar ? Evaluation.evalNBT(payload, ctx) : BinaryTagHolder.binaryTagHolder(ctx.substituteVars(payload))
        );
    }
}
