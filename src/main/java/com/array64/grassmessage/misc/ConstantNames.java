package com.array64.grassmessage.misc;

import com.array64.grassmessage.components.ComponentModifier;
import com.array64.grassmessage.components.ComponentModifiers;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;

import java.util.Map;

import static java.util.Map.entry;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.event.ClickEvent.Action.*;
import static net.kyori.adventure.text.event.HoverEvent.Action.*;

public class ConstantNames {
    public static final ModifierMapping[] MODIFIERS = {
        mod(ComponentModifiers.BOLD, "bold", "b"),
        mod(ComponentModifiers.ITALIC, "italic", "i"),
        mod(ComponentModifiers.UNDERLINED, "underlined", "u"),
        mod(ComponentModifiers.STRIKETHROUGH, "strikethrough", "st"),
        mod(ComponentModifiers.OBFUSCATED, "obfuscated", "obf"),
        mod(ComponentModifiers.RESET, "reset"),
    };
    public static final ColorMapping[] CHAT_COLORS = {
        color(BLACK, "black"),
        color(DARK_BLUE, "dark_blue"),
        color(DARK_GREEN, "dark_green"),
        color(DARK_AQUA, "dark_aqua"),
        color(DARK_RED, "dark_red"),
        color(DARK_PURPLE, "dark_purple"),
        color(GOLD, "gold"),
        color(GRAY, "gray"),
        color(DARK_GRAY, "dark_gray"),
        color(BLUE, "blue"),
        color(GREEN, "green"),
        color(AQUA, "aqua"),
        color(RED, "red"),
        color(LIGHT_PURPLE, "light_purple"),
        color(YELLOW, "yellow"),
        color(WHITE, "white")
    };

    public static final Map<String, ClickEvent.Action> CLICK_EVENTS = Map.ofEntries(
        entry("run_command", RUN_COMMAND),
        entry("suggest_command", SUGGEST_COMMAND),
        entry("open_url", OPEN_URL),
        entry("change_page", CHANGE_PAGE),
        entry("copy_to_clipboard", COPY_TO_CLIPBOARD)
    );

    public static final Map<String, HoverEvent.Action<?>> HOVER_EVENTS = Map.ofEntries(
        entry("show_text", SHOW_TEXT),
        entry("show_item", SHOW_ITEM),
        entry("show_entity", SHOW_ENTITY)
    );

    public record ModifierMapping(ComponentModifier modifier, String[] qNames) {}
    public record ColorMapping(NamedTextColor color, String qName) {}

    private static ModifierMapping mod(ComponentModifier modifier, String... qNames) {
        return new ModifierMapping(modifier, qNames);
    }

    private static ColorMapping color(NamedTextColor color, String qName) {
        return new ColorMapping(color, qName);
    }
}
