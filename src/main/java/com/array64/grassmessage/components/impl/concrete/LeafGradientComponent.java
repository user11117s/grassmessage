package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.impl.AbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.misc.Color;
import com.array64.grassmessage.data.GradientData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.xml.sax.Attributes;

public class LeafGradientComponent extends AbstractComponent {
    private String text = "";
    private final String ref;

    public LeafGradientComponent(String ref) {
        this.ref = ref;
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        throw new IllegalStateException("Unexpected tag: " + qName);
    }

    @Override
    protected void exitTag(String qName) {}

    @Override
    public void instantiateInParent(BaseComponent parent, InstantiationContext ctx) {
        StringBuilder sb = new StringBuilder();
        instantiateTextInParent(parent, text, ctx);
    }

    @Override
    protected BaseComponent instantiate(InstantiationContext ctx) {
        throwOnInstantiate();
        return null;
    }

    private void instantiateTextInParent(BaseComponent parent, String text, InstantiationContext ctx) {
        int length = text.length();
        GradientData gradient = ctx.getGradient(ctx.substituteVars(ref));

        for(int i = 0; i < length; i++) {
            TextComponent component = new TextComponent(Character.toString(text.charAt(i)));
            Color color = gradient.evaluate((float) i / Math.max(1f, length - 1));
            component.setColor(ChatColor.of(color.toString()));

            parent.addExtra(component);
        }
    }

    @Override
    public void parseText(String text) {
        this.text += text;
    }
}
