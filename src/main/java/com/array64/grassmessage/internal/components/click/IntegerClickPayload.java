package com.array64.grassmessage.internal.components.click;

import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.misc.Evaluation;
import net.kyori.adventure.text.event.ClickEvent;

public class IntegerClickPayload implements ClickPayload {
    private final String value;

    public IntegerClickPayload(String value) {
        this.value = value;
    }

    @Override
    public ClickEvent.Payload getPayload(InstantiationContext ctx) {
        return ClickEvent.Payload.integer(Evaluation.evalInt(value, ctx));
    }
}
