package com.array64.grassmessage;

import java.util.HashMap;
import java.util.Map;

public class FileData {
    private final Map<String, MutableReference<GradientData>> gradients = new HashMap<>();
    private final Map<String, ComponentHolder> messages = new HashMap<>();

    public void addGradient(String name, GradientData gradientData) {
        var gradientReference = gradients.get(name);

        if(gradientReference == null)
            gradients.put(name, new MutableReference<>(gradientData));
        else
            gradientReference.set(gradientData);
    }

    public void addMessage(String name, ComponentHolder messageData) {
        messages.put(name, messageData);
    }

    public MutableReference<GradientData> getGradientReference(String name) {
        return gradients.computeIfAbsent(name, k -> new MutableReference<>());
    }

    public ComponentHolder getMessage(String message) {
        return messages.get(message);
    }
}
