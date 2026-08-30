package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.impl.GAbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
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
        Object objComponent = ctx.getVarRaw(name);

        if(objComponent instanceof Component component)
            return component;
        else
            throw new IllegalArgumentException("Value of variable " + name + " is not an Adventure component.");
    }

    @Override
    public void parseText(String text) {
        throwOnParseText();
    }
}
