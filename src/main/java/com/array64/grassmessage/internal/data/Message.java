package com.array64.grassmessage.internal.data;

import com.array64.grassmessage.internal.components.GComponent;
import com.array64.grassmessage.internal.components.InstantiationContext;
import net.kyori.adventure.text.Component;

import java.util.Map;

public class Message {
    private final GComponent component;
    private final FileData fileData;

    Message(GComponent component, FileData fileData) {
        this.component = component;
        this.fileData = fileData;
    }

    public Component get(Map<String, Object> vars) {

        Component parent = Component.empty();
        parent = component.instantiateInParent(parent, new InstantiationContext(fileData, vars));
        return parent;
    }

    GComponent getComponent() {
        return component;
    }
}
