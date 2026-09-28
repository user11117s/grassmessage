package com.array64.grassmessage.internal.components.concrete;

import com.array64.grassmessage.internal.components.GComponent;
import com.array64.grassmessage.internal.components.GComponentRegistry;
import com.array64.grassmessage.internal.components.GAbstractComponent;
import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.xml.properties.PropertyHolder;
import com.array64.grassmessage.internal.xml.properties.TextHolder;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GTranslatableComponent extends GAbstractComponent {
    private String key;
    private String fallback = null;
    private final List<GComponent> with;
    private PropertyHolder propertyHolder;
    private final GComponentRegistry registry;

    public GTranslatableComponent(GComponentRegistry registry) {
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
                case "with" -> with.add(propertyMeta.getValue(GComponent.class));
                case "fallback" -> fallback = propertyMeta.getValue(String.class);
            }
        });
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        var translatable = Component.translatable(ctx.substituteVars(key));
        translatable = translatable.arguments(with.stream().map(component -> {
            Component parentComponent = Component.empty();
            component.instantiateInParent(parentComponent, ctx);
            return parentComponent;
        }).toList());

        if(fallback != null) translatable = translatable.fallback(ctx.substituteVars(fallback));
        return translatable;
    }
}
