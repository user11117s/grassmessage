package com.array64.grassmessage.data;

import com.array64.grassmessage.components.impl.concrete.CompositeComponent;

import java.util.HashMap;
import java.util.Map;

public class FileData {
    private final Map<String, GradientData> gradients = new HashMap<>();
    private final Map<String, CompositeComponent> messages = new HashMap<>();

    public void addGradient(String name, GradientData gradientData) {
        gradients.put(name, gradientData);
    }

    public void addMessage(String name, CompositeComponent messageData) {
        messages.put(name, messageData);
    }

    public GradientData getGradient(String ref) {
        return gradients.get(ref);
    }

    public CompositeComponent getMessage(String ref) {
        return messages.get(ref);
    }
}
