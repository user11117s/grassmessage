package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.impl.GAbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.misc.Color;
import com.array64.grassmessage.data.GradientData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.xml.sax.Attributes;

public class GGradientComponent extends GAbstractComponent {
    private String text = "";
    private final String ref;

    public GGradientComponent(String ref) {
        this.ref = ref;
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        throw new IllegalStateException("Unexpected tag: " + qName);
    }

    @Override
    protected void exitTag(String qName) {}

    @Override
    public Component instantiateInParent(Component parent, InstantiationContext ctx) {
        return instantiateTextInParent(parent, ctx.substituteVars(text), ctx);
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        throwOnInstantiate();
        return null;
    }

    private Component instantiateTextInParent(Component parent, String text, InstantiationContext ctx) {
        int length = text.length();
        GradientData gradient = ctx.getGradient(ref);

        for(int i = 0; i < length; i++) {
            Component component = Component.text(Character.toString(text.charAt(i)));
            Color color = gradient.evaluate((float) i / Math.max(1f, length - 1));
            component = component.color(TextColor.fromHexString(color.toString()));

            parent = parent.append(component);
        }
        return parent;
    }

    @Override
    public void parseText(String text) {
        this.text += text;
    }
}
