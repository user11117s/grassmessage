package com.array64.grassmessage.components.click;

import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.dialog.DialogLike;
import net.kyori.adventure.text.event.ClickEvent;

public class DialogClickPayload implements ClickPayload {
    private final String var;

    public DialogClickPayload(String var) {
        this.var = var;
    }

    @Override
    public ClickEvent.Payload getPayload(InstantiationContext ctx) {
        if(ctx.getVarRaw(var) instanceof DialogLike dialog) {
            return ClickEvent.Payload.dialog(dialog);
        } else
            throw new IllegalArgumentException("Dialog, pointed to by variable " + var + ", is not a DialogLike.");
    }
}
