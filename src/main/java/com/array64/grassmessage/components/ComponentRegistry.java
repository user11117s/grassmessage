package com.array64.grassmessage.components;

import com.array64.grassmessage.components.impl.*;
import com.array64.grassmessage.util.ConstantNames;
import com.array64.grassmessage.data.FileData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ComponentRegistry {
    private final Map<String, ComponentFactory> componentFactories = new HashMap<>();
    private final FileData fileData;

    public static final String VAR_TAG_NAME = "var"; // Dedicated constant due to multiple uses

    public ComponentRegistry(FileData fileData) {
        this.fileData = fileData;
    }

    public void register(ComponentFactory factory, String... qNames) {
        for(String qName : qNames) {
            componentFactories.put(qName, factory);
        }
    }

    public Component get(String qName, Attributes attrs) {
        return componentFactories.get(qName).getComponent(attrs);
    }

    public Optional<String> getWhitespace(String qName, Attributes attrs) {
        return switch(qName) {
            case "glue" -> Optional.of("");
            case "nbsp" -> Optional.of(" ".repeat(
                Integer.parseInt(attrs.getValue("times"))
            ));
            case "ln" -> Optional.of("\n");
            default -> Optional.empty();
        };
    }

    public void registerModifier(Function<Attributes, ComponentModifier> modifierFunction, String... qNames) {
        register(attrs -> new ComponentHolder(modifierFunction.apply(attrs), this), qNames);
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

        registerModifier(attrs -> ComponentModifiers.click(new ClickEvent(
            ConstantNames.CLICK_EVENTS.get(attrs.getValue("action")),
            attrs.getValue("value")
        )), "click");

        registerModifier(attrs -> ComponentModifiers.color(attrs.getValue("hex")), "color");
        registerModifier(attrs -> ComponentModifiers.insertion(attrs.getValue("text")), "insertion");
        registerModifier(attrs -> ComponentModifiers.font(attrs.getValue("font")), "font");
        registerModifier(attrs -> ComponentModifiers.shadow(attrs.getValue("color")), "shadow");

        // Other leaf components
        register(attrs -> new LeafScoreComponent(), "score");
        register(attrs -> new LeafSelectorComponent(), "selector");
        register(attrs -> new LeafKeybindComponent(), "keybind");
        register(attrs -> new LeafGradientComponent(
            attrs.getValue("ref"),
            new ArrayList<>(),
                this
            ), "grad");

        register(this::createVarComponent, "var");
        register(attrs -> new LeafBungeeComponent(attrs.getValue("name")), "bungee_component");
        register(attrs -> new LeafEmbeddedMessage(attrs.getValue("ref")), "embed_msg");
    }

    public LeafVariableComponent createVarComponent(Attributes attrs) {
        return new LeafVariableComponent(attrs.getValue("name"));
    }
}
