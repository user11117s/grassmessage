package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.impl.GAbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.misc.Color;
import com.array64.grassmessage.data.GradientData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.xml.sax.Attributes;

import java.util.Iterator;

public class GGradientComponent extends GAbstractComponent {
    private String text = "";
    private final String ref;

    public GGradientComponent(String ref) {
        this.ref = ref;
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
    public Component instantiateInParent(Component parent, InstantiationContext ctx) {
        return instantiateTextInParent(parent, ctx.substituteVars(text), ctx);
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        throwOnInstantiate();
        return null;
    }

    private Component instantiateTextInParent(Component parent, String text, InstantiationContext ctx) {
        String finalText = text.strip();
        int length = finalText.length();
        GradientData gradient = ctx.getGradient(ref);
        Component[] atomParent = new Component[] {parent};

        gradient.evaluate(length, (color, i) -> {
            Component ch = Component.text(Character.toString(finalText.charAt(i)));
            atomParent[0] = atomParent[0].append(ch.color(color));
        }, ctx);

        return atomParent[0];
    }

    @Override
    public void parseText(String text) {
        this.text += text;
    }
}
