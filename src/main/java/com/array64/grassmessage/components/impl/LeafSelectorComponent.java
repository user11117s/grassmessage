package com.array64.grassmessage.components.impl;

import com.array64.grassmessage.components.AbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.SelectorComponent;
import org.xml.sax.Attributes;

public class LeafSelectorComponent extends AbstractComponent {
    private String selector = "";

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
        parent.addExtra(new SelectorComponent(selector));
    }

    @Override
    public void parseText(String text) {
        selector += text.strip();
    }
}
