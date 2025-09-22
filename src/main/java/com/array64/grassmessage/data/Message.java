package com.array64.grassmessage.data;

import com.array64.grassmessage.components.Component;
import com.array64.grassmessage.components.InstantiationContext;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Map;

public class Message {
    private final Component component;
    private final FileData fileData;

    Message(Component component, FileData fileData) {
        this.component = component;
        this.fileData = fileData;
    }

    public BaseComponent get() {
        return this.get(Map.of(), Map.of());
    }

    public BaseComponent get(Map<String, String> vars) {
        return this.get(vars, Map.of());
    }

    public BaseComponent get(Map<String, String> vars, Map<String, BaseComponent> bungeeComponents) {
        BaseComponent parent = new TextComponent();
        component.instantiateInParent(parent, new InstantiationContext(fileData, vars, bungeeComponents));
        return parent;
    }

    Component getComponent() {
        return component;
    }
}
