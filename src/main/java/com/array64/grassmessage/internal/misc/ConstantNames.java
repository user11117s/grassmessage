package com.array64.grassmessage.internal.misc;

import com.array64.grassmessage.internal.components.GComponentModifier;
import com.array64.grassmessage.internal.components.GComponentModifiers;
import com.array64.grassmessage.internal.components.click.ClickType;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Map;

import static com.array64.grassmessage.internal.components.click.ClickType.*;
import static java.util.Map.entry;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public class ConstantNames {
    public static final ModifierMapping[] MODIFIERS = {
        mod(GComponentModifiers.BOLD, "bold", "b"),
        mod(GComponentModifiers.ITALIC, "italic", "i"),
        mod(GComponentModifiers.UNDERLINED, "underlined", "u"),
        mod(GComponentModifiers.STRIKETHROUGH, "strikethrough", "st"),
        mod(GComponentModifiers.OBFUSCATED, "obfuscated", "obf"),
        mod(GComponentModifiers.RESET, "reset")
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

    public static final Map<String, ClickType> CLICK_EVENTS = Map.ofEntries(
        entry("run_command", RUN_COMMAND),
        entry("suggest_command", SUGGEST_COMMAND),
        entry("open_url", OPEN_URL),
        entry("change_page", CHANGE_PAGE),
        entry("copy_to_clipboard", COPY_TO_CLIPBOARD),
        entry("custom", CUSTOM),
        entry("show_dialog", SHOW_DIALOG),
        entry("open_file", OPEN_FILE),
        entry("callback", CALLBACK)
    );

    public record ModifierMapping(GComponentModifier modifier, String[] qNames) {}
    public record ColorMapping(NamedTextColor color, String name) {}

    private static ModifierMapping mod(GComponentModifier modifier, String... qNames) {
        return new ModifierMapping(modifier, qNames);
    }

    private static ColorMapping color(NamedTextColor color, String qName) {
        return new ColorMapping(color, qName);
    }
}
