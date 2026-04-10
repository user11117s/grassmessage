package com.array64.grassmessage.data;

import com.array64.grassmessage.components.GComponent;
import com.array64.grassmessage.components.InstantiationContext;
import net.kyori.adventure.text.Component;

import java.util.Map;

public class Message {
    private final GComponent component;
    private final FileData fileData;

    Message(GComponent component, FileData fileData) {
        this.component = component;
        this.fileData = fileData;
    }

    public Component get() {
        return this.get(Map.of(), Map.of());
    }

    public Component get(Map<String, String> vars) {
        return this.get(vars, Map.of());
    }

    public Component get(
            Map<String, String> vars, Map<String, Component> adventureComponents) {

        Component parent = Component.empty();
        component.instantiateInParent(parent, new InstantiationContext(fileData, vars, adventureComponents));
        return parent;
    }

    GComponent getComponent() {
        return component;
    }
}
