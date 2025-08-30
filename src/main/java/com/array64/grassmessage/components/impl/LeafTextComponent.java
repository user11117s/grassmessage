package com.array64.grassmessage.components.impl;

import com.array64.grassmessage.components.InstantiationContext;

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
