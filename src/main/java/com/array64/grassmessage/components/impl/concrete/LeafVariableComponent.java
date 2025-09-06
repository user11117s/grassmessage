package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.components.impl.PlaintextInstantiatingComponent;

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
        throwOnParseText();
    }
}
