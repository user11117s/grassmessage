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
import com.array64.grassmessage.xml.properties.XMLPropertyMeta;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEventSource;
import org.xml.sax.Attributes;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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
                "tag", TextHolder::new
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
        List<XMLPropertyMeta> properties = propertyHolder.get();
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
        AtomicReference<String> id = new AtomicReference<>("");
        AtomicReference<Integer> count = new AtomicReference<>(1);
        AtomicReference<String> tag = new AtomicReference<>();

        ((List<XMLPropertyMeta>) properties)
            .forEach(propertyMeta -> {
                switch(propertyMeta.propertyName()) {
                    case "id" -> id.set(propertyMeta.getValue(String.class));
                    case "count" -> count.set(Integer.parseInt(propertyMeta.getValue(String.class)));
                    case "tag" -> tag.set(propertyMeta.getValue(String.class));
                }
            });

        hoveredContent = new ItemHoveredContent(id.get(), count.get(), tag.get());
    }

    @SuppressWarnings("unchecked")
    private void parseEntityProperties(List<?> properties) {
        AtomicReference<String> type = new AtomicReference<>("");
        AtomicReference<String> uuid = new AtomicReference<>("");
        AtomicReference<GComponent> name = new AtomicReference<>();

        ((List<XMLPropertyMeta>) properties)
            .forEach(propertyMeta -> {
                switch(propertyMeta.propertyName()) {
                    case "type" -> type.set(propertyMeta.getValue(String.class));
                    case "uuid" -> uuid.set(propertyMeta.getValue(String.class));
                    case "name" -> name.set(propertyMeta.getValue(GComponent.class));
                }
            });

        hoveredContent = new EntityHoveredContent(type.get(), uuid.get(), name.get());
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        Component parent = Component.empty();
        HoverEventSource<?> instantiatedEvent = hoveredContent.instantiate(ctx);

        mainContent.instantiateInParent(parent, ctx);
        return parent.hoverEvent(instantiatedEvent);
    }
}
