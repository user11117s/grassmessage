package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.GComponent;
import com.array64.grassmessage.components.GComponentRegistry;
import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.components.hover.EntityHoveredContent;
import com.array64.grassmessage.components.hover.HoveredContent;
import com.array64.grassmessage.components.hover.ItemHoveredContent;
import com.array64.grassmessage.components.hover.TextHoveredContent;
import com.array64.grassmessage.components.impl.GAbstractComponent;
import com.array64.grassmessage.xml.DepthTracker;
import com.array64.grassmessage.xml.properties.PropertyHolder;
import com.array64.grassmessage.xml.properties.TextHolder;
import com.array64.grassmessage.xml.properties.XmlPropertyMeta;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.text.object.ObjectContents;
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class GHeadComponent extends GAbstractComponent {
    private final boolean outerLayer;
    private PropertyHolder propertyHolder;

    public GHeadComponent(String outerLayerStr) {
        outerLayer = "1".equals(outerLayerStr) || "true".equals(outerLayerStr);
    }

    @Override
    public void onStart() {
        DepthTracker depthTracker = getDepthTracker();

        propertyHolder = new PropertyHolder(Map.of(
                "texture", TextHolder::new,
                "uuid", TextHolder::new,
                "name", TextHolder::new
        ), depthTracker);
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
    protected Component instantiate(InstantiationContext ctx) {
        String action = propertyHolder.get().get(0).propertyName(),
                value = ctx.substituteVars(propertyHolder.get().get(0).getValue(String.class));

        return Component.object(
                switch(action) {
                    case "uuid" -> ObjectContents.playerHead().id(UUID.fromString(value)).hat(outerLayer).build();
                    case "texture" -> ObjectContents.playerHead().texture(Key.key(value)).hat(outerLayer).build();
                    case "name" -> ObjectContents.playerHead().name(value).hat(outerLayer).build();
                    default -> throw new IllegalArgumentException("Unexpected skin source type: " + action);
                }
        );
    }
}