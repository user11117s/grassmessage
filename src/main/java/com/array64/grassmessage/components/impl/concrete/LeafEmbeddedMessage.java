package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.impl.AbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

public class LeafEmbeddedMessage extends AbstractComponent {
    private final String ref;

    public LeafEmbeddedMessage(String ref) {
        this.ref = ref;
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        throwOnEnterTag();
    }

    @Override
    protected void exitTag(String qName) {
        throwOnExitTag();
    }

    @Override
    public void instantiateInParent(Component parent, InstantiationContext ctx) {
        ctx.getMessage(ref).instantiateInParent(parent, ctx);
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        throwOnInstantiate();
        return null;
    }

    @Override
    public void parseText(String text) {
        throwOnParseText();
    }
}
