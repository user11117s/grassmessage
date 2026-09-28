package com.array64.grassmessage.internal.components.concrete;

import com.array64.grassmessage.internal.components.GComponent;
import com.array64.grassmessage.internal.components.GComponentRegistry;
import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.components.hover.EntityHoveredContent;
import com.array64.grassmessage.internal.components.hover.HoveredContent;
import com.array64.grassmessage.internal.components.hover.ItemHoveredContent;
import com.array64.grassmessage.internal.components.hover.TextHoveredContent;
import com.array64.grassmessage.internal.components.GAbstractComponent;
import com.array64.grassmessage.internal.xml.DepthTracker;
import com.array64.grassmessage.internal.xml.properties.PropertyHolder;
import com.array64.grassmessage.internal.xml.properties.TextHolder;
import com.array64.grassmessage.internal.xml.properties.XmlPropertyMeta;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEventSource;
import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GHoverComponent extends GAbstractComponent {
    private HoveredContent hoveredContent;
    private GComponent mainContent;
    private final GComponentRegistry componentRegistry;
    private PropertyHolder propertyHolder;

    public GHoverComponent(GComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
    }

    @Override
    public void onStart() {
        DepthTracker depthTracker = getDepthTracker();

        propertyHolder = new PropertyHolder(Map.of(
            "content", componentRegistry::createCompositeComponent,
            "show_text", componentRegistry::createCompositeComponent,
            "show_item", () -> new PropertyHolder(Map.of(
                "id", TextHolder::new,
                "count", TextHolder::new,
                "sdata", TextHolder::new,
                "vdata", TextHolder::new
            ), depthTracker),
            "show_entity", () -> new PropertyHolder(Map.of(
                "type", TextHolder::new,
                "uuid", TextHolder::new,
                "name", componentRegistry::createCompositeComponent
            ), depthTracker)
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
    public void onEnd() {
        List<XmlPropertyMeta> properties = propertyHolder.get();
        properties.forEach(propertyMeta -> {
            switch(propertyMeta.propertyName()) {
                case "content" -> mainContent = propertyMeta.getValue(GComponent.class);
                case "show_text" -> hoveredContent = new TextHoveredContent(propertyMeta.getValue(GComponent.class));
                case "show_item" -> parseItemProperties(propertyMeta.getValue(List.class));
                case "show_entity" -> parseEntityProperties(propertyMeta.getValue(List.class));
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void parseItemProperties(List<?> properties) {
        String id = "";
        String count = "1";
        Map<String, String> sdata = new HashMap<>();
        Map<String, String> vdata = new HashMap<>();

        for(var propertyMeta : (List<XmlPropertyMeta>) properties) {
                switch(propertyMeta.propertyName()) {
                    case "id" -> id = propertyMeta.getValue(String.class);
                    case "count" -> count = propertyMeta.getValue(String.class);
                    case "sdata" -> sdata.put(propertyMeta.attrs().getValue("key"), propertyMeta.getValue(String.class));
                    case "vdata" -> vdata.put(propertyMeta.attrs().getValue("key"), propertyMeta.attrs().getValue("src"));
                }
            }

        hoveredContent = new ItemHoveredContent(id, count, sdata, vdata);
    }

    @SuppressWarnings("unchecked")
    private void parseEntityProperties(List<?> properties) {
        String type = "";
        String uuid = "";
        GComponent name = null;

        for(var propertyMeta : (List<XmlPropertyMeta>) properties) {
            switch(propertyMeta.propertyName()) {
                case "type" -> type = propertyMeta.getValue(String.class);
                case "uuid" -> uuid = propertyMeta.getValue(String.class);
                case "name" -> name = propertyMeta.getValue(GComponent.class);
            }
        }

        hoveredContent = new EntityHoveredContent(type, uuid, name);
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        Component parent = Component.empty();
        HoverEventSource<?> instantiatedEvent = hoveredContent.instantiate(ctx);

        parent = mainContent.instantiateInParent(parent, ctx);
        return parent.hoverEvent(instantiatedEvent);
    }
}
