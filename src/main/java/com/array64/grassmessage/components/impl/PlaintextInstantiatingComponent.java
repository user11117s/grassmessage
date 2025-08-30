package com.array64.grassmessage.components.impl;

import com.array64.grassmessage.components.AbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import net.md_5.bungee.api.chat.BaseComponent;
import org.xml.sax.Attributes;

public abstract class PlaintextInstantiatingComponent extends AbstractComponent {

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
        parent.addExtra(instantiateText(ctx));
    }

    public abstract String instantiateText(InstantiationContext ctx);
}
