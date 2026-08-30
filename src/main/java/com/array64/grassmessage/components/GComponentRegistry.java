package com.array64.grassmessage.components;

import com.array64.grassmessage.components.impl.concrete.*;
import com.array64.grassmessage.misc.ConstantNames;
import com.array64.grassmessage.xml.DepthTracker;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.event.ClickEvent;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class GComponentRegistry {
    private final Map<String, GComponentFactory> componentFactories;
    public final DepthTracker depthTracker;

    public GComponentRegistry(DepthTracker depthTracker) {
        this.depthTracker = depthTracker;
        this.componentFactories = new HashMap<>();
        registerAll();
    }

    public void register(GComponentFactory factory, String... qNames) {
        for(String qName : qNames) {
            componentFactories.put(qName, factory);
        }
    }

    public void register(GComponentFactory.Abstract factory, String... qNames) {
        // Allows me to initialize parts of AbstractComponent here. Probably not the best design though,
        // since it's not obvious which overload is used.
        register((GComponentFactory) (attrs -> factory.getAbstractComponent(attrs).initialize(depthTracker)), qNames);
    }

    public GComponent get(String qName, Attributes attrs) {
        GComponent component = componentFactories.get(qName).getComponent(attrs);
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

    public void registerModifier(Function<Attributes, GComponentModifier> modifierFunction, String... qNames) {
        register(attrs -> new GCompositeComponent(modifierFunction.apply(attrs), this), qNames);
    }

    private void registerAll() {

        // Modifiers without attributes
        for(ConstantNames.ModifierMapping modifierMapping : ConstantNames.MODIFIERS) {
            final GComponentModifier modifier = modifierMapping.modifier();
            final String[] qNames = modifierMapping.qNames();

            registerModifier(attrs -> modifier, qNames);
        }

        // 16 default chat colors
        for(ConstantNames.ColorMapping colorMapping : ConstantNames.CHAT_COLORS) {
            final NamedTextColor chatColor = colorMapping.color();
            final String qName = colorMapping.name();

            registerModifier(attrs -> GComponentModifiers.color(chatColor), qName);
        }

        // Modifiers with attributes

        registerModifier(attrs -> GComponentModifiers.click(
            ConstantNames.CLICK_EVENTS.get(attrs.getValue("action")),
            attrs.getValue("value")
        ), "click");

        registerModifier(attrs -> GComponentModifiers.color(attrs.getValue("hex")), "color");
        registerModifier(attrs -> GComponentModifiers.insertion(attrs.getValue("text")), "insertion");
        registerModifier(attrs -> GComponentModifiers.font(attrs.getValue("font")), "font");
        registerModifier(attrs -> GComponentModifiers.shadow(attrs.getValue("color")), "shadow");

        // Other leaf components
        register(attrs -> new GScoreComponent(), "score");
        register(attrs -> new GSelectorComponent(), "selector");
        register(attrs -> new GKeybindComponent(), "keybind");
        register(attrs -> new GGradientComponent(attrs.getValue("ref")), "gradref");
        register(attrs -> new GStyleComponent(attrs.getValue("ref"), this), "styleref");

        register(attrs -> new GAdventureComponent(attrs.getValue("name")), "adventure");
        register(attrs -> new GEmbeddedMessage(attrs.getValue("ref")), "embed_msg");
        register(attrs -> new GTranslatableComponent(this), "translatable");
        register(attrs -> new GHoverComponent(this), "hover");
    }

    public GCompositeComponent createCompositeComponent() {
        var compositeComponent = new GCompositeComponent(this);
        compositeComponent.initialize(depthTracker);
        compositeComponent.onStart();
        return compositeComponent;
    }
}
