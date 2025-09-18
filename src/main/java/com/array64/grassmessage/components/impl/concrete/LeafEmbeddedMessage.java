package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.impl.AbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import net.md_5.bungee.api.chat.BaseComponent;
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
    public void instantiateInParent(BaseComponent parent, InstantiationContext ctx) {
        ctx.getMessage(ctx.substituteVars(ref)).instantiateInParent(parent, ctx);
    }

    @Override
    protected BaseComponent instantiate(InstantiationContext ctx) {
        throwOnInstantiate();
        return null;
    }

    @Override
    public void parseText(String text) {
        throwOnParseText();
    }
}
