package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.impl.AbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.misc.Color;
import com.array64.grassmessage.data.GradientData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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
    public void instantiateInParent(Component parent, InstantiationContext ctx) {
        instantiateTextInParent(parent, ctx.substituteVars(text), ctx);
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        throwOnInstantiate();
        return null;
    }

    private void instantiateTextInParent(Component parent, String text, InstantiationContext ctx) {
        int length = text.length();
        GradientData gradient = ctx.getGradient(ref);

        for(int i = 0; i < length; i++) {
            Component component = Component.text(Character.toString(text.charAt(i)));
            Color color = gradient.evaluate((float) i / Math.max(1f, length - 1));
            component = component.color(TextColor.fromHexString(color.toString()));

            parent.append(component);
        }
    }

    @Override
    public void parseText(String text) {
        this.text += text;
    }
}
