package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.components.impl.TextInstantiator;

public class LeafTextComponent extends TextInstantiator {
    private String text = "";

    @Override
    public String instantiateText(InstantiationContext ctx) {
        return ctx.substituteVars(text);
    }

    @Override
    public void parseText(String text) {
        this.text += text;
    }
}
