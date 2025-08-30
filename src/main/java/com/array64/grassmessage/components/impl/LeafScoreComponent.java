package com.array64.grassmessage.components.impl;

import com.array64.grassmessage.components.AbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ScoreComponent;
import org.xml.sax.Attributes;

public class LeafScoreComponent extends AbstractComponent {
    private String childElementName;
    private String target = "", objective = "";

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        childElementName = qName;
    }

    @Override
    protected void exitTag(String qName) {
        childElementName = null;
    }

    @Override
    public void instantiateInParent(BaseComponent parent, InstantiationContext ctx) {
        parent.addExtra(new ScoreComponent(target, objective));
    }

    @Override
    public void parseText(String text) {
        String strippedText = text.strip();

        if("target".equals(childElementName)) {
            target += strippedText;
        }
        else if("objective".equals(childElementName)) {
            objective += strippedText;
        }
        else throw new IllegalStateException("Unexpected text: " + text);
    }
}
