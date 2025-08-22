package com.array64.grassmessage;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

class ComponentModifiers {
    public static final ComponentModifier
        NONE            = builder -> {},
        BOLD            = builder -> builder.bold(true),
        ITALIC          = builder -> builder.italic(true),
        UNDERLINED      = builder -> builder.underlined(true),
        STRIKETHROUGH   = builder -> builder.strikethrough(true),
        OBFUSCATED      = builder -> builder.obfuscated(true),
        RESET           = ComponentBuilder::reset;

    public static ComponentModifier color(ChatColor color) {
        return builder -> builder.color(color);
    }

    public static ComponentModifier click(ClickEvent event) {
        return builder -> builder.event(event);
    }

    public static ComponentModifier hover(HoverEvent event) {
        return builder -> builder.event(event);
    }

    public static ComponentModifier font(String font) {
        return builder -> builder.font(font);
    }

    public static ComponentModifier insertion(String text) {
        return builder -> builder.insertion(text);
    }
}
