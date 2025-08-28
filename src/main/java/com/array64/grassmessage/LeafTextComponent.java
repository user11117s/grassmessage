package com.array64.grassmessage;

import net.md_5.bungee.api.chat.BaseComponent;
import org.xml.sax.Attributes;

public class LeafTextComponent implements Component {
    private String text = "";

    @Override
    public void modifyParent(BaseComponent parent) {
        parent.addExtra(text);
    }


    @Override
    public boolean isDoneParsing() {
        return true; // We should never pretend like we still need to be "finished".
    }

    @Override
    public void startTag(String qName, Attributes attributes) {
        throw new UnsupportedOperationException("LeafTextComponent does not support startTag.");
    }

    @Override
    public void endTag(String qName) {
        throw new UnsupportedOperationException("LeafTextComponent does not support endTag.");
    }

    @Override
    public void parseText(String text) {
        this.text += text;
    }
}
