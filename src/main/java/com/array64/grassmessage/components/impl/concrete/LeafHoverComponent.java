package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.Component;
import com.array64.grassmessage.components.ComponentRegistry;
import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.components.hover.HoveredContent;
import com.array64.grassmessage.components.hover.TextHoveredContent;
import com.array64.grassmessage.components.impl.AbstractComponent;
import com.array64.grassmessage.xml.properties.PropertyHolder;
import com.array64.grassmessage.xml.properties.TextHolder;
import com.array64.grassmessage.xml.properties.XMLPropertyMeta;
import net.md_5.bungee.api.chat.BaseComponent;
import org.xml.sax.Attributes;

import java.util.List;
import java.util.Map;

public class LeafHoverComponent extends AbstractComponent {
    private HoveredContent hoveredContent;
    private Component mainContent;
    private final ComponentRegistry componentRegistry;
    private PropertyHolder propertyHolder;

    public LeafHoverComponent(ComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
    }

    @Override
    public void onStart() {
        propertyHolder = new PropertyHolder(Map.of(
            "content", componentRegistry::createCompositeComponent,
            "show_text", componentRegistry::createCompositeComponent,
            "show_item", TextHolder::new
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
        List<XMLPropertyMeta> properties = propertyHolder.get();
        properties.forEach(propertyMeta -> {
            switch(propertyMeta.propertyName()) {
                case "content" -> mainContent = (Component) propertyMeta.parser().get();
                case "show_text" -> hoveredContent = new TextHoveredContent((Component) propertyMeta.parser().get());
                case "show_item" -> {

                }
            }
        });
    }

    @Override
    protected BaseComponent instantiate(InstantiationContext ctx) {
        return null;
    }
}
