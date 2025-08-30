package com.array64.grassmessage.components.impl;

import com.array64.grassmessage.components.InstantiationContext;

public class LeafVariableComponent extends PlaintextInstantiatingComponent {
    private final String ref;

    public LeafVariableComponent(String ref) {
        this.ref = ref;
    }

    @Override
    public String instantiateText(InstantiationContext ctx) {
        return ctx.getVar(ref);
    }

    @Override
    public void parseText(String text) {
        throw new UnsupportedOperationException(this.getClass() + " does not support parseText.");
    }
}
