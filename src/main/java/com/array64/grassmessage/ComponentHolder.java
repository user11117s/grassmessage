package com.array64.grassmessage;

import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;
import java.util.List;

class ComponentHolder implements Component {
    private final List<Component> heldComponents;
    private final ComponentModifier modifier;

    public ComponentHolder(ComponentModifier modifier) {
        this.heldComponents = new ArrayList<>();
        this.modifier = modifier;
    }

    public void addComponent(Component component) {
        heldComponents.add(component);
    }

    @Override
    public void modify(ComponentBuilder builder) {
        modifier.modify(builder);

        // Add components to the builder based on children
        if(heldComponents.isEmpty()) return;
        if(heldComponents.size() == 1) heldComponents.get(0).modify(builder);
        else {
            heldComponents.forEach(child -> {
                ComponentBuilder childBuilder = new ComponentBuilder();
                child.modify(builder);
                builder.append(childBuilder.build());
            });
        }
    }

    @Override
    public void append(String text) {
        if(heldComponents.isEmpty())
            heldComponents.add(new ComponentOfText());

        heldComponents.get(heldComponents.size() - 1).append(text);
    }
}
