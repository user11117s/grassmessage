package com.array64.grassmessage;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

import java.awt.Color;

public class ComponentModifiers {
    public static final ComponentModifier
        NONE            = component -> {},
        BOLD            = component -> component.setBold(true),
        ITALIC          = component -> component.setItalic(true),
        UNDERLINED      = component -> component.setUnderlined(true),
        STRIKETHROUGH   = component -> component.setStrikethrough(true),
        OBFUSCATED      = component -> component.setObfuscated(true),
        RESET           = component -> component.setReset(true);

    public static ComponentModifier color(ChatColor color) {
        return component -> component.setColor(color);
    }

    public static ComponentModifier color(String hexCode) {
        return color(ChatColor.of(hexCode));
    }

    public static ComponentModifier shadow(String shadowColor) {
        return component -> component.setShadowColor(toAWTColor(shadowColor));
    }

    public static ComponentModifier click(ClickEvent event) {
        return component -> component.setClickEvent(event);
    }

    public static ComponentModifier hover(HoverEvent event) {
        return component -> component.setHoverEvent(event);
    }

    public static ComponentModifier font(String font) {
        return component -> component.setFont(font);
    }

    public static ComponentModifier insertion(String text) {
        return component -> component.setInsertion(text);
    }

    private static java.awt.Color toAWTColor(String hexCode) {
        return new Color(
            Integer.valueOf(hexCode.substring(1, 3), 16), // red
            Integer.valueOf(hexCode.substring(3, 5), 16), // green
            Integer.valueOf(hexCode.substring(5, 7), 16), // blue
            hexCode.length() == 9 ? Integer.valueOf(hexCode.substring(7, 9), 16) : 255 // alpha
        );
    }
}
