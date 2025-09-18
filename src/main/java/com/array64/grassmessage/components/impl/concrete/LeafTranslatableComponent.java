package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.Component;
import com.array64.grassmessage.components.ComponentRegistry;
import com.array64.grassmessage.components.impl.AbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.xml.properties.PropertyHolder;
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
    private List<Component> with;
    private final PropertyHolder propertyHolder;

    public LeafTranslatableComponent(ComponentRegistry registry) {
        this.with = new ArrayList<>();
        this.propertyHolder = new PropertyHolder(Map.of(
            "key", com.array64.grassmessage.xml.properties.TextHolder::new,
            "with", registry::createCompositeComponent
        ), getDepthTracker());
    }

    @Override
    public void onStart() {
        super.onStart();
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

        translatable.setFallback(ctx.substituteVars(fallback));
        return translatable;
    }
}
