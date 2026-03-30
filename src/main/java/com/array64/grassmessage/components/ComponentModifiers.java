package com.array64.grassmessage.components;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.ShadowColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

public class ComponentModifiers {
    public static final ComponentModifier
        NONE            = component -> {},
        BOLD            = component -> component.decorate(TextDecoration.BOLD),
        ITALIC          = component -> component.decorate(TextDecoration.ITALIC),
        UNDERLINED      = component -> component.decorate(TextDecoration.UNDERLINED),
        STRIKETHROUGH   = component -> component.decorate(TextDecoration.STRIKETHROUGH),
        OBFUSCATED      = component -> component.decorate(TextDecoration.OBFUSCATED),
        RESET           = component -> component.style(Style.empty());

    public static ComponentModifier color(TextColor color) {
        return component -> component.color(color);
    }

    public static ComponentModifier color(String hexCode) {
        return color(TextColor.fromHexString(hexCode));
    }

    public static ComponentModifier shadow(String shadowColor) {
        return component -> component.shadowColor(ShadowColor.fromHexString(shadowColor));
    }

    public static ComponentModifier click(ClickEvent event) {
        return component -> component.clickEvent(event);
    }

    public static ComponentModifier hover(HoverEvent<?> event) {
        return component -> component.hoverEvent(event);
    }

    public static ComponentModifier font(String font) {
        return component -> component.font(Key.key(font));
    }

    public static ComponentModifier insertion(String text) {
        return component -> component.insertion(text);
    }

    /* private static java.awt.Color toAWTColor(String hexCode) {
        return new Color(
            Integer.valueOf(hexCode.substring(1, 3), 16), // red
            Integer.valueOf(hexCode.substring(3, 5), 16), // green
            Integer.valueOf(hexCode.substring(5, 7), 16), // blue
            hexCode.length() == 9 ? Integer.valueOf(hexCode.substring(7, 9), 16) : 255 // alpha
        );
    } */
}
