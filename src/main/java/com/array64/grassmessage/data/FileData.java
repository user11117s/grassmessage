package com.array64.grassmessage.data;

import com.array64.grassmessage.components.GComponent;

import java.util.HashMap;
import java.util.Map;

public class FileData {
    private final Map<String, GradientData> gradients = new HashMap<>();
    private final Map<String, Message> messages = new HashMap<>();

    public void addGradient(String name, GradientData gradientData) {
        gradients.put(name, gradientData);
    }

    public void addMessage(String name, GComponent messageData) {
        messages.put(name, new Message(messageData, this));
    }

    public GradientData getGradient(String ref) {
        return gradients.get(ref);
    }

    public Message getMessage(String ref) {
        return messages.get(ref);
    }

    public GComponent getMessageComponent(String ref) {
        return messages.get(ref).getComponent();
    }
}
