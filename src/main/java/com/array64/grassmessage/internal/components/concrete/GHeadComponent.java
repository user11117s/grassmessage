package com.array64.grassmessage.internal.components.concrete;

import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.components.GAbstractComponent;
import com.array64.grassmessage.internal.misc.Evaluation;
import com.array64.grassmessage.internal.xml.DepthTracker;
import com.array64.grassmessage.internal.xml.properties.PropertyHolder;
import com.array64.grassmessage.internal.xml.properties.TextHolder;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.object.ObjectContents;
import org.xml.sax.Attributes;

import java.util.Map;

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
                unsubbedValue = propertyHolder.get().get(0).getValue(String.class);
        String value = ctx.substituteVars(unsubbedValue);

        return Component.object(
                switch(action) {
                    case "uuid" -> ObjectContents.playerHead().id(Evaluation.evalUUID(unsubbedValue, ctx)).hat(outerLayer).build();
                    case "texture" -> ObjectContents.playerHead().texture(Key.key(value)).hat(outerLayer).build();
                    case "name" -> ObjectContents.playerHead().name(value).hat(outerLayer).build();
                    default -> throw new IllegalStateException("[THIS SHOULD NEVER HAPPEN] Unexpected skin source type: " + action);
                }
        );
    }
}