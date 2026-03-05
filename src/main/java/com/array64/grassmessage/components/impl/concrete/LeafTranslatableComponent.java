package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.Component;
import com.array64.grassmessage.components.ComponentRegistry;
import com.array64.grassmessage.components.impl.AbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.xml.properties.PropertyHolder;
import com.array64.grassmessage.xml.properties.TextHolder;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
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
    protected BaseComponent instantiate(InstantiationContext ctx) {
        var translatable = new TranslatableComponent(ctx.substituteVars(key));
        translatable.setWith(with.stream().map(component -> {
            BaseComponent parentComponent = new TextComponent();
            component.instantiateInParent(parentComponent, ctx);
            return parentComponent;
        }).toList());

        if(fallback != null) translatable.setFallback(ctx.substituteVars(fallback));
        return translatable;
    }
}
