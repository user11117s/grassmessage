package com.array64.grassmessage;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.ComponentStyleBuilder;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

class ComponentRegistry {
    private static ComponentRegistry instance;
    private final Map<String, ComponentFactory> componentFactories = new HashMap<>();

    private ComponentRegistry() {
        registerAll();
    }

    public static ComponentRegistry getInstance() {
        if(instance == null)
            instance = new ComponentRegistry();

        return instance;
    }

    private void register(ComponentFactory factory, String... qNames) {
        for(String qName : qNames) {
            componentFactories.put(qName, factory);
        }
    }

    public Component get(String qName, Attributes attrs, Component parent) {
        return componentFactories.get(qName).getComponent(attrs, parent);
    }

    private void registerModifier(Function<Attributes, ComponentModifier> modifierFunction, String... qNames) {
        register((attrs, parent) -> new ComponentHolder(parent, modifierFunction.apply(attrs)), qNames);
    }

    private void registerAll() {

        // Modifiers without attributes
        for(ConstantNames.ModifierMapping modifierMapping : ConstantNames.MODIFIERS) {
            final ComponentModifier modifier = modifierMapping.modifier();
            final String[] qNames = modifierMapping.qNames();

            registerModifier(attrs -> modifier, qNames);
        }

        // 16 default chat colors
        for(ConstantNames.ColorMapping colorMapping : ConstantNames.CHAT_COLORS) {
            final ChatColor chatColor = colorMapping.color();
            final String qName = colorMapping.qName();

            registerModifier(attrs -> ComponentModifiers.color(chatColor), qName);
        }

        // Modifiers with attributes
        registerModifier(attrs -> ComponentModifiers.color(attrs.getValue("hex")), "color");

        registerModifier(attrs -> ComponentModifiers.click(new ClickEvent(
            ConstantNames.CLICK_EVENTS.get(attrs.getValue("action")),
            attrs.getValue("value")
        )), "click");

        registerModifier(attrs -> ComponentModifiers.insertion(attrs.getValue("text")), "insertion");

        registerModifier(attrs -> ComponentModifiers.font(attrs.getValue("font")), "font");
    }
}
