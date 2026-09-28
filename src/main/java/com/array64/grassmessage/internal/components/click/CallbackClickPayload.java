package com.array64.grassmessage.internal.components.click;

import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.misc.Evaluation;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.ClickEvent;

import java.time.temporal.TemporalAmount;

public class CallbackClickPayload implements ClickPayload {
    private final String src;
    private final String duration;
    private final String uses;

    public CallbackClickPayload(String src, String duration, String uses) {
        this.src = src;
        this.duration = duration;
        this.uses = uses;
    }

    @Override
    public ClickEvent.Payload getPayload(InstantiationContext ctx) {
        throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN] This is a dummy payload, not meant to be called by getPayload().");
    }

    public ClickCallback<Audience> getCallback(InstantiationContext ctx) {
        return Evaluation.evalClickCallback(src, ctx);
    }

    public TemporalAmount getDuration(InstantiationContext ctx) {
        return Evaluation.evalDuration(duration, ctx);
    }

    public int getUses(InstantiationContext ctx) {
        int usesInt = Evaluation.evalInt(uses, ctx);
        return usesInt < 0 ? -1 : usesInt;
    }
}
