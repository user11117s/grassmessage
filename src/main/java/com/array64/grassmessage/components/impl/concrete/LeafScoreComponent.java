package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.impl.AbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ScoreComponent;
import org.xml.sax.Attributes;

public class LeafScoreComponent extends AbstractComponent {
    private String childElementName;
    private String target = "", objective = "";
    private String defaultValue;

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        childElementName = qName;
    }

    @Override
    protected void exitTag(String qName) {
        childElementName = null;
    }

    @Override
    public BaseComponent instantiate(InstantiationContext ctx) {
        return defaultValue == null ? new ScoreComponent(target, objective) : new ScoreComponent(target, objective, defaultValue);
    }

    @Override
    public void parseText(String text) {
        String strippedText = text.strip();

        switch(childElementName) {
            case "target" -> target = strippedText;
            case "objective" -> objective = strippedText;
            case "default" -> defaultValue = strippedText;
            default -> throw new IllegalStateException("Unexpected text: " + text);
        }
    }
}
