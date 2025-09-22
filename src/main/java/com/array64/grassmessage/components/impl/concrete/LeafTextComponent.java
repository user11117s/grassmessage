package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.components.impl.AbstractComponent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.xml.sax.Attributes;

public class LeafTextComponent extends AbstractComponent {
    private String text = "";

    @Override
    public void parseText(String text) {
        this.text += text;
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
    protected BaseComponent instantiate(InstantiationContext ctx) {
        return new TextComponent(ctx.substituteVars(text));
    }
}
