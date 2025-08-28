package com.array64.grassmessage;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.SelectorComponent;
import org.xml.sax.Attributes;

public class LeafSelectorComponent extends AbstractComponent {
    private String selector = "";

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        throw new UnsupportedOperationException("LeafSelectorComponent does not support enterTag.");
    }

    @Override
    protected void exitTag(String qName) {
        throw new UnsupportedOperationException("LeafSelectorComponent does not support exitTag.");
    }

    @Override
    public void modifyParent(BaseComponent parent) {
        parent.addExtra(new SelectorComponent(selector));
    }

    @Override
    public void parseText(String text) {
        selector += text.strip();
    }
}
