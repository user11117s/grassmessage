package com.array64.grassmessage.components;

import com.array64.grassmessage.components.impl.concrete.*;
import com.array64.grassmessage.misc.ConstantNames;
import com.array64.grassmessage.xml.DepthTracker;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ComponentRegistry {
    private final Map<String, ComponentFactory> componentFactories;
    public final DepthTracker depthTracker;

    public ComponentRegistry(DepthTracker depthTracker) {
        this.depthTracker = depthTracker;
        this.componentFactories = new HashMap<>();
        registerAll();
    }

    public void register(ComponentFactory factory, String... qNames) {
        for(String qName : qNames) {
            componentFactories.put(qName, factory);
        }
    }

    public void register(ComponentFactory.Abstract factory, String... qNames) {
        // Allows me to initialize parts of AbstractComponent here. Probably not the best design though,
        // since it's not obvious which overload is used.
        register((ComponentFactory) (attrs -> factory.getAbstractComponent(attrs).initialize(depthTracker)), qNames);
    }

    public Component get(String qName, Attributes attrs) {
        Component component = componentFactories.get(qName).getComponent(attrs);
        component.onStart();
        return component;
    }

    public Optional<String> getWhitespace(String qName, Attributes attrs) {
        return switch(qName) {
            case "glue" -> Optional.of("");
            case "nbsp" -> Optional.of(" ".repeat(
                Integer.parseInt(attrs.getValue("spaces"))
            ));
            case "ln" -> Optional.of("\n");
            default -> Optional.empty();
        };
    }

    public void registerModifier(Function<Attributes, ComponentModifier> modifierFunction, String... qNames) {
        register(attrs -> new CompositeComponent(modifierFunction.apply(attrs), this), qNames);
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
        register(attrs -> new LeafGradientComponent(attrs.getValue("ref")), "grad");

        register(attrs -> new LeafBungeeComponent(attrs.getValue("name")), "bungee_component");
        register(attrs -> new LeafEmbeddedMessage(attrs.getValue("ref")), "embed_msg");
        register(attrs -> new LeafTranslatableComponent(this), "translatable");
        register(attrs -> new LeafHoverComponent(this), "hover");
    }

    public CompositeComponent createCompositeComponent() {
        var compositeComponent = new CompositeComponent(this);
        compositeComponent.initialize(depthTracker);
        compositeComponent.onStart();
        return compositeComponent;
    }
}
