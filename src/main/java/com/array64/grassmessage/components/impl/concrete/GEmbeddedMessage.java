package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.impl.GAbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

public class GEmbeddedMessage extends GAbstractComponent {
    private final String ref;

    public GEmbeddedMessage(String ref) {
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
    public Component instantiateInParent(Component parent, InstantiationContext ctx) {
        return ctx.getMessage(ref).instantiateInParent(parent, ctx);
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
