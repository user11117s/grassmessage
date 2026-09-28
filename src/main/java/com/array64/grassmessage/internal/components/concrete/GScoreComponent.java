package com.array64.grassmessage.internal.components.concrete;

import com.array64.grassmessage.internal.components.GAbstractComponent;
import com.array64.grassmessage.internal.components.InstantiationContext;
import com.array64.grassmessage.internal.xml.properties.PropertyHolder;
import com.array64.grassmessage.internal.xml.properties.TextHolder;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

import java.util.Map;

public class GScoreComponent extends GAbstractComponent {
    private String target = "", objective = "";
    private String defaultValue;
    private final PropertyHolder propertyHolder = new PropertyHolder(Map.of(
        "target", TextHolder::new,
        "objective", TextHolder::new,
        "default", TextHolder::new
    ), getDepthTracker());

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
                case "target" -> target = propertyMeta.getValue();
                case "objective" -> objective = propertyMeta.getValue();
                case "default" -> defaultValue = propertyMeta.getValue();
            }
        });
    }

    @SuppressWarnings("deprecation")
    @Override
    public Component instantiate(InstantiationContext ctx) {
        String substitutedTarget = ctx.substituteVars(target),
            substitutedObjective = ctx.substituteVars(objective),
            substitutedDefaultValue = ctx.substituteVars(defaultValue);

        return substitutedDefaultValue == null ?
            Component.score(substitutedTarget, substitutedObjective)
            : Component.score(substitutedTarget, substitutedObjective, substitutedDefaultValue);
    }
}
