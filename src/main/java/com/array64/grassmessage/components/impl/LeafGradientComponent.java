package com.array64.grassmessage.components.impl;

import com.array64.grassmessage.components.AbstractComponent;
import com.array64.grassmessage.components.ComponentRegistry;
import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.util.Color;
import com.array64.grassmessage.data.GradientData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.xml.sax.Attributes;

import java.util.List;

public class LeafGradientComponent extends AbstractComponent {
    private final List<PlaintextInstantiatingComponent> components;
    private final String ref;
    private final ComponentRegistry componentRegistry;

    public LeafGradientComponent(String ref, List<PlaintextInstantiatingComponent> components, ComponentRegistry registry) {
        this.components = components;
        this.ref = ref;
        this.componentRegistry = registry;
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        if(ComponentRegistry.VAR_TAG_NAME.equals(qName)) {
            components.add(componentRegistry.createVarComponent(attrs));
        }
        else throw new IllegalStateException("Unexpected tag: " + qName);
    }

    @Override
    protected void exitTag(String qName) {}

    @Override
    public void instantiateInParent(BaseComponent parent, InstantiationContext ctx) {
        StringBuilder sb = new StringBuilder();
        components.forEach(component -> sb.append(component.instantiateText(ctx)));
        instantiateTextInParent(parent, sb.toString(), ctx);
    }

    private void instantiateTextInParent(BaseComponent parent, String text, InstantiationContext ctx) {
        int length = text.length();
        GradientData gradient = ctx.getGradient(ref);

        for(int i = 0; i < length; i++) {
            TextComponent component = new TextComponent(Character.toString(text.charAt(i)));
            Color color = gradient.evaluate((float) i / Math.max(1f, length - 1));
            component.setColor(ChatColor.of(color.toString()));

            parent.addExtra(component);
        }
    }

    @Override
    public void parseText(String text) {
        var component = new LeafTextComponent();
        component.parseText(text);
        components.add(component);
    }
}
