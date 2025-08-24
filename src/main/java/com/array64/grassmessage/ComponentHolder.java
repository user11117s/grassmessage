package com.array64.grassmessage;

import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;
import java.util.List;

class ComponentHolder implements Component {
    private final List<Component> heldComponents;
    private final ComponentModifier modifier;
    private final Component parent;

    public ComponentHolder(Component parent, ComponentModifier modifier) {
        this.heldComponents = new ArrayList<>();
        this.modifier = modifier;
        this.parent = parent;
    }

    @Override
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
        int top = heldComponents.size() - 1;

        // Add a text component if we aren't already on a text component.
        // This is the only case where components are added without
        // becoming the currentComponent of a MessageParser.
        if(top < 0 || !(heldComponents.get(top++) instanceof ComponentOfText))
            addComponent(new ComponentOfText(this));

        heldComponents.get(top).append(text);
    }

    @Override
    public Component getParent() {
        return this.parent;
    }
}
