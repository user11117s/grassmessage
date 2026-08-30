package com.array64.grassmessage.components;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.ShadowColor;
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
        return (component, ctx) -> component.color(TextColor.fromHexString(ctx.substituteVars(hexCode)));
    }

    public static GComponentModifier shadow(String shadowColor) {
        return (component, ctx) -> component.shadowColor(ShadowColor.fromHexString(ctx.substituteVars(shadowColor)));
    }

    public static GComponentModifier click(ClickEvent.Action action, String value) {
        return (component, ctx) -> component.clickEvent(
                ClickEvent.clickEvent(action, ClickEvent.Payload.string(ctx.substituteVars(value)))
        );
    }

    public static GComponentModifier font(String font) {
        return (component, ctx) -> component.font(Key.key(ctx.substituteVars(font)));
    }

    public static GComponentModifier insertion(String text) {
        return (component, ctx) -> component.insertion(ctx.substituteVars(text));
    }
}
