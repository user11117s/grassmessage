package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.components.impl.PlaintextInstantiatingComponent;

public class LeafTextComponent extends PlaintextInstantiatingComponent {
    private String text = "";

    @Override
    public String instantiateText(InstantiationContext ctx) {
        return text;
    }

    @Override
    public void parseText(String text) {
        this.text += text;
    }
}
