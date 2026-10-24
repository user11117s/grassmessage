package com.array64.grassmessage.internal.components.concrete;

import com.array64.grassmessage.internal.components.GAbstractComponent;
import com.array64.grassmessage.internal.components.GComponent;
import com.array64.grassmessage.internal.components.GComponentRegistry;
import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.xml.properties.PropertyHolder;
import com.array64.grassmessage.internal.xml.properties.TextHolder;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GSelectorComponent extends GAbstractComponent {
    private String pattern;
    private GComponent separator = null;
    private PropertyHolder propertyHolder;
    private final GComponentRegistry registry;

    public GSelectorComponent(GComponentRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onStart() {
        this.propertyHolder = new PropertyHolder(Map.of(
            "pattern", TextHolder::new,
            "separator", registry::createCompositeComponent
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
                case "pattern" -> pattern = propertyMeta.getValue();
                case "separator" -> separator = propertyMeta.getValue();
            }
        });
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        String substitutedPattern = ctx.substituteVars(pattern);

        if(separator == null)
            return Component.selector(substitutedPattern);
        else {
            Component separatorParent = Component.empty();
            separatorParent = separator.instantiateInParent(separatorParent, ctx);
            return Component.selector(substitutedPattern, separatorParent);
        }
    }
}
