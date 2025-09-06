package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.impl.AbstractComponent;
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
    public BaseComponent instantiate(InstantiationContext ctx) {
        return ctx.getBungeeComponent(name);
    }

    @Override
    public void parseText(String text) {
        throwOnParseText();
    }
}
