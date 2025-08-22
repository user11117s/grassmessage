package com.array64.grassmessage;

import java.util.HashMap;
import java.util.Map;

class FileData {
    private final Map<String, GradientData> gradients = new HashMap<>();
    private final Map<String, ComponentHolder> messages = new HashMap<>();

    public void addGradient(String name, GradientData gradientData) {
        gradients.put(name, gradientData);
    }

    public void addMessage(String name, ComponentHolder messageData) {
        messages.put(name, messageData);
    }

    public GradientData getGradient(String name) {
        return gradients.get(name);
    }

    public ComponentHolder getMessage(String message) {
        return messages.get(message);
    }
}
