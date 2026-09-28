package com.array64.grassmessage.internal.components;

import com.array64.grassmessage.internal.misc.Evaluation;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class GComponentModifiers {
    public static final GComponentModifier
        NONE            = (component, ctx) -> component,
        BOLD            = (component, ctx) -> component.decorate(TextDecoration.BOLD),
        ITALIC          = (component, ctx) -> component.decorate(TextDecoration.ITALIC),
        UNDERLINED      = (component, ctx) -> component.decorate(TextDecoration.UNDERLINED),
        STRIKETHROUGH   = (component, ctx) -> component.decorate(TextDecoration.STRIKETHROUGH),
        OBFUSCATED      = (component, ctx) -> component.decorate(TextDecoration.OBFUSCATED),
        RESET           = (component, ctx) -> component.style(Style.empty());

    public static GComponentModifier color(TextColor color) {
        return (component, ctx) -> component.color(color);
    }

    public static GComponentModifier color(String hexCode) {
        return (component, ctx) -> component.color(Evaluation.evalTextColor(hexCode, ctx));
    }

    public static GComponentModifier shadow(String shadowColor) {
        return (component, ctx) -> component.shadowColor(Evaluation.evalShadowColor(shadowColor, ctx));
    }

    public static GComponentModifier font(String font) {
        return (component, ctx) -> component.font(Key.key(ctx.substituteVars(font)));
    }

    public static GComponentModifier insertion(String text) {
        return (component, ctx) -> component.insertion(ctx.substituteVars(text));
    }
}
