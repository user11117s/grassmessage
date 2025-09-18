package com.array64.grassmessage.components.impl.concrete;

import com.array64.grassmessage.components.impl.AbstractComponent;
import com.array64.grassmessage.components.InstantiationContext;
import com.array64.grassmessage.xml.properties.PropertyHolder;
import com.array64.grassmessage.xml.properties.TextHolder;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ScoreComponent;
import org.xml.sax.Attributes;

import java.util.Map;

public class LeafScoreComponent extends AbstractComponent {
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
                case "target" -> target = propertyMeta.getValue(String.class);
                case "objective" -> objective = propertyMeta.getValue(String.class);
                case "default" -> defaultValue = propertyMeta.getValue(String.class);
            }
        });
    }

    @Override
    public BaseComponent instantiate(InstantiationContext ctx) {
        String substitutedTarget = ctx.substituteVars(target),
            substitutedObjective = ctx.substituteVars(objective),
            substitutedDefaultValue = ctx.substituteVars(defaultValue);

        return substitutedDefaultValue == null ?
            new ScoreComponent(substitutedTarget, substitutedObjective)
            : new ScoreComponent(substitutedTarget, substitutedObjective, substitutedDefaultValue);
    }
}
