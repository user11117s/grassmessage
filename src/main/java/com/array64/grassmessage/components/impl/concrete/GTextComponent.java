package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.components.impl.GAbstractComponent;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

public class GTextComponent extends GAbstractComponent {
    private String text = "";

    @Override
    public void parseText(String text) {
        this.text += text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
    protected Component instantiate(InstantiationContext ctx) {
        return Component.text(ctx.substituteVars(text));
    }
}
