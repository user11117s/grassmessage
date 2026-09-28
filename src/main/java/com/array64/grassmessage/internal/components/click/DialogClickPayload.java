package com.array64.grassmessage.internal.components.click;

import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.misc.Evaluation;
import net.kyori.adventure.text.event.ClickEvent;

public class DialogClickPayload implements ClickPayload {
    private final String src;

    public DialogClickPayload(String src) {
        this.src = src;
    }

    @Override
    public ClickEvent.Payload getPayload(InstantiationContext ctx) {
        return ClickEvent.Payload.dialog(Evaluation.evalDialog(src, ctx));
    }
}
