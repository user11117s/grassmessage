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
        NONE            = component -> component,
        BOLD            = component -> component.decorate(TextDecoration.BOLD),
        ITALIC          = component -> component.decorate(TextDecoration.ITALIC),
        UNDERLINED      = component -> component.decorate(TextDecoration.UNDERLINED),
        STRIKETHROUGH   = component -> component.decorate(TextDecoration.STRIKETHROUGH),
        OBFUSCATED      = component -> component.decorate(TextDecoration.OBFUSCATED),
        RESET           = component -> component.style(Style.empty());

    public static GComponentModifier color(TextColor color) {
        return component -> component.color(color);
    }

    public static GComponentModifier color(String hexCode) {
        return color(TextColor.fromHexString(hexCode));
    }

    public static GComponentModifier shadow(String shadowColor) {
        return component -> component.shadowColor(ShadowColor.fromHexString(shadowColor));
    }

    public static GComponentModifier click(ClickEvent event) {
        return component -> component.clickEvent(event);
    }

    public static GComponentModifier font(String font) {
        return component -> component.font(Key.key(font));
    }

    public static GComponentModifier insertion(String text) {
        return component -> component.insertion(text);
    }
}
