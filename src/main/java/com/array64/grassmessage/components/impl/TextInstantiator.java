package com.array64.grassmessage.components.impl;

import com.array64.grassmessage.components.InstantiationContext;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.xml.sax.Attributes;

public abstract class TextInstantiator extends AbstractComponent {

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        throwOnEnterTag();
    }

    @Override
    protected void exitTag(String qName) {
        throwOnExitTag();
    }

    @Override
    public BaseComponent instantiate(InstantiationContext ctx) {
        return new TextComponent(instantiateText(ctx));
    }

    public abstract String instantiateText(InstantiationContext ctx);
}
