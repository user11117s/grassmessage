package com.array64.grassmessage.internal.components.concrete;

import com.array64.grassmessage.internal.components.GAbstractComponent;
import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.data.GradientData;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

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
