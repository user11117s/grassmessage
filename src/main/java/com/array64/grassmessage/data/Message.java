package com.array64.grassmessage.data;

import com.array64.grassmessage.components.Component;
import com.array64.grassmessage.components.InstantiationContext;

import java.util.Map;

public class Message {
    private final Component component;
    private final FileData fileData;

    Message(Component component, FileData fileData) {
        this.component = component;
        this.fileData = fileData;
    }

    public net.kyori.adventure.text.Component get() {
        return this.get(Map.of(), Map.of());
    }

    public net.kyori.adventure.text.Component get(Map<String, String> vars) {
        return this.get(vars, Map.of());
    }

    public net.kyori.adventure.text.Component get(
            Map<String, String> vars, Map<String, net.kyori.adventure.text.Component> adventureComponents) {

        net.kyori.adventure.text.Component parent = net.kyori.adventure.text.Component.empty();
        component.instantiateInParent(parent, new InstantiationContext(fileData, vars, adventureComponents));
        return parent;
    }

    Component getComponent() {
        return component;
    }
}
