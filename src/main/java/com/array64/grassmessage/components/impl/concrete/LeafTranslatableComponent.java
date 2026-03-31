package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.Component;
import com.array64.grassmessage.components.ComponentRegistry;
import com.array64.grassmessage.components.impl.AbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.xml.properties.PropertyHolder;
import com.array64.grassmessage.xml.properties.TextHolder;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeafTranslatableComponent extends AbstractComponent {
    private String key;
    private String fallback = null;
    private final List<Component> with;
    private PropertyHolder propertyHolder;
    private final ComponentRegistry registry;

    public LeafTranslatableComponent(ComponentRegistry registry) {
        this.with = new ArrayList<>();
        this.registry = registry;
    }

    @Override
    public void onStart() {
        this.propertyHolder = new PropertyHolder(Map.of(
                "key", TextHolder::new,
                "with", registry::createCompositeComponent,
                "fallback", TextHolder::new
        ), getDepthTracker());
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        propertyHolder.startTag(qName, attrs);
    }

    @Override
    protected void exitTag(String qName) {
        propertyHolder.endTag(qName);
    }

    @Override
    public void parseText(String text) {
        propertyHolder.parseText(text);
    }

    @Override
    public void onEnd() {
        propertyHolder.get().forEach(propertyMeta -> {
            switch(propertyMeta.propertyName()) {
                case "key" -> key = propertyMeta.getValue(String.class);
                case "with" -> with.add(propertyMeta.getValue(Component.class));
                case "fallback" -> fallback = propertyMeta.getValue(String.class);
            }
        });
    }

    @Override
    protected net.kyori.adventure.text.Component instantiate(InstantiationContext ctx) {
        var translatable = net.kyori.adventure.text.Component.translatable(ctx.substituteVars(key));
        translatable = translatable.arguments(with.stream().map(component -> {
            net.kyori.adventure.text.Component parentComponent = net.kyori.adventure.text.Component.empty();
            component.instantiateInParent(parentComponent, ctx);
            return parentComponent;
        }).toList());

        if(fallback != null) translatable = translatable.fallback(ctx.substituteVars(fallback));
        return translatable;
    }
}
