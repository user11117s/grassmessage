package com.array64.grassmessage.components.impl;

import com.array64.grassmessage.components.AbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import net.md_5.bungee.api.chat.BaseComponent;
import org.xml.sax.Attributes;

public class LeafBungeeComponent extends AbstractComponent {
    private final String name;

    public LeafBungeeComponent(String name) {
        this.name = name;
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
        parent.addExtra(ctx.getBungeeComponent(name));
    }

    @Override
    public void parseText(String text) {
        throwOnParseText();
    }
}
