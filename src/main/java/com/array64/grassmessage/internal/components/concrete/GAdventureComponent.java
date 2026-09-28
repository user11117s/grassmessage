package com.array64.grassmessage.internal.components.concrete;

import com.array64.grassmessage.internal.components.GAbstractComponent;
import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.misc.Evaluation;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

public class GAdventureComponent extends GAbstractComponent {
    private final String name;

    public GAdventureComponent(String name) {
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
    public Component instantiate(InstantiationContext ctx) {
        return Evaluation.evalAdventureComponent(name, ctx);
    }

    @Override
    public void parseText(String text) {
        throwOnParseText();
    }
}
