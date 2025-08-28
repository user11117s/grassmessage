package com.array64.grassmessage;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.KeybindComponent;
import org.xml.sax.Attributes;

public class LeafKeybindComponent extends AbstractComponent {
    private String keybind = "";

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        throw new UnsupportedOperationException("LeafKeybindComponent does not support enterTag");
    }

    @Override
    protected void exitTag(String qName) {
        throw new UnsupportedOperationException("LeafKeybindComponent does not support exitTag.");
    }

    @Override
    public void modifyParent(BaseComponent parent) {
        parent.addExtra(new KeybindComponent(keybind));
    }

    @Override
    public void parseText(String text) {
        keybind += text.strip();
    }
}
